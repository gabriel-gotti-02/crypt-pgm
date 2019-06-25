import javax.swing.JFrame; //utilisation de SWING
import java.io.*;
public class Main {
    public static void main(String args[]){
            JFrame frame = new JFrame("CryptPGM - par MAUR"); //creation de la JFRAME (qui contiendra JPANEL puis les boutons)
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fermeture si on appui sur la croix
            frame.getContentPane().add(new Fenetre());
            frame.pack(); //groupage de la frame
            frame.setVisible(true); //visible
    }
}