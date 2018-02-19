package shoot_the_duck;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

// Laver et JPanel der implementerer KeyListerner og MouseListener så spillet kan startes med et klik med musen eller afsluttes med en tastatur tryk.

public abstract class Canvas extends JPanel implements KeyListener, MouseListener {

    private static boolean[] keyboardState = new boolean[525];

    private static boolean[] mouseState = new boolean[3];


    public Canvas()
    {
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);

        // Fjerner musemarkør, så man kan lave sin egen.
        if(true)
        {
            BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
            this.setCursor(blankCursor);
        }

        // Tilføjer KeyListener til JPanel.
        this.addKeyListener(this);
        // Tilføjer MouseListener til JPanel.
        this.addMouseListener(this);
    }


    // Denne metode overskrives i Framework klassen. Ikke 100% sikker på hvorfor den skal bruges.
    public abstract void Draw(Graphics2D g2d);

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        Draw(g2d);
    }


    // Tjekker om keyboard bliver brugt.

    public static boolean keyboardKeyState(int key)
    {
        return keyboardState[key];
    }

    // Metode til keyboard listener.
    @Override
    public void keyPressed(KeyEvent e)
    {
        keyboardState[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keyboardState[e.getKeyCode()] = false;
        keyReleasedFramework(e);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    public abstract void keyReleasedFramework(KeyEvent e);


    // Tjekker om musetast bruges i forhold til tidligerer mouselistener opsætning.

    public static boolean mouseButtonState(int button)
    {
        return mouseState[button - 1];
    }

    // Mouse key status.
    private void mouseKeyStatus(MouseEvent e, boolean status)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            mouseState[0] = status;
        else if(e.getButton() == MouseEvent.BUTTON2)
            mouseState[1] = status;
        else if(e.getButton() == MouseEvent.BUTTON3)
            mouseState[2] = status;
    }

    // Metoder til MouseListener.
    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseKeyStatus(e, true);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseKeyStatus(e, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

}