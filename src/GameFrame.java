import javax.swing.JFrame;
public class GameFrame extends JFrame {

    GameFrame(String str, String color){
        this.add(new GamePanel(str, color));
        this.setTitle("Benny Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
