/**
 * La classe <code> Fichier </code> permet de charger ou de sauvegarder une partie de Démineur, ainsi que les images des cases.
 * C'est une classe utilitaire
 * @author JUSTINE Yannis
 */
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.Toolkit;


public final class Fichier {
    //Tableau d'image utilisée pour les cases. Charger ici car évite de charger plusieurs fois les images dans chaque cases
    public static final Image[] IMAGE;

    /**
     * Permet de charger toutes les images au chargement de la classe
     */
    static {
        IMAGE = new Image[15];
        for(int i = 0; i < 15; i++){
            IMAGE[i] = Toolkit.getDefaultToolkit().getImage("./Image/"+ i + ".png");
        }
    }

    //Constructeur d'une classe utilitaire
    private Fichier(){
        throw new AssertionError("Impossible de créer un objet de la classe");
    }

    /**
     * Lis dans un fichier et initialise une partie avec les données qui ont été lu
     * @param file le fichier dans lequel lire
     * @return l'objet qui représente la partie qui a été lu ou null si il y a un échec
     */
    public static Jeu charger(String file){        
        Jeu ancienJeu = null;
        int compteurBombe = 0;
        //Ouverture du fichier
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);

            //Test lecture dans fichier
            try {

                int lignes = (int) dis.readByte();
                int colonnes = (int) dis.readByte();
                int bombes = dis.readInt();

                //Si le fichier ne contient pas assez d'information
                if (dis.available() / 4 < lignes*colonnes) {
                    JOptionPane.showMessageDialog(null, "Fichier corrompu (nombre d'octets)","Erreur", JOptionPane.ERROR_MESSAGE);
                } else {

                    boolean[] tabClique = new boolean[lignes*colonnes];
                    boolean[] tabEstMine = new boolean[lignes*colonnes];
                    int[] tabBombeVoisin = new int[lignes*colonnes];
                    int[] tabFlag = new int[lignes*colonnes];
                    
                    //Lis les paramètres de chaque cases.
                    //Si une exception est levée on la capture comme pour la lecture du nombre de lignes, ...
                    for(int i=0; i<lignes*colonnes; i++) {
                        tabClique[i] = dis.readBoolean();
                        tabEstMine[i] = dis.readBoolean();
                        tabBombeVoisin[i] = dis.readByte();
                        tabFlag[i] = (int) dis.readByte();
                    }
                    
                    //Compte le nombre de bombe
                    for(int i=0; i<lignes*colonnes; i++){
                        if(tabEstMine[i])
                            compteurBombe++;
                    }

                    //Si les nombres de bombes ne correspondent pas
                    if(compteurBombe != bombes) {
                        JOptionPane.showMessageDialog(null, "Fichier corrompu (nombre de bombes) ","Erreur", JOptionPane.ERROR_MESSAGE);
                    } 
                    //Si les nombres de bombes correspondent
                    else {
                        //Test initialisation de la partie
                        try {
                            ancienJeu = new Jeu (lignes, colonnes, bombes, tabClique, tabEstMine, tabBombeVoisin, tabFlag);
                        } catch (IllegalArgumentException e) { //Si la partie ne peut pas être initialisée
                            JOptionPane.showMessageDialog(null, "Fichier corrompu","Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                    
            } catch(IOException e){ //Si il y a des erreurs lors de la lecture du fichier
                JOptionPane.showMessageDialog(null, "Erreur lecture","Erreur", JOptionPane.ERROR_MESSAGE);
            }
            //Le fichier doit être fermé
            try{
                if(dis != null) dis.close();
            } catch (IOException e){}  //Erreur a la fermeture mais on ne s'en occupe pas
            
        //Si erreur lors de l'ouverture
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur ouverture du fichier","Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return ancienJeu;
    }

    /**
     * Ecrit dans un fichier une partie de Démineur.
     * Ouvre un pop-up si une erreur se produit
     * @param jeu le jeu a sauvegarder
     * @param file le nom du fichier dans lequel sauvegarder
     */
    public static void sauvegarder(Jeu jeu, String file) throws IOException{
        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            byte lignes = (byte) jeu.getLignes();
            byte colonnes = (byte) jeu.getColonnes();
            int bombes = jeu.getBombes();
            Case[][] cases =  jeu.getCases();
            //Test écriture dans fichier
            try{
                dos.writeByte(lignes);
                dos.writeByte(colonnes);
                dos.writeInt(bombes);
                for(int i=0; i<lignes; i++) {
                    for(int j=0; j<colonnes; j++) {
                        dos.writeBoolean(cases[i][j].estClique());
                        dos.writeBoolean(cases[i][j].estMine());
                        dos.writeByte(cases[i][j].getVoisin());
                        dos.writeByte(cases[i][j].getFlag());
                    }
                }
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "Erreur écriture dans le fichier","Erreur", JOptionPane.ERROR_MESSAGE);
            }
            
            try {
                dos.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur fermeture du fichier","Erreur", JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(IOException err){
            JOptionPane.showMessageDialog(null, "Erreur ouverture du fichier","Erreur", JOptionPane.ERROR_MESSAGE);
        } 
    }

    public static boolean testerSauvegarde(String s){
        File file = new File(s);
        //Une exception peut être levée mais on ne s'en occupe pas
        //On prétend que l'utilisateur laisse les droits de lecture
        return file.exists(); 
    }
}