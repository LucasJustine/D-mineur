import java.awt.event.WindowListener;   
import java.awt.event.WindowEvent;    

/**
 * La classe <code> FenetreListener </code> représente un observateur de fenetre
 * @author JUSTINE Yannis, Lucas
*/

public class FenetreListener implements WindowListener{
    private Fenetre fenetre;
    /**
    * Initialise un controleur de fenêtre  
    * @param f permet d'acceder directement à la fenetre
    */
    public FenetreListener(Fenetre f){
        this.fenetre = f;
    }

    public void windowActivated (WindowEvent arg0) {    
    }      

    public void windowClosed (WindowEvent arg0) {    
    }    

    public void windowClosing (WindowEvent arg0) { 
        this.fenetre.sauvegarderJeu();
        this.fenetre.dispose();     
    }    
      
    public void windowDeactivated (WindowEvent arg0) {    
    }    
    
    public void windowDeiconified (WindowEvent arg0) {    
    }    
      
    public void windowIconified(WindowEvent arg0) {      
    }    
      
    public void windowOpened(WindowEvent arg0) {    
    }    
}