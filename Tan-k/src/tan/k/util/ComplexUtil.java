/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.util;

import org.ejml.data.Complex64F;

/**
 *
 * @author gabriel
 */
public class ComplexUtil {
  public static Complex64F mult(Complex64F A, Complex64F B){
    return new Complex64F(A.getReal()*B.getReal() -A.getImaginary()*B.getImaginary(), A.getReal()*B.getImaginary() + A.getImaginary()*B.getImaginary());
  }
  
  public static Complex64F add(Complex64F A, Complex64F B){
    return new Complex64F(A.getReal()+B.getReal(), A.getImaginary()+B.getImaginary());
  }
}
