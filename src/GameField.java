import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int Size = 320;
    private final int Dot_Size = 16;
    private final int All_Dots = 400;
    private Image Dot;
    private Image Apple;
    private int AppleX;
    private int AppleY;
    private int[] x = new int[All_Dots];
    private int[] y = new int[All_Dots];
    private int Dots;
    private Timer timer;
    private boolean Left = false;
    private boolean Right = true;
    private boolean Up = false;
    private boolean Down = false;
    private boolean InGame = true;
    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        InitGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }
    public void InitGame() {
        Dots = 3;
        for(int i = 0;i < Dots;i ++) {
            x[i] = 48 - i * Dot_Size;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }
    public void createApple() {
        AppleX = new Random().nextInt(20)*Dot_Size;
        AppleY = new Random().nextInt(20)*Dot_Size;
    }
    public void loadImages() {
        ImageIcon AppleImage = new ImageIcon("apple.png");
        Apple = AppleImage.getImage();
        ImageIcon DotImage = new ImageIcon("dot.png");
        Dot = DotImage.getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(InGame) {
            g.drawImage(Apple,AppleX,AppleY,this);
            for (int i = 0; i < Dots; i++) {
                g.drawImage(Dot, x[i],y[i],this);
            }
        }else  {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str,125, Size / 2);
        }
    }
    public void move() {
        for(int i = Dots;i > 0;i --) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(Left) {
           x[0] -= Dot_Size;
        }
        if(Right) {
            x[0] += Dot_Size;
        }
        if(Up) {
            y[0] -= Dot_Size;
        }
        if(Down) {
            y[0] += Dot_Size;
        }

    }
    public void checkApple() {
        if(x[0] == AppleX && y[0] == AppleY) {
            Dots ++;
            createApple();
        }
    }
    public void checkCollisions() {
        for (int i = Dots; i > 0; i--) {
            if(i > 4  && x[0] == x[i] && y[0] == y[i]) {
                InGame = false;
            }
        }
        if(x[0] > Size) {
            InGame = false;
        }
        if(x[0] < 0) {
            InGame = false;
        }
        if(y[0] > Size) {
            InGame = false;
        }
        if(y[0] < 0) {
            InGame = false;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(InGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !Right) {
                Left = true;
                Up = false;
                Down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !Left) {
                Right = true;
                Up = false;
                Down = false;
            }
            if(key == KeyEvent.VK_UP && !Down) {
                Up = true;
                Right = false;
                Left = false;
            }
            if(key == KeyEvent.VK_DOWN && !Up) {
                Down = true;
                Left = false;
                Right = false;
            }
        }
    }
}
