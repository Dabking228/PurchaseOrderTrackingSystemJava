package user_interface;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfirmPane extends JOptionPane{
    protected int result; 
    public ConfirmPane(String msg, String title, JPanel panel, int option){
        result = JOptionPane.showConfirmDialog(panel, msg, title, option);
    }

    public int getResult(){
        return result;
    }
}
