import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class WelcomeFrame {
    private WelcomePanel panel;

    public WelcomeFrame() {
        JFrame frame = new JFrame("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);

        panel = new WelcomePanel(frame);
        frame.add(panel);

        frame.setVisible(true);
    }
}
