package shoot_the_duck;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// Duck klassen.

public class Duck {

    // Tid mellem "spawning" af en ny and.
    public static long timeBetweenDucks = Framework.secInNanosec / 2;

    // Sidste gang en and var "spawned".
    public static long lastDuckTime = 0;

    // Detaljer om de fire andelinjer. Først deres "spawn"linje, så deres hastighed og deres pointværdi.
    public static int[][] duckLines = {
            {Framework.frameWidth, (int)(Framework.frameHeight * 0.60), -2, 20},
            {Framework.frameWidth, (int)(Framework.frameHeight * 0.65), -3, 30},
            {Framework.frameWidth, (int)(Framework.frameHeight * 0.70), -4, 40},
            {Framework.frameWidth, (int)(Framework.frameHeight * 0.78), -5, 50}
    };

    // Variabel for næste and.
    public static int nextDuckLines = 0;


    // Variabel for koordinater til anden.
    public int x;

    public int y;

    // Variabel for andens hastighed
    private int speed;

    // Variabel for andens pointværdi.
    public int score;

    // Variabel for det billede anden bruger fra mine assets.
    private BufferedImage duckImg;


    // Constructor til en ny and med de tidligere variabler.
    public Duck(int x, int y, int speed, int score, BufferedImage duckImg)
    {
        this.x = x;
        this.y = y;

        this.speed = speed;

        this.score = score;

        this.duckImg = duckImg;
    }


    // Metode til bevægelse af anden over skærmen.
    public void Update()
    {
        x += speed;
    }

    // Andens billede på skærmen. Ikke 100% sikker på denne kodes opsætning.
    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(duckImg, x, y, null);
    }
}