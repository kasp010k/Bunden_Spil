package shoot_the_duck;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// Windows klassen. Min main.
public class Window extends JFrame{

    private Window()
    {
        // Titel til spillet.
        this.setTitle("Shoot the duck");

        // Sætter fuld skærm når spillet startes.
        if(true)
        {
            this.setUndecorated(true);
            this.setExtendedState(this.MAXIMIZED_BOTH);
        }
        // Window mode hvis spillet ikke kan køres i fuld skærm. Havde lidt problemer på en af mine maskiner med dette.
        else
        {
            // Window mode størrelse, belligenhed på skærmen og ikke re-sizeable.
            this.setSize(800, 600);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
        }

        // Lidt i tvivl, men handler nok om at lukke JFrame når spillet lukkes.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(new Framework());

        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}