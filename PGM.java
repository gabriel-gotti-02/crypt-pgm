import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PGM {
    private ShortPixmap img; //image contenant dimensions et pixels
    private String chemin; //

    //Constructeurs
    public PGM(){
        this.img = null;
        this.chemin = null;
    }

    public PGM(String chemin){
        try {
            this.chemin=new String(chemin);
            this.img = new ShortPixmap(this.chemin);
        } catch(IOException e){
            System.err.println(e);
        }
    }

    //Getters
    public String getChemin(){
        return this.chemin;
    }

    public ShortPixmap getImg(){
        return this.img;
    }

    //Setters
    public void setChemin(String chemin){
        this.chemin = chemin;
    }

    //Méthodes
    public void crypter(String out) {
        int largeur = this.img.width;
        int hauteur = this.img.height;
        int taille = this.img.size; //= largeur*hauteur

        short[][] tabImage = new short[largeur][hauteur];

        int k=0;
        for(int i = 0; i < hauteur; i++){ //balayage de haut en bas
            for(int j = 0; j < largeur; j++){
                tabImage[i][j]=img.getShorts()[k]; //on prend le kième pixel (type short)
                k++; //k suivant...
            }
        }

        File fichierImg = new File(out);

        try{
            PrintWriter ecriture = new PrintWriter(fichierImg); //on place un printwriter sur fichierImg
            ecriture.println(largeur+" "+hauteur); //on met en premiere ligne les dimensions de l'image

            for(int i = 0; i < hauteur; i++){ //parcours du tableau pour l'inserer dans fichierImg grâce au PrintWriter
                for(int j = 0; j < largeur; j++){
                    ecriture.print(tabImage[i][j]+" "); //pixel[i][j]
                }
            }
            ecriture.close();
        } catch (FileNotFoundException e) {
            System.err.println(e); //affichage erreur si le fichier n'est pas trouvé.
        }
    }

    public static void decrypter(String chemin, String out) throws FileNotFoundException{ //on ignore l'erreur FileNotFound
        Scanner sc = new Scanner(new File(chemin)); //Scanner sur le fichier txt à décrypter

        int largeur, hauteur, taille;
        largeur = Integer.valueOf(sc.next()).intValue(); //premiere ligne 1ere nombre
        hauteur = Integer.valueOf(sc.next()).intValue(); //premiere ligne 2eme nombre
        taille = largeur*hauteur;

        short[] pixels = new short[taille]; //va contenir les pixels de l'image img 
        
        int i = 0;
        while(sc.hasNext()){ //tant qu'on a qlq chose à "scanner"...
            pixels[i] = Short.valueOf(sc.next()).shortValue(); //on met le nième nombre dans le tableau des pixels (ième case)
            i++;
        }
        sc.close();

        ShortPixmap img = new ShortPixmap(largeur, hauteur, pixels); //creation de l'objet
        img.write(out); //creation du fichier decrypté
    }
}