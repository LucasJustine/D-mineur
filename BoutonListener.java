/**
 * La classe <code> BoutonListener </code> représente un observateur de bouton
* @author Lucas JUSTINE
*/

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class BoutonListener implements ActionListener {
   private Fenetre fenetre;
    /**
    * Initialise un controleur  
    * @param Fenetre permet d'acceder directement à la fenètre pour la fermer 
    */
   public BoutonListener(Fenetre f) {
       this.fenetre = f;
   }
   
   @Override
   public void actionPerformed(ActionEvent evenement){
        //Récupère le texte du bouton cliqué
        String b = evenement.getActionCommand();
        switch (b) {
            case "Jouer":
                fenetre.demarrerJeu();
                break;

            case "Continuer":
                fenetre.chargerSauvegarde();
                break;

            case "Sauvegarder et Quitter":
                fenetre.sauvegarderJeu();
                break;
            
            case "Accueil":
                fenetre.accueil();
                break;
                
            default :
                this.fenetre.dispose();
                break;
        }
    }
}