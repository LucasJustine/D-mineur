/**
 * La classe <code> CaseListener </code> représente l'observateur des cases de Démineur
 * @author JUSTINE Lucas, Yannis
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class CaseListener implements MouseListener{
     
    private Jeu jeu;
    private Fenetre f;
    /**
     * Initialise un CaseListerner 
     * @param jeu Le Jeu dont on observe les cases
     * @param fenetre La Fenetre qui affiche la partie
     */
    public CaseListener(Jeu jeu,Fenetre fenetre){
        this.jeu = jeu;
        this.f = fenetre;
    }

    public void mouseClicked(MouseEvent e) {
        //mouseClicked a des fois des problèmes sur Windows, on utilisera mouseReleased à la place
    }
    public void mouseEntered(MouseEvent e) {
    
    }
    public void mouseExited(MouseEvent e) {
    
    }
    public void mousePressed(MouseEvent e) {
    
    }
    public void mouseReleased(MouseEvent e) {
        //Récupère la case cliqué
        Case c = (Case) e.getSource();
        //Si la case est cliqué ou que le jeu est fini, ne rien faire
        if(c.estClique() || this.jeu.estFini()) return;
        //Si le bouton gauche est appuyé
        if(e.getButton() == MouseEvent.BUTTON1){
            if(c.getFlag() != 0 ) return;
            if(!c.estMine()){
                try {
                    int [] tab = this.jeu.trouverCase(c);
                    this.jeu.revelerCase(tab[0], tab[1]);
                    if(jeu.verifierWin()){
                        f.jeuFini();
                        JOptionPane.showMessageDialog(this.f,"Bravo","Gagné ", JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                } catch (IllegalArgumentException evenement) {
                    //System.out.println(evenement.getMessage());
                }
            }
            else{
                f.jeuFini();
                jeu.gameOver(c);
                JOptionPane.showMessageDialog(this.f,"Perdu","Dommage ", JOptionPane.INFORMATION_MESSAGE);  
            }  
        }
        //Si le bouton droit est appuyé
        else {
            //On change le flag de la case
            this.jeu.changeFlag(c);
            //On change le nombre de bombe restante sur l'affichage 
            this.f.changerNombresBombes();
        }
    }
  
}