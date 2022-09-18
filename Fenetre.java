/**
 * La classe <code> Fenetre </code> représente la fenêtre du démineur
 * @author JUSTINE Lucas, Yannis
 */

import javax.swing.*;

public class Fenetre extends JFrame{
    /**
     * <code> jeu </code> représente le jeu en cours 
     */
    private Jeu jeu;
    /**
     * <code> accueil </code> représente le panneau d'accueil
     */
    private Accueil accueil;
    /**
     * <code> JPanelDemineur </code> représente le panneau du Démineur
     */
    private JPanelDemineur panneauJeu;

    /**
     * <code>  DIMENSION_MAX_GRILLE </code> représente la taille de grille du Démineur
     */
    public static final int DIMENSION_MAX_GRILLE = 800;

    /**
     * Fenetre du démineur
     */
    public Fenetre(){
        //Configuration de la fenêtre
        super("Démineur");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        /*
        Pour avoir accès à la taille de fenetre lorsqu'elle est maximisée
        nous devons la rendre visible puis ajouter des composants dedans
        sinon les dimensions restent nulles
        */
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Initialisation l'observateur des boutons
        BoutonListener observateurBouton = new BoutonListener(this);
        //Initialisation et affichage de la page d'accueil
        this.accueil = new Accueil(observateurBouton);
        this.setContentPane(this.accueil);
        this.revalidate();

        //Initialisation du JPanel du demineur
        this.panneauJeu = new JPanelDemineur(observateurBouton);
        
        //Tester si le fichier de sauvegarde existe
        this.testerSauvegarde();
        /*
        On limite la taille maximume de la fenetre à ses dimensions lorsqu'elle est maximisée.
        La limitation se fait à la fin car elle ne fonctionne pas avant d'avoir ajouter 
        des composants dans la fenetre. Sinon les dimensions restent nulles.
        setResizable() ne fonctionne pas avec setExtentedState() car la fenetre n'est plus maximisée
        */
        this.setMaximumSize(this.getSize());
        this.setMinimumSize(this.getSize());
    }

    /**
     * Change le panneau visible à la fenêtre
     * @param panel le panneau qui doit être visible
     */
    private void changerPanel(JComponent panel){
        this.setContentPane(panel);
        this.revalidate();
    }

    /**
     * Demande à l'utilisateur les informations nécessaires à la création de la grille de Démineur.
     * Affiche ensuite la grille.
     * Si cela échoue affiche un message d'erreur
     */
    public void demarrerJeu(){
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();
        Object[] message = {
            "Entrez le nombre de ligne", field1,
            "Entrez le nombre de colonne :", field2,
            "Entrez le nombre de bombes :", field3,
        };
        int choix = JOptionPane.showConfirmDialog(this, message, "Entrez les dimensions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (choix == JOptionPane.OK_OPTION)
        {
            try {
                int lignes = Integer.parseInt(field1.getText());
                int colonnes = Integer.parseInt(field2.getText());
                int bombes = Integer.parseInt(field3.getText());
                try {
                    this.jeu = new Jeu(lignes, colonnes, bombes);
                    this.jeu.setListener(new CaseListener(this.jeu,this));
                    this.panneauJeu.init(this.jeu);
                    this.changerPanel(this.panneauJeu);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(),"Erreur ", JOptionPane.ERROR_MESSAGE);
                    this.demarrerJeu();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nombre invalide","Erreur", JOptionPane.ERROR_MESSAGE);
                this.demarrerJeu();
            }
        }
    }
    
    /**
     * Change le nombre de bombe à l'affichage 
     * Utilisé à chaque clic droit 
     */
    public void changerNombresBombes(){
        this.panneauJeu.changerNombresBombes(jeu.getNombreBombesRestantes());
    }


    private void testerSauvegarde(){
        if(!Fichier.testerSauvegarde("./Sauvegarde/sauvegarde.bin"))
            this.accueil.boutonContinuerEnable(false);
    }

    /**
     * Permet de reprendre une partie enregistrée
     * Utilisé lors du clic sur Continuer dans le menu d'accueil 
     */
    public void chargerSauvegarde(){
        this.jeu = Fichier.charger("./Sauvegarde/sauvegarde.bin");
        if(this.jeu != null){
            //L'observateur doit être modifié pour qu'il puisse utilisé les méthodes de cette fenêtre.
            this.jeu.setListener(new CaseListener(this.jeu,this));
            this.panneauJeu.init(this.jeu);
            this.changerPanel(this.panneauJeu);
        } 
    }

     /**
     * Change le panneau visible à la fenêtre
     * Renvoie au menu d'accueil
     */
    public void accueil(){
        //Tester si le fichier de sauvegarde existe
        this.testerSauvegarde();
        this.changerPanel(this.accueil);
    }

    /**
     * Permet de sauvegarder la partie en cours
     * Sauvegarde l'objet <code> jeu </code> dans un fichier
     */
    public void sauvegarderJeu(){
        if( this.jeu != null && !this.jeu.estFini()){
            try {
                Fichier.sauvegarder(this.jeu, "./Sauvegarde/sauvegarde.bin");
                JOptionPane.showMessageDialog(this, "Sauvegarde effectuée", "Information", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur : Impossible de sauvegarder le fichier", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Lorsque le jeu est terminé, affiche le bouton accueil permettant de rejouer 
     */
    public void jeuFini(){
        this.panneauJeu.setBoutonText("Accueil");
    }
}
