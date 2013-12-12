/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.controller;

import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.factory.EigenDecomposition;
import org.ejml.ops.CommonOps;
import tan.k.util.ComplexUtil;

/**
 *
 * @author gabriel
 */
public class FollowReference {
  double r; //Nível do tanque em centímetros
  
  double k1; //ganhos
  double[] k2 = new double[2];
  double p1, p2, p3; //polos
  
  //Valores reais
  
  double[][] GA = {{  0.993457,        0, 0.02954299},  // [G H]  --> G aumentada
                   { 0.0065215, 0.993457, 0.00009679},  // [0 0]
                   {         0,        0,          0}};
  double[][] HA =  {{0},  //  --> H aumentada
                    {0},  //
                    {1}}; //
  
  double[][] alphaI = {{ -0.5007296865, 151.8365656,  1.000009712},  // [G-I   H]^-1
                       {             0,        -1.0,          1.0},  // [CG   CH]
                       {   33.73807884,  33.6278301, 0.2214760099}};
  
  double[][] alpha = {{ -0.0065430000,             0, 0.0295429900},
                      {  0.0065215000, -0.0065430000, 0.0000967900},
                      {  0.0065215000,  0.9934570000, 0.0000967900}};
  
  double[][] wcInv = {{              0,                0, 1.0},    // Wc inversa     
                      {  50.7426148542, -5156.4062735073,   0},
                      { -17.0049005469,  5190.3668437660,   0}};
  
  // Valores da prova 3 - para testar se funciona
//  double[][] GA = {{  2.7183,    5.4366, 6.000},  // [G H]  --> G aumentada
//                   { 0.000, 2.7183, 5.1584},  // [0 0]
//                   {         0,        0,          0}};
//  double[][] HA =  {{0},  //  --> H aumentada
//                    {0},  //
//                    {1}}; //
//  
//  double[][] alphaI = {{ -15.2378, -11.9035,  2},  // [G-I   H]^-1
//                       {   7.9099,   5.6130, -1.0},  // [CG   CH]
//                       {  -2.6366,  -1.6770,  0.3333}};
//  
//  double[][] alpha = {{  1.7183,  5.4366,  6},
//                      {  0     ,  1.7183,  5.1548},
//                      { 13.5914, 51.6474, 76,3936}};
//  
//  double[][] wcInv = {{              0,                0, 1.0},    // Wc inversa     
//                      {  -0.0970, 0.3069,   0},
//                      { 0.0357,  -0.0415,   0}};
  
  private double u;
  private double v;
  
  
  public FollowReference(){
    
  }
  
  /**
   * Calcula a tensão a ser enviada para a bomba a partir dos níveis
   * do tanque 1 e 2 e da referência desejada..
   * 
   * @param l1 O nível medido no tanque 1
   * @param l2 O nível medido no tanque 2
   * @param r A referência
   */
  public void calcU(double l1, double l2, double r){
    double[] x = {l1, l2}; // x é um vetor coluna [l1; l2]
    double y = l2; // y é o tanque 2
    
    double e = r - y;
    
    v = v + e;
    
    u = -(k2[0]*x[0] + k2[1]*x[1]) + k1*v;
  }
  
