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

    public void estimate(double l1, double l2, double usaturado) {
        double nivelTanqueSuperior = l1;
        double nivelTanqueInfeior = l2;
        double L[] = {ganho1, ganho2};
        double nivel_real[] = {nivelTanqueSuperior, nivelTanqueInfeior};

        double G[][] = new double[2][2];
        G[0][0] = 0.9934570;
        G[0][1] = 0;
        G[1][0] = 0.0065215;
        G[1][1] = 0.9934570;

        double H[] = new double[2];
        H[0] = 0.02954299;
        H[1] = 0.00009679;
        
          double estimado1 = G[0][0]*nivel_estimado[0] + G[0][1] * nivel_estimado[1] + L[0] * (nivelTanqueInfeior - nivel_estimado[1]) + H[0]*usaturado;
        double estimado2 = G[1][0]*nivel_estimado[0] + G[1][1] * nivel_estimado[1] + L[1] * (nivelTanqueInfeior - nivel_estimado[1]) + H[1]*usaturado;
        
        nivel_estimado[0] = estimado1;
        nivel_estimado[1] = estimado2;

        double nivelTanqueSuperiorEstiamdo = nivel_estimado[0];
        double nivelTanqueInferiorEstiamdo = nivel_estimado[1];
    }

    /**
     *
     * @param L
     */
    public final void setL(Matrix L_param) {
      
      ganho1 = L_param.get(0, 0);
      ganho2 = L_param.get(1, 0);
      
      double L1 = ganho1;
      double L2 = ganho2;
      double M[] = new double[2];
        M[0] = 154.33825;
        M[1] = 0;
        double L[] = new double[2];
        L[0] = L1;
        L[1] = L2;
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
        double G2xM[] = new double[2];
        for (int column = 0; column < 2; column++) {

            double aux = 0;

            for (int i = 0; i < 2; i++) {
                aux = aux + G2[column][i] * M[i];
            }

            G2xM[column] = aux;

        }

        double L_G2xM[] = new double[2];
        L_G2xM[0] = L[0] - G2xM[0];
        L_G2xM[1] = L[1] - G2xM[1];
        double GM[] = new double[2];
        for (int column = 0; column < 2; column++) {

            double aux = 0;

            for (int i = 0; i < 2; i++) {
                aux = aux + G[column][i] * M[i];
            }

            GM[column] = aux;

        }
        double alfa1 = L_G2xM[1] / GM[1];
        double alfa2 = (L_G2xM[0] - GM[0] * alfa1) / M[0];
        Complex P2;
        Complex P1;
        if (((alfa1 * alfa1) - (4 * alfa2)) < 0) {
            P2 = new Complex(-alfa1 / 2, Math.sqrt((4 * alfa2) - alfa1 * alfa1) / 2);
            P1 = P2.add(alfa1).multiply(-1);
        } else {
            P2 = new Complex((-alfa1 / 2) + Math.sqrt((alfa1 * alfa1) - (4 * alfa2)) / 2, 0);
            P1 = P2.add(alfa1).multiply(-1);
        }
        
        poles.get(0).set(P1.getReal(), P1.getImaginary());
        poles.get(1).set(P2.getReal(), P2.getImaginary());
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
