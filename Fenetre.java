import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon; //Utilisation de Java Swing pour l'IU
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class Fenetre extends JPanel implements ActionListener{
        private static final long serialVersionUID = 1L;
        private JFileChooser fileChooser;
        private JFileChooser exportDecrypt;
        private JFileChooser exportCrypt;
        private JButton openButton;
        private JButton decryptButton;
        private JButton cryptButton;
        private JLabel cheminTxt;
        FiltreFC filtreDecryptage;
        FiltreFC filtreCryptage;
        int returnVal;
        String cheminImg=null;
        PGM image;

        public Fenetre(){
                fileChooser = new JFileChooser(); //filechooser pour l'importation
                exportCrypt = new JFileChooser(); //filechooser pour l'exportation txt
                exportDecrypt = new JFileChooser(); //filechooser pour l'exportation en .pgm
                filtreDecryptage =  new FiltreFC(new String[]{"pgm"}, "Fichier image .pgm");
                filtreCryptage = new FiltreFC(new String[]{"txt"}, "Fichier texte .txt"); //creation filtre de type de fichier
                fileChooser.addChoosableFileFilter(filtreCryptage);
                fileChooser.addChoosableFileFilter(filtreDecryptage);
                exportCrypt.addChoosableFileFilter(filtreCryptage); //ajout des filtres sur les FileChooser
                exportDecrypt.addChoosableFileFilter(filtreDecryptage);
                openButton = new JButton("Importer");
                decryptButton = new JButton("Decrypter");
                cryptButton = new JButton("Crypter");
                cheminTxt = new JLabel("Chemin : <néant>"); //affichage du chemin
               
                setPreferredSize(new Dimension(340, 100)); //fenetre 340*100
                setLayout(new BorderLayout());//layout pour la fenetre
               
                add(openButton, BorderLayout.PAGE_START);
                add(decryptButton, BorderLayout.LINE_END);
                add(cryptButton, BorderLayout.LINE_START);
                add(cheminTxt, BorderLayout.PAGE_END);
                openButton.addActionListener(this);//en attente d'une action sur la fenetre
                decryptButton.addActionListener(this);
                cryptButton.addActionListener(this);

        }
       
        public void actionPerformed(ActionEvent e){
                if(e.getSource() == openButton){ //si clique sur le bouton importer
                        returnVal = fileChooser.showOpenDialog(null); //fenetre d'ouverture de fichier
                        if(returnVal == JFileChooser.APPROVE_OPTION){
                            cheminImg = new String(fileChooser.getSelectedFile().getAbsolutePath());
                            cheminTxt.setText("Chemin : "+cheminImg);                        
                        }
                }
                else if(e.getSource() == decryptButton){
                    if(cheminImg!=null && cheminImg.endsWith(".txt")){
                        returnVal = exportDecrypt.showSaveDialog(null);//creation fichier
                        if(returnVal == JFileChooser.APPROVE_OPTION){
                            try {
                                PGM.decrypter(cheminImg, exportDecrypt.getSelectedFile().getAbsolutePath());
                            }
                            catch(FileNotFoundException er) {
                                System.err.println(er);
                            }
                        }
                    } else {showMessageDialog(null, "Veuillez importer un fichier texte (.txt)");}//erreur
                }
                else if(e.getSource() == cryptButton){
                    if(cheminImg!=null && cheminImg.endsWith(".pgm")){
                        returnVal = exportCrypt.showSaveDialog(null);
                        if(returnVal == JFileChooser.APPROVE_OPTION){
                            image = new PGM(cheminImg);
                            image.crypter(exportCrypt.getSelectedFile().getAbsolutePath());
                        }
                    } else {showMessageDialog(null, "Veuillez importer un fichier image (.pgm)");}
                }
        }
}
 
