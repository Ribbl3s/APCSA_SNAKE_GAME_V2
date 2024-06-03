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

public class WelcomePanel extends JPanel implements ActionListener {
    private JTextField textField;
    private JButton submitButton;
    private JButton clearButton;
    private JFrame enclosingFrame;
    private BufferedImage snake;

    // sound track
    private Clip welcomeMusic;

    public WelcomePanel(JFrame frame) {
        enclosingFrame = frame;
        try {
            snake = ImageIO.read(new File("src/snake.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        textField = new JTextField(10);
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        add(textField);
        add(submitButton);
        add(clearButton);
        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        playBackgroundMusic();
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.RED);
        g.drawString("Please enter your name:", 50, 30);
        g.drawImage(snake, 200, 50, null);
        textField.setLocation(50, 50);
        submitButton.setLocation(50, 100);
        clearButton.setLocation(150, 100);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button == submitButton) {
                String playerName = textField.getText();
                ColorFrame frame = new ColorFrame(playerName);
                enclosingFrame.setVisible(false);
            } else {
                textField.setText("");
            }
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
