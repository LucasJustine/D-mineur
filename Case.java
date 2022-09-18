/**
 * La classe <code> Case </code> représente une case de Démineur
 * @author JUSTINE Yannis
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JComponent;


public class Case extends JComponent{
    //Si la case est révéler ou non
    private boolean clique;
    //Si la case est une mine
    private boolean estMine;
    //Le nombre de voisin de la case qui sont des mines
    private int bombeVoisin;
    /*Le flag de la case :
        0 = sans flag
        1 = étoile
        2 = ?
        3 = utilisé uniquement pour représenter la bombe qui a explosé*/
    private int flag;
  

    /**
     * Initialise une case qui n'est pas une mine et qui a comme dimension celle fourni par son argument
     * @param size dimension de la case
     */
    public Case(int size){
        super();
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(size,size));
        this.clique = false;
        this.estMine = false;
        this.bombeVoisin = 0;
        this.flag = 0;
    }

    /**
     * Révèle la case et la repaint.
     */
    public void reveler(){
        this.clique = true;
        repaint();
    }

    //Variante de révéler() spécifique a la mine déclenchée
    public void exploser(){
        this.flag = 3; //Bombe a explosé
        this.clique = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics pinceau = g.create();
        int width = this.getWidth();
        int height = this.getHeight();
        pinceau.setColor(Color.BLACK);
        //Si la case est une mine
        //this.setBackground(new Color(240,240,240));
        if(!this.clique){
            if(this.flag == 0) 
                pinceau.drawImage(Fichier.IMAGE[0],0,0,width,height,this);
            else if(this.flag == 1)
                pinceau.drawImage(Fichier.IMAGE[13],0,0,width,height,this);
            else
                pinceau.drawImage(Fichier.IMAGE[12],0,0,width,height,this);
        }
        else{
            if(!this.estMine){
                switch(this.bombeVoisin){
                    case(0):
                    pinceau.drawImage(Fichier.IMAGE[9],0,0,width,height,this);
                        break;
                    case(1):
                    pinceau.drawImage(Fichier.IMAGE[1],0,0,width,height,this);
                        break;
                    case(2):
                    pinceau.drawImage(Fichier.IMAGE[2],0,0,width,height,this);
                        break;
                    case(3):
                    pinceau.drawImage(Fichier.IMAGE[3],0,0,width,height,this);
                        break;
                    case(4):
                    pinceau.drawImage(Fichier.IMAGE[4],0,0,width,height,this);
                        break;
                    case(5):
                    pinceau.drawImage(Fichier.IMAGE[5],0,0,width,height,this);
                        break;
                    case(6):
                    pinceau.drawImage(Fichier.IMAGE[6],0,0,width,height,this);
                        break;
                    case(7):
                    pinceau.drawImage(Fichier.IMAGE[7],0,0,width,height,this);
                        break;
                    case(8):
                    pinceau.drawImage(Fichier.IMAGE[8],0,0,width,height,this);
                        break;
                }
                if(this.flag == 1) 
                    pinceau.drawImage(Fichier.IMAGE[11],0,0,width,height,this);
            }
            else{
                if(this.flag == 3)
                pinceau.drawImage(Fichier.IMAGE[14],0,0,width,height,this);
            else if (this.flag == 2)
                pinceau.drawImage(Fichier.IMAGE[12],0,0,width,height,this);
            else if (this.flag == 1)
                pinceau.drawImage(Fichier.IMAGE[13],0,0,width,height,this);
            else
                pinceau.drawImage(Fichier.IMAGE[10],0,0,width,height,this);
            }
        }
    }

    /**
    * Retourne true si la case est une mine
    * @return true si la case est une mine
    */
    public boolean estMine(){
        return this.estMine;
    }

    /**
     * Retourne le nombre de mine autour de la case
     * @return le nombre de mine autour de la case
    */
    public int getVoisin(){
        return this.bombeVoisin;
    }
    /**
     * Retourne le flag de la case
     * @return integer
    */
    public int getFlag(){
        return this.flag;
    }

    /**
     * Récuperer si la case est révélée
     * @return true si la case est révélée
     */
    public boolean estClique(){
        return this.clique;
    }

    /**
     * Change une case en mine
     */
    public void setMine(){
        this.estMine = true;
    }

    /**
     * Change le flag de la case 
     * @param flag le nouveau flag de la cas
     */
    public void setFlag(int flag){
        this.flag = flag;
    }

    /**
     * Incrémente le nombre de mines aux alentours de la case
     */
    public void ajouterBombeVoisin(){
        this.bombeVoisin += 1;
    }
    /**
     * Incrémente le flag de la case
     */
    public void ajouteFlag(){
        this.flag = (this.flag + 1) % 3;
    }
    

}


