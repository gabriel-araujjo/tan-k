/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.view;

/**
 *
 * @author gabriel
 */
public interface ChangeParameterEvent extends Runnable{
  
  public void onChangeParameter(Object value);
  
}
