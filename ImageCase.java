/**
 * La classe <code> ImageCode </code> représente une classe utilitaire qui permet d'avoir un tableau contenant toutes les images pour les cases
 * @author JUSTINE Yannis
 */

import java.awt.Image;
import  java.awt.Toolkit;

public final class ImageCase {
    //Tableau d'image utilisée pour les cases. Charger ici car évite de charger plusieurs fois les images dans chaque cases
    public static final Image[] IMAGE;
    
    public ImageCase(){
      throw new AssertionError("Impossible de créer un objet de la classe");
    }

    /*** Permet de charger toutes les images */
    static {
        IMAGE = new Image[15];
        for(int i = 0; i < 15; i++){
            IMAGE[i] = Toolkit.getDefaultToolkit().getImage("./Image/"+ i + ".png");
        }
    }
}
