package user_interface;

import javax.swing.JPanel;
import backend.Backend;

public class MainMenu extends JPanel {
    Backend backend;
    UserInterface userInterface;

    public MainMenu(Backend backend, UserInterface userInterface) {
        this.backend = backend;
        this.userInterface = userInterface;
        initComponents();
    }

    private void initComponents() {
        
    }
}
