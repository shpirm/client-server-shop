package Interface.GUI;

import javax.swing.*;

public class CurrentPanel {

    private static CurrentPanel instance;
    private CurrentPanel() {}

    private JPanel panel;
    public static CurrentPanel getInstance(){
        if (instance == null) {
            instance = new CurrentPanel();
        }
        return instance;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }
}
