/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author Martin_Notebook
 */
public interface IMessageReceiver {
    
    boolean Available();
    void SendMessage(Message mess);
}