  public void setPoles(Complex64F p1, Complex64F p2, Complex64F p3){
    //double qc[] = {-p1*p2*p3, p1*p2+p2*p3+p1*p3, -(p1+p2+p3), 1};
    
    double[] qc = new double[4];
    
    qc[0] =  -(ComplexUtil.mult(ComplexUtil.mult(p1, p2), p3)).getReal();
    qc[1] =  ComplexUtil.add(ComplexUtil.add(ComplexUtil.mult(p1, p2), ComplexUtil.mult(p1, p3)), ComplexUtil.mult(p2, p3)).getReal();
    qc[2] =  -(ComplexUtil.add(ComplexUtil.add(p1, p2), p3)).getReal();
    qc[3] =  1.0;
    
    
    double[][] parte0,parte1,parte2,parte3;
    
    parte0 = new double[][] {{ qc[0],     0,     0},
                             {     0, qc[0],     0},
                             {     0,     0, qc[0]}};
    
    parte1 = new double[3][3];
    parte2 = multMatix(GA, GA);
    parte3 = multMatix(parte2, GA);
    
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        parte1[i][j] = qc[1]*GA[i][j];
        parte2[i][j] = qc[2]*parte2[i][j];
        parte3[i][j] = qc[3]*parte3[i][j];
      }
    }
    
    double[][] qcGA = new double[3][3];
    
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        qcGA[i][j] = parte0[i][j] +parte1[i][j]+parte2[i][j]+parte3[i][j];
      }
    }
    
    double[][] K_chapeu = multMatix(new double[][]{wcInv[2]}, qcGA);
    
    K_chapeu[0][2] +=1;
    
    double[] K = multMatix(K_chapeu, alphaI)[0];
    
    k1 = K[2];
    k2[0] = K[0];
    k2[1] = K[1];
    
    System.out.println("k1 = "+k1);
    System.out.println("k2 = ["+k2[0]+", "+k2[1]+"]");
  }
  
  public void setGains(double k1, double[] k2){
    double[][][] G = new double[4][3][3];
    G[0] = new double[][] {{1,0,0},{0,1,0},{0,0,1}};  // GA^0 identidade
    G[1] = GA;                                        // GA^1 GA
    G[2] = multMatix(GA, GA);                         // GA^2 GA*GA
    G[3] = multMatix(G[2], GA);                       // GA^3 GA^2 * GA
    
    double[] WI3 = wcInv[2];                          // terceira linha de Wc inversa 
    
    double[][] A = new double[][]{{0,0,0},{0,0,0},{0,0,0}};// A no sistema linear Ax = Y
    double[] Y = multMatix(new double[][] {{k2[0], k2[1], k1}}, alpha)[0];                // Y no sistema linear Ax = Y
    
    Y[2] = Y[2]-1;
    
    for(int i=0;i<3;i++){  // itera os y1, y2 e y3]
      for(int j=0;j<3;j++){ // itera Ga3 
        Y[i] -= WI3[j]*G[3][j][i];
      }
    }
    System.out.println("Y = ["+Y[0]+", "+Y[1]+", "+Y[2]+"]");
    System.out.println("A =");
    for(int i=0;i<3;i++){ //itera linhas de A
      System.out.print("[");
      for(int j=0; j<3;j++){ // itera colunas de A
        for(int k=0; k<3; k++){ // itera os G
          A[i][j] += G[i][k][j]*WI3[k];
        }
        System.out.print(A[i][j]+", ");
      }
      System.out.print(";");
    }
    System.out.println("]");
    
    
    // A partir daqui tá correto
    double[][] YColumn = new double[3][1];
    YColumn[0][0] = Y[0];
    YColumn[1][0] = Y[1];
    YColumn[2][0] = Y[2];
    
    DenseMatrix64F AMatrix = new DenseMatrix64F(A);
    DenseMatrix64F YMatrix = new DenseMatrix64F(YColumn);
    DenseMatrix64F x = new DenseMatrix64F(3,1);
    
    CommonOps.solve(AMatrix,YMatrix,x);
    
    System.out.println(
            "alpha = "+x.get(2, 0)+
            "\nbeta = "+x.get(1,0)+
            "\ngamma = "+x.get(0,0)
    );
    
    DenseMatrix64F c = new DenseMatrix64F(3,3);
    
    c.set(0, 0, 0);
    c.set(0, 1, 1);
    c.set(0, 2, 0);
    
    c.set(1, 0, 0);
    c.set(1, 1, 0);
    c.set(1, 2, 1);
    
    c.set(2, 0, -x.get(0,0));
    c.set(2, 1, -x.get(1,0));
    c.set(2, 2, -x.get(2,0));
    
    
    EigenDecomposition<DenseMatrix64F> evd = DecompositionFactory.eig(3, false);
    
    evd.decompose(c);
    
    Complex64F[] roots = new Complex64F[3];
    
    for(int i=0;i<3;i++){
      roots[i] = evd.getEigenvalue(i);
      System.out.println("x"+i+" = "+roots[i].getReal()+" + i"+roots[i].getImaginary());
    }
  }
  
  private double[][] multMatix(double[][] A, double[][] B){
    double[][] out = new double[A.length][B[0].length];
    System.out.println(A.length);
    System.out.println(B[0].length);
    
    for(int i=0; i<A.length;i++){
      for(int j=0; j<B[0].length; j++){
        out[i][j] = 0;
        for(int k = 0; k<B.length; k++){
          out[i][j] += A[i][k]*B[k][j];
        }
      }
    }
    return out;
  }
  
}
