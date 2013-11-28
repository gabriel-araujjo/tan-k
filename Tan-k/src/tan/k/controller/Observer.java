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
import org.apache.commons.math3.complex.Complex;
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
    private double ganho1, ganho2;
    private double[] nivel_estimado = {0, 0};

    /**
     *
     * @param G
     * @param poles
     */
    public Observer(Matrix G, Matrix C, List<Complex64F> poles) {
        this(G, C);
        setPoles(poles);
    }

    public Observer(Matrix G, Matrix C, Matrix L) {
        this(G, C);
        setL(L);
    }

    public Observer(Matrix G, Matrix C) {
//        nivel_estimado = new double[]
        
        this.G = G;
        this.C = C;
        this.poles = new ArrayList<>();
        poles.add(new Complex64F(0, 0));
        poles.add(new Complex64F(0, 0));
        this.L = new Matrix(new double[][]{{0}, {0}});

        this.estimated_levels = new Matrix(new double[][]{{0}, {0}});
    }

    public void estimate(double l1, double l2) {
        double nivelTanqueSuperior = l1;
        double nivelTanqueInfeior = l2;
        double L[] = {ganho1, ganho2};
        double nivel_real[] = {nivelTanqueSuperior, nivelTanqueInfeior};

        double erro_nivel[] = new double[2];
        erro_nivel[0] = nivel_estimado[0] - nivel_real[0];
        erro_nivel[1] = nivel_estimado[1] - nivel_real[1];

        double LC[][] = new double[2][2];
        LC[0][0] = 0;
        LC[0][1] = L[0];
        LC[1][0] = 0;
        LC[1][1] = L[1];

        double G[][] = new double[2][2];
        G[0][0] = 0.9934570;
        G[0][1] = 0;
        G[1][0] = 0.0065215;
        G[1][1] = 0.9934570;

        double G_LC[][] = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                G_LC[i][j] = G[i][j] - LC[i][j];
            }
        }

        for (int column = 0; column < 2; column++) {
            double aux = 0;

            for (int i = 0; i < 2; i++) {
                aux = aux + G_LC[column][i] * erro_nivel[i];
            }

            nivel_estimado[column] = aux;
        }

        double nivelTanqueSuperiorEstiamdo = nivel_estimado[0];
        double nivelTanqueInferiorEstiamdo = nivel_estimado[1];
    }

    /**
     *
     * @param L
     */
    public final void setL(Matrix L) {
//    this.L = L;
//    
//    if(this.G_LC == null){
//      this.G_LC = new Matrix(2, 2);
//    }
//
//    //this.G_LC = new Matrix(new double[][] {{0.9934570, -L.get(0, 1)}, {0.0065215, 0.9934570-L.get(1, 1)}});
//    this.G_LC.set(0, 0, 0.9934570);
//    this.G_LC.set(0, 1, -L.get(0, 0));
//    this.G_LC.set(1, 0, 0.0065215);
//    this.G_LC.set(1,1, 0.9934570-L.get(1, 0));
//    
////    calcVInverse();
////    calcW();
//    double[] qc1 = new double[G.getColumnDimension()];
//    Matrix VITimesW = VInverse.times(W);
//        
//    for(int i=0; i<G.getColumnDimension(); i++){
//      qc1[i] = L.get(i,0);
//      for(int j = i; j>0; j--){
//        qc1[i] -= qc1[i-j]*VITimesW.get(j, 0);
//      }
//      qc1[i]/=VITimesW.get(0, 0);
//    }
//    
////    qc[0] = L.get(0, 0)/VITimesW.get(0, 0);
////    qc[1] = (L.get(1, 0) - qc[0]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
////    qc[2] = (L.get(2, 0) - qc[0]*VITimesW.get(2, 0) - qc[1]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
////    qc[3] = (L.get(3, 0) - qc[0]*VITimesW.get(3, 0) - qc[1]*VITimesW.get(2, 0) - qc[2]*VITimesW.get(1, 0)) / VITimesW.get(0, 0);
//    
//    DenseMatrix64F coef = new DenseMatrix64F(G.getRowDimension(), G.getColumnDimension());
//    
//    coef.set(0,0,1);
//    for(int i=0;i<G.getColumnDimension();i++){
//      Matrix powerA = matrixPow(G, i);
//      for(int j=0;j<powerA.getColumnDimension(); j++){
//        coef.set(j,i,powerA.get(j, 0));
//      }
//    }
//    DenseMatrix64F x = new DenseMatrix64F(G.getColumnDimension(),1);
//    DenseMatrix64F b = new DenseMatrix64F(G.getRowDimension(),1);
//    
//    Matrix powerA = matrixPow(G, G.getColumnDimension());
//    for(int i=0; i<G.getRowDimension();i++){
//      b.set(i, 0, qc1[i]-powerA.get(i, 0));
//    }
//    CommonOps.solve(coef, b, x);
//    polynomial = new ArrayList<>();
//    
//    for(int i=0; i<x.numRows; i++){
//      polynomial.add(x.get(i));
//    }
//    polynomial.add(1.0);
//    findRoots(polynomial.toArray(new Double[polynomial.size()]));
    }

    /**
     *
     * @param poles
     */
    public final void setPoles(List<Complex64F> poles) {
        double real1 = poles.get(0).getReal();
        double real2 = poles.get(1).getReal();
        double imagionario1 = poles.get(0).getImaginary();
        double imagionario2 = poles.get(1).getImaginary();
        Complex polo1 = new Complex(real1, imagionario1);
        Complex polo2 = new Complex(real2, imagionario2);
        Complex alpha1 = polo1.add(polo2);
        alpha1 = alpha1.multiply(-1);
        Complex alpha2 = polo1.multiply(polo2);

        double G[][] = new double[2][2];
        G[0][0] = 0.9934570;
        G[0][1] = 0;
        G[1][0] = 0.0065215;
        G[1][1] = 0.9934570;
        double G2[][] = new double[2][2];
        for (int row = 0; row < G.length; row++) // multiplicação das matrizes
        {
            for (int column = 0; column < G[row].length; column++) {
                double aux = 0;

                for (int i = 0; i < G[row].length; i++) {

                    try {

                        aux = aux + G[row][i] * G[i][column];

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }

                G2[row][column] = aux;

            }
        }
        Complex alpha1xG[][] = new Complex[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                alpha1xG[i][j] = alpha1.multiply(G[i][j]);
            }
        }
        Complex alpha2xI[][] = new Complex[2][2];
        alpha2xI[0][0] = alpha2;
        alpha2xI[1][0] = alpha2.multiply(0);
        alpha2xI[0][1] = alpha2.multiply(0);
        alpha2xI[1][1] = alpha2;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                alpha1xG[i][j] = alpha1xG[i][j].add(alpha2xI[i][j]);
                alpha1xG[i][j] = alpha1xG[i][j].add(G2[i][j]);
                System.out.print(alpha1xG[i][j].toString() + " ");
            }
            System.out.print("\n");
        }

        double M[] = new double[2];
        M[0] = 154.33825;
        M[1] = 0;
        Complex L1;
        Complex L2;
        L1 = alpha1xG[0][0].multiply(M[0]);
        L2 = alpha1xG[1][0].multiply(M[0]);

        ganho1 = L1.getReal();
        ganho2 = L2.getReal();
    }

    public double get$L0() {
        return nivel_estimado[0];
//        return this.estimated_levels.get(0, 0);
    }

    public double get$L1() {
        return nivel_estimado[1];
//        return this.estimated_levels.get(1, 0);
    }

    /**
     * @return the L
     */
    public Matrix getL() {
        
        return new Matrix(new double[][]{{ganho1},{ganho2}});
    }

    /**
     * @return the poles
     */
    public List<Complex64F> getPoles() {
        return poles;
    }
}
