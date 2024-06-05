import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int NUM_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    private String name;
    private String colorOption;

    final int x[] = new int[NUM_UNITS];
    final int y[] = new int[NUM_UNITS];
    int bodyParts = 6;  // initial body parts
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    Image appleImage;

    // sound effects
    private Clip eatingSound;
    private Clip oofSound;
    private Clip gameplayMusic;

    private BufferedImage grass;

    GamePanel(String str, String color) {
        try {
            grass = ImageIO.read(new File("src/grass.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        name = str;
        colorOption = color;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        appleImage = new ImageIcon("apple.png").getImage();
    }

    public void startGame() {
        playGameplayMusic();
        placeApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(grass, 0, 0, null);
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.drawImage(appleImage, appleX, appleY, UNIT_SIZE, UNIT_SIZE, this);

            for (int i = 0; i < bodyParts; i++) {
                if (colorOption.equals("red")) {
                    if (i == 0) {
                        g.setColor(new Color(180, 0, 45));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(Color.red);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                } else if (colorOption.equals("blue")) {
                    if (i == 0) {
                        g.setColor(new Color(0, 45, 180));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(Color.blue);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                } else if (colorOption.equals("purple")) {
                    if (i == 0) {
                        g.setColor(new Color(75,0,130));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(128,0,128));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                } else {
                    if (i == 0) {
                        g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(name + " : " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            playOofSound();
            gameOver(g);
        }
    }

    public void placeApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            playEatSound();
            bodyParts++;
            applesEaten++;
            placeApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        gameplayMusic.stop();
        gameplayMusic.close();
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Click 'R' to play again!",(SCREEN_WIDTH - metrics3.stringWidth("Press 'R' to play again"))/2,SCREEN_HEIGHT/2 + 70);
    }

    public void restartGame() {
        gameplayMusic.stop();
        gameplayMusic.close();
        setVisible(false);
        new GameFrame(name, colorOption);

    }

    public void dispose() {
        JFrame parent = (JFrame) this.getTopLevelAncestor();
        parent.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_A:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    restartGame();
                    System.out.println("restart");
                    break;
            }
        }
    }

    // sound methods
    public void playEatSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/eat.wav").getAbsoluteFile());
            eatingSound = AudioSystem.getClip();
            eatingSound.open(audioInputStream);
            eatingSound.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void playOofSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/steve.wav").getAbsoluteFile());
            eatingSound = AudioSystem.getClip();
            eatingSound.open(audioInputStream);
            eatingSound.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void playGameplayMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/BPT.wav").getAbsoluteFile());
            gameplayMusic = AudioSystem.getClip();
            gameplayMusic.open(audioInputStream);
            gameplayMusic.loop(Clip.LOOP_CONTINUOUSLY);
            gameplayMusic.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}