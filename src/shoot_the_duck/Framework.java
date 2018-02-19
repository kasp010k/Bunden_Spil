package shoot_the_duck;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

// Framework klassen.

public class Framework extends Canvas {

    // Højde og bredde af rammen.
    public static int frameWidth;

    public static int frameHeight;

    // Sekund til nanosekund.
    public static final long secInNanosec = 1000000000L;

    // Millisekund til nanosekund.
    public static final long milisecInNanosec = 1000000L;

    // Frames per second. Hvor mange gange i sekundet spillet opdateres.
    private final int GAME_FPS = 60;

    // Pause mellem opdateringer i nanosekunder.
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;

    // "Game state" enum.
    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER}

    public static GameState gameState;

    // Variabel til brugt tid.
    private long gameTime;
    // Variabel til brug til at udregne brugt tid.
    private long lastTime;

    // Variabel til spillet.
    private Game game;


    // Variabel til billede for start menu.
    private BufferedImage shootTheDuckMenuImg;

    // Er usikker på denne kode.
    public Framework ()
    {
        super();

        gameState = GameState.VISUALIZING;

        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }


    private void Initialize()
    {

    }

    // Metode til at hente "menu" billedet fra mine assets.
    private void LoadContent()
    {
        try
        {
            URL shootTheDuckMenuImgUrl = this.getClass().getResource("/resources/images/menu.jpg");
            shootTheDuckMenuImg = ImageIO.read(shootTheDuckMenuImgUrl);
        }
        catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Har mange usikkerheder om den følgende kode. Har indsat kommentare hist og her.
    private void GameLoop()
    {
        // Variabel med sammenhæng mellem enum listen og tiden der bruges i spillet.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();

        long beginTime, timeTaken, timeLeft;

        while(true)
        {
            beginTime = System.nanoTime();

            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;

                    game.UpdateGame(gameTime, mousePosition());

                    lastTime = System.nanoTime();
                    break;
                case GAMEOVER:
                    //...
                    break;
                case MAIN_MENU:
                    //...
                    break;
                case OPTIONS:
                    //...
                    break;
                case GAME_CONTENT_LOADING:
                    //...
                    break;
                case STARTING:
                    Initialize();
                    // Henter filer, billeder og evt lyd.
                    LoadContent();

                    // Ændring a "gamestate" til "Main menu" fra enum listen.
                    gameState = GameState.MAIN_MENU;
                    break;
                case VISUALIZING:

                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();

                        // Skift i "Game state".
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                    break;
            }

            repaint();

            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec;

            if (timeLeft < 10)
                timeLeft = 10;
            try {
                Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }

    // Billede på skærmen under de forskellige enum states. Ved Main Menu gives instruktioner til at fortsætte eller forlade spillet.
    @Override
    public void Draw(Graphics2D g2d)
    {
        switch (gameState)
        {
            case PLAYING:
                game.Draw(g2d, mousePosition());
                break;
            case GAMEOVER:
                game.DrawGameOver(g2d, mousePosition());
                break;
            case MAIN_MENU:
                g2d.drawImage(shootTheDuckMenuImg, 0, 0, frameWidth, frameHeight, null);
                g2d.drawString("Use left mouse button to shot the duck.", frameWidth / 2 - 83, (int)(frameHeight * 0.65));
                g2d.drawString("Click with left mouse button to start the game.", frameWidth / 2 - 100, (int)(frameHeight * 0.67));
                g2d.drawString("Press ESC any time to exit the game.", frameWidth / 2 - 75, (int)(frameHeight * 0.70));
                g2d.setColor(Color.white);
                break;
            case OPTIONS:
                break;
            case GAME_CONTENT_LOADING:
                g2d.setColor(Color.white);
                g2d.drawString("GAME is LOADING", frameWidth / 2 - 50, frameHeight / 2);
                break;
        }
    }

    // Metode til start af spillet.
    private void newGame()
    {
        // Sæt a spiltid til nul til senere brug.
        gameTime = 0;
        lastTime = System.nanoTime();

        game = new Game();
    }

    // Restart game metode.
    private void restartGame()
    {
        // Sæt a spiltid til nul til senere brug.
        gameTime = 0;
        lastTime = System.nanoTime();

        game.RestartGame();

        // Ændring i game state fra enum listen.
        gameState = GameState.PLAYING;
    }

    // Sætter musemarkøren i vinduet.
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();

            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }

    // Metode til keyboard listener fra Canvas klassen.
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
        switch (gameState)
        {
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    System.exit(0);
                else if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
                    restartGame();
                break;
            case PLAYING:
            case MAIN_MENU:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    System.exit(0);
                break;
        }
    }

    // Metode til mouselistener fra Canvas klassen.
    @Override
    public void mouseClicked(MouseEvent e)
    {
        switch (gameState)
        {
            case MAIN_MENU:
                if(e.getButton() == MouseEvent.BUTTON1)
                    newGame();
                break;
        }
    }
}