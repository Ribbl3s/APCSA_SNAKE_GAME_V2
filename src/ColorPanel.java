import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ColorPanel extends JPanel implements ActionListener {
    private JButton redButton;
    private JButton blueButton;
    private JButton purpleButton;
    private JButton randomButton;
    private JFrame enclosingFrame;
    private String color;
    private String name;

    // sound effects
    private Clip welcomeMusic;

    public ColorPanel(JFrame frame, String str) {
        name = str;
        enclosingFrame = frame;
        redButton = new JButton("Red");
        blueButton = new JButton("Blue");
        purpleButton = new JButton("Purple");
        randomButton = new JButton("Random?");
        add(redButton);
        add(blueButton);
        add(purpleButton);
        add(randomButton);
        redButton.addActionListener(this);
        blueButton.addActionListener(this);
        purpleButton.addActionListener(this);
        randomButton.addActionListener(this);
        playBackgroundMusic();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.RED);
        g.drawString("Hello " + name + ", please choose a snake color:", 50, 30);

        redButton.setLocation(50, 100);
        blueButton.setLocation(150, 100);
        purpleButton.setLocation(250, 100);
        randomButton.setLocation(350, 100);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button == redButton) {
                color = "red";
            } else if (button == blueButton){
                color = "blue";
            } else if (button == purpleButton) {
                color = "purple";
            } else {
                color = "random";
            }
            welcomeMusic.stop();
            welcomeMusic.close();
            GameFrame hi = new GameFrame(name, color);
            enclosingFrame.setVisible(false);
        }
    }

    public void playBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/SBT.wav").getAbsoluteFile());
            welcomeMusic = AudioSystem.getClip();
            welcomeMusic.open(audioInputStream);
            welcomeMusic.loop(Clip.LOOP_CONTINUOUSLY);
            welcomeMusic.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
