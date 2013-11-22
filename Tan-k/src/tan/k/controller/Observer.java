/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.controller;

import Jama.Matrix;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.factory.EigenDecomposition;
import org.ejml.ops.CommonOps;

/**
 *
 * @author gabriel
 */
public class Observer {
  private Matrix A;
  private Matrix C;
  private Matrix VInverse;
  private Matrix W;
  private Matrix qc;
  private Matrix L;
  
  private List<Double> poles;
  private List<Double> polynomial;
  
  /**
   *
   * @param A
   * @param poles
   */
  public Observer(Matrix A, Matrix C, List<Double> poles){
    this.A = A;
    this.C = C;
    this.poles = poles;
    
    calcPolynomial();
    calcQc();
    calcVInverse();
    calcW();
    calcL();
  }
  
  public Observer(Matrix A, Matrix C, Matrix L){
    this.L = L;
    this.A = A;
    this.C = C;
    
    calcVInverse();
    calcW();
    
    double[] qc = new double[A.getColumnDimension()];
    Matrix VITimesW = VInverse.times(W);
        
    for(int i=0; i<A.getColumnDimension(); i++){
      qc[i] = L.get(i,0);
      for(int j = i; j>0; j--){
        qc[i] -= qc[i-j]*VITimesW.get(j, 0);
      }
      qc[i]/=VITimesW.get(0, 0);
    }
    
//    qc[0] = L.get(0, 0)/VITimesW.get(0, 0);
//    qc[1] = (L.get(1, 0) - qc[0]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
//    qc[2] = (L.get(2, 0) - qc[0]*VITimesW.get(2, 0) - qc[1]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
//    qc[3] = (L.get(3, 0) - qc[0]*VITimesW.get(3, 0) - qc[1]*VITimesW.get(2, 0) - qc[2]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
    
    DenseMatrix64F coef = new DenseMatrix64F(A.getRowDimension(), A.getColumnDimension());
    
    coef.set(0,0,1);
    for(int i=0;i<A.getColumnDimension();i++){
      Matrix powerA = matrixPow(A, i);
      for(int j=0;j<powerA.getColumnDimension(); j++){
        coef.set(j,i,powerA.get(j, 0));
      }
    }
    DenseMatrix64F x = new DenseMatrix64F(A.getColumnDimension(),1);
    DenseMatrix64F b = new DenseMatrix64F(A.getRowDimension(),1);
    
    Matrix powerA = matrixPow(A, A.getColumnDimension());
    for(int i=0; i<A.getRowDimension();i++){
      b.set(i, 0, qc[i]-powerA.get(i, 0));
    }
    CommonOps.solve(coef, b, x);
    polynomial = new ArrayList<>();
    
    for(int i=0; i<x.numRows; i++){
      polynomial.add(x.get(i));
    }
    polynomial.add(1.0);
    findRoots(polynomial.toArray(new Double[polynomial.size()]));
    
  }
  
  public static Complex64F[] findRoots(Double[] coefficients) {
        int N = coefficients.length-1;

        // Construct the companion matrix
        DenseMatrix64F c = new DenseMatrix64F(N,N);

        double a = coefficients[N];
        for( int i = 0; i < N; i++ ) {
            c.set(i,N-1,-coefficients[i]/a);
        }
        for( int i = 1; i < N; i++ ) {
            c.set(i,i-1,1);
        }

        // use generalized eigenvalue decomposition to find the roots
        EigenDecomposition<DenseMatrix64F> evd =  DecompositionFactory.eig(N, false); //eigGeneral(N, false);

        evd.decompose(c);

        Complex64F[] roots = new Complex64F[N];

        for( int i = 0; i < N; i++ ) {
            roots[i] = evd.getEigenvalue(i);
        }
        
        return roots;
    }

  private void calcPolynomial() {
    polynomial = new ArrayList<>();
    
    polynomial.add(1.0);
    
    List<Double> auxPoly = new ArrayList<>(poles.size());
    
    for(int j = 0; j<poles.size();j++){
      double pole = poles.get(j);
      int polySize = polynomial.size();
      for(int i = 0; i<polySize; i++){
        if(i<auxPoly.size())
          auxPoly.set(i, polynomial.get(i)*(-pole));
        else
          auxPoly.add(polynomial.get(i)*(-pole));
      }
      List<Double> shifted = new ArrayList<>();
      shifted.add(0.0);
      shifted.addAll(polynomial);
      polynomial = shifted;
      polynomial.set(0, auxPoly.get(0));
      for(int i = 1; i<polySize; i++){
        polynomial.set(i, polynomial.get(i)+auxPoly.get(i));
      }
    }
  }
  
  private void calcQc() {
    qc = new Matrix(A.getRowDimension(), A.getColumnDimension(), 0.0);
    for(int i=0;i<polynomial.size();i++){
      qc.plusEquals(matrixPow(A, i).times(polynomial.get(i)));
    }
    
    String s = "Qc\n";
    
    for(int i=0; i<qc.getRowDimension(); i++){
      for(int j=0; j<qc.getColumnDimension(); j++){
        s+=qc.get(i, j)+", ";
      }
      s+="\n";
    }
    System.out.println(s+"\n\n");
  }
  
  private Matrix matrixPow(Matrix A, int power){
    if(power>1){
      return A.times(matrixPow(A, power-1));
    }else if(power==1){
      return A;
    }else{
      return Matrix.identity(A.getRowDimension(), A.getColumnDimension());
    }
  }

  private void calcVInverse() {
    VInverse = new Matrix(A.getRowDimension(),A.getColumnDimension());
    
    for(int i=0;i<A.getRowDimension();i++){
      VInverse.setMatrix(i, i, 0, C.getColumnDimension()-1, C.times(matrixPow(A, i)));
    }
    
    VInverse = VInverse.inverse();
    
    String s = "VInverse\n";
    
    for(int i=0; i<VInverse.getRowDimension(); i++){
      for(int j=0; j<VInverse.getColumnDimension(); j++){
        s+=VInverse.get(i, j)+", ";
      }
      s+="\n";
    }
    System.out.println(s+"\n\n");
    
    
  }

  private void calcL() {
    L = qc.times(VInverse).times(W);
    String s = "L\n";
    
    for(int i=0; i<L.getRowDimension(); i++){
      for(int j=0; j<L.getColumnDimension(); j++){
        s+=L.get(i, j)+", ";
      }
      s+="\n";
    }
    System.out.println(s+"\n\n");
  }

  private void calcW() {
    W = new Matrix(A.getRowDimension(), 1, 0.0);
    W.set(W.getRowDimension()-1, 0, 1);
  }
}
