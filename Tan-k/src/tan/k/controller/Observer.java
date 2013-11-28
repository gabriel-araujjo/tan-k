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
  private Matrix G;
  private Matrix C;
  private Matrix VInverse;
  private Matrix W;
  private Matrix qc;
  private Matrix L;
  private Matrix G_LC;
  private Matrix estimated_levels;
  
  private List<Complex64F> poles;
  private List<Double> polynomial;
  
  /**
   *
   * @param G
   * @param poles
   */
  public Observer(Matrix G, Matrix C, List<Complex64F> poles){
    this(G,C);
    setPoles(poles);
  }
  
  public Observer(Matrix G, Matrix C, Matrix L){
    this(G,C);
    setL(L);
  }
  
  public Observer(Matrix G, Matrix C){
    this.G = G;
    this.C = C;
    this.poles = new ArrayList<>();
    poles.add(new Complex64F(0,0));
    poles.add(new Complex64F(0,0));
    this.L = new Matrix(new double[][] {{0},{0}});
    
    this.estimated_levels = new Matrix(new double[][] {{0},{0}});
  }
  
  public void estimate(double l1, double l2){
    Matrix tankLevels = new Matrix(new double[][] {{l1},{l2}});
    this.estimated_levels = G_LC.times(tankLevels.minus(tankLevels));
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
            System.out.println("root "+i+"= "+roots[i].getReal()+" + j"+roots[i].getImaginary());
        }
        
        return roots;
    }

  private void calcPolynomial() {
    List<Complex64F> polynomial = new ArrayList<>();
    
    polynomial.add(new Complex64F(1.0, 0));
    
    List<Complex64F> auxPoly = new ArrayList<>(getPoles().size());
    
    for(int j = 0; j<getPoles().size();j++){
      Complex64F pole = getPoles().get(j);
      int polySize = polynomial.size();
      for(int i = 0; i<polySize; i++){
        Complex64F aux = new Complex64F();
        mult(polynomial.get(i), mult(pole, -1.0, new Complex64F()), aux );
        if(i<auxPoly.size()){
          auxPoly.set(i,  aux);
        }else
          auxPoly.add(aux);
      }
      List<Complex64F> shifted = new ArrayList<>();
      shifted.add(new Complex64F(0, 0));
      shifted.addAll(polynomial);
      polynomial = shifted;
      polynomial.set(0, auxPoly.get(0));
      for(int i = 1; i<polySize; i++){
        polynomial.set(i, add(polynomial.get(i),auxPoly.get(i),new Complex64F()));
      }
    }
    
    this.polynomial = new ArrayList<>();
    for(Complex64F c : polynomial){
      this.polynomial.add(c.getReal());
    }
  }
  
  private void calcQc() {
    qc = new Matrix(G.getRowDimension(), G.getColumnDimension(), 0.0);
    for(int i=0;i<polynomial.size();i++){
      qc.plusEquals(matrixPow(G, i).times(polynomial.get(i)));
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
    VInverse = new Matrix(G.getRowDimension(),G.getColumnDimension());
    
    for(int i=0;i<G.getRowDimension();i++){
      VInverse.setMatrix(i, i, 0, C.getColumnDimension()-1, C.times(matrixPow(G, i)));
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
    if(this.G_LC == null){
      this.G_LC = new Matrix(2, 2);
    }
    
    //this.G_LC = new Matrix(new double[][] {{0.9934570, -L.get(0, 1)}, {0.0065215, 0.9934570-L.get(1, 1)}});
    this.G_LC.set(0, 0, 0.9934570);
    this.G_LC.set(0, 1, -L.get(0, 0));
    this.G_LC.set(1, 0, 0.0065215);
    this.G_LC.set(1,1, 0.9934570-getL().get(1, 0));
    
    String s = "L\n";
    
    for(int i=0; i<getL().getRowDimension(); i++){
      for(int j=0; j<getL().getColumnDimension(); j++){
        s+=getL().get(i, j)+", ";
      }
      s+="\n";
    }
    System.out.println(s+"\n\n");
  }

  private void calcW() {
    W = new Matrix(G.getRowDimension(), 1, 0.0);
    W.set(W.getRowDimension()-1, 0, 1);
  }
  
  public static Complex64F mult( Complex64F a , Complex64F b , Complex64F result )
	{
		result.setReal(a.getReal()*b.getReal() -a.getImaginary()*b.getImaginary());
		result.setImaginary(a.getReal()*b.getImaginary()+b.getReal()*a.getImaginary());
    return result;
	}
  
  
  public static Complex64F mult( Complex64F a , double b , Complex64F result )
  {
    result.setReal(b*a.getReal());
    result.setImaginary(b*a.getImaginary());
    return result;
  }
  
  public static Complex64F mult( double a, Complex64F b, Complex64F result){
    return mult( b, a, result );
  }
  
  public static Complex64F div( Complex64F a, Complex64F b, Complex64F result ){
    double denominator = b.getReal()*b.getReal() + b.getImaginary()*b.getImaginary();
    result.setReal((a.getReal()*b.getReal()-a.getImaginary()*b.getImaginary())/denominator);
    result.setImaginary((-b.getImaginary()*a.getReal()+b.getReal()*a.getImaginary())/denominator);
    return result;
  }
  
  public static Complex64F div( Complex64F a, double b, Complex64F result ){
    result.setReal(a.getReal()/b);
    result.setImaginary(a.getImaginary()/b);
    return result;
  }
  
  public static Complex64F div( double a, Complex64F b, Complex64F result){
    double denominator = b.getReal()*b.getReal() + b.getImaginary()*b.getImaginary();
    result.setReal(a*b.getReal()/denominator);
    return result;
  }
  
  public static Complex64F add( Complex64F a , Complex64F b, Complex64F result )
  {
    result.setReal(a.getReal()+b.getReal());
    result.setImaginary(a.getImaginary()+b.getImaginary());
    return result;
  }
  
  public static Complex64F add( Complex64F a , double b , Complex64F result ){
    result.setReal(a.getReal()+b);
    return result;
  }
  
  public static Complex64F add(double a, Complex64F b, Complex64F result){
    return add(b,a,result);
  } 
  
  public static Complex64F sub(Complex64F a, Complex64F b, Complex64F result ){
    result.setReal(a.getReal()-b.getReal());
    result.setImaginary(a.getImaginary()-b.getImaginary());
    return result;
  }
  
  public static Complex64F sub(Complex64F a, double b, Complex64F result){
    result.setReal(a.getReal()-b);
    return result;
  }
  public static Complex64F sub(double a, Complex64F b, Complex64F result){
    result.setReal(a-b.getReal());
    return result;
  }
  
  /**
   *
   * @param L
   */
  public final void setL(Matrix L){
    this.L = L;
    
    if(this.G_LC == null){
      this.G_LC = new Matrix(2, 2);
    }

    //this.G_LC = new Matrix(new double[][] {{0.9934570, -L.get(0, 1)}, {0.0065215, 0.9934570-L.get(1, 1)}});
    this.G_LC.set(0, 0, 0.9934570);
    this.G_LC.set(0, 1, -L.get(0, 0));
    this.G_LC.set(1, 0, 0.0065215);
    this.G_LC.set(1,1, 0.9934570-L.get(1, 0));
    
    calcVInverse();
    calcW();
    double[] qc1 = new double[G.getColumnDimension()];
    Matrix VITimesW = VInverse.times(W);
        
    for(int i=0; i<G.getColumnDimension(); i++){
      qc1[i] = L.get(i,0);
      for(int j = i; j>0; j--){
        qc1[i] -= qc1[i-j]*VITimesW.get(j, 0);
      }
      qc1[i]/=VITimesW.get(0, 0);
    }
    
//    qc[0] = L.get(0, 0)/VITimesW.get(0, 0);
//    qc[1] = (L.get(1, 0) - qc[0]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
//    qc[2] = (L.get(2, 0) - qc[0]*VITimesW.get(2, 0) - qc[1]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
//    qc[3] = (L.get(3, 0) - qc[0]*VITimesW.get(3, 0) - qc[1]*VITimesW.get(2, 0) - qc[2]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
    
    DenseMatrix64F coef = new DenseMatrix64F(G.getRowDimension(), G.getColumnDimension());
    
    coef.set(0,0,1);
    for(int i=0;i<G.getColumnDimension();i++){
      Matrix powerA = matrixPow(G, i);
      for(int j=0;j<powerA.getColumnDimension(); j++){
        coef.set(j,i,powerA.get(j, 0));
      }
    }
    DenseMatrix64F x = new DenseMatrix64F(G.getColumnDimension(),1);
    DenseMatrix64F b = new DenseMatrix64F(G.getRowDimension(),1);
    
    Matrix powerA = matrixPow(G, G.getColumnDimension());
    for(int i=0; i<G.getRowDimension();i++){
      b.set(i, 0, qc1[i]-powerA.get(i, 0));
    }
    CommonOps.solve(coef, b, x);
    polynomial = new ArrayList<>();
    
    for(int i=0; i<x.numRows; i++){
      polynomial.add(x.get(i));
    }
    polynomial.add(1.0);
    findRoots(polynomial.toArray(new Double[polynomial.size()]));
  }
  
  /**
   *
   * @param poles
   */
  public final void setPoles(List<Complex64F> poles){
    
    this.poles = poles;
    
    calcPolynomial();
    calcQc();
    calcVInverse();
    calcW();
    calcL();
  }
  
  public double get$L0(){
    return this.estimated_levels.get(0, 0);
  }
  
  public double get$L1(){
    return this.estimated_levels.get(1,0);
  }

  /**
   * @return the L
   */
  public Matrix getL() {
    return L;
  }

  /**
   * @return the poles
   */
  public List<Complex64F> getPoles() {
    return poles;
  }
}
