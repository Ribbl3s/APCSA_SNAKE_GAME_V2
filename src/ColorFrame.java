import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ColorFrame {
    private ColorPanel panel;

    public ColorFrame(String str) {
        JFrame frame = new JFrame("Customization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);

        panel = new ColorPanel(frame, str);
        frame.add(panel);

        frame.setVisible(true);
    }
}