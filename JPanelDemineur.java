/**
 * La classe <code> JPanelDemineur </code> représente un JPanel contenant toutes les cases d'une partie de démineur
 * @author JUSTINE Yannis
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JPanelDemineur extends JPanel{

    //<code> bouton </code> représente le bouton qui permet de sauvegarder ou revenir à l'accueil
    private JButton bouton;
    //<code> nombreBombesRestantes </code> représente le JLabel qui indique le nombre de bombes restantes
    private JLabel nombreBombesRestantes;
    
    private JPanel panneaupartie;

    //Initialise un panneau avec une zone pour la partie, le nombre de bombe restante et un bouton
    public JPanelDemineur(ActionListener observateurBouton){
        super();
        //Change le layout de l'objet
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Initialisation du JLabel affichant le nombre de bombes restantes
        this.nombreBombesRestantes = new JLabel();
        this.nombreBombesRestantes.setFont(new Font("Arial", Font.BOLD, 20));

        //Initialisation du bouton
        this.bouton = new JButton();
        this.bouton.addActionListener(observateurBouton);

        this.panneaupartie = new JPanel();

        //Alignement horizontale des differents composants
        this.panneaupartie.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.nombreBombesRestantes.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.bouton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Ajout de tous les éléments au panneau en créeant de l'espace entre chaque élément
        this.add(Box.createVerticalGlue());
        this.add(this.nombreBombesRestantes);
       
        this.add(Box.createVerticalGlue());
        this.add(this.panneaupartie);
       
        this.add(Box.createVerticalGlue());
        this.add(this.bouton);
        
        this.add(Box.createVerticalGlue());
        
    }
    /**
     * Initialise le panneau de la panneaupartie
     * Affiche toutes les cases de la panneaupartie donné en argument
     * @param jeu la panneaupartie dont les cases doivent être affichées
     */
    public void init(Jeu jeu){
        //Récupère le tableau des cases
        Case[][] cases = jeu.getCases();
        //Récupère le nombre de lignes du jeu
        int lignes = jeu.getLignes();
        //Récupère le nombre de colonnes du jeu
        int colonnes = jeu.getColonnes();
        //Change le layout du panneau de la panneaupartie
        GridLayout layout = new GridLayout(lignes,colonnes);
        layout.setVgap(2);
        layout.setHgap(2);
        this.panneaupartie.setLayout(layout);
        //Supprime tous les composants du panneau de la panneaupartie 
        //(Eventuellement pour un bouton rejouer change la panneaupartie sans créer un nouvel objet)
        this.panneaupartie.removeAll();
        //Ajoute les cases au panneau de la panneaupartie
        for(int i = 0; i < lignes; i++){
            for(int j = 0; j < colonnes; j++){
                this.panneaupartie.add(cases[i][j]);
            }
        }
        //Change le texte
        this.nombreBombesRestantes.setText("Nombres de bombes restantes : " + jeu.getNombreBombesRestantes());
        //Change la taille maximale et minimale du panneau (pour le BoxLayout de la Fenetre)
        this.panneaupartie.setMaximumSize(this.panneaupartie.getPreferredSize());
        this.panneaupartie.setMinimumSize(this.panneaupartie.getPreferredSize());
        //Change le texte du bouton
        this.bouton.setText("Sauvegarder et Quitter");
        //Redessine le panneau
        this.revalidate();
    }

    public void setBoutonText(String texte){
         this.bouton.setText(texte);
    }

    public void changerNombresBombes(int bombe){
        this.nombreBombesRestantes.setText("Nombres de bombes restantes : " + bombe);
    }
}