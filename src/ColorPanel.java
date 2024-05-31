import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ColorPanel extends JPanel implements ActionListener {
    private JButton redButton;
    private JButton blueButton;
    private JButton greenButton;
    private JButton randomButton;
    private JFrame enclosingFrame;
    private String color;
    private String name;

    public ColorPanel(JFrame frame, String str) {
        name = str;
        enclosingFrame = frame;
        redButton = new JButton("Red");
        blueButton = new JButton("Blue");
        greenButton = new JButton("Green");
        randomButton = new JButton("Random?");
        add(redButton);
        add(blueButton);
        add(greenButton);
        add(randomButton);
        redButton.addActionListener(this);
        blueButton.addActionListener(this);
        greenButton.addActionListener(this);
        randomButton.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.RED);
        g.drawString("Hello" + name + ", please choose a snake color", 50, 30);

        redButton.setLocation(50, 100);
        blueButton.setLocation(100, 100);
        greenButton.setLocation(150, 100);
        randomButton.setLocation(200, 100);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button == redButton) {
                color = "red";
            } else if (button == blueButton){
                color = "blue";
            } else if (button == greenButton) {
                color = "green";
            } else {
                color = "random";
            }
            GameFrame hi = new GameFrame(name, "red");
            enclosingFrame.setVisible(false);
        }
    }
}
