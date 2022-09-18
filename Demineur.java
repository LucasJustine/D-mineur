/**
 * La classe <code> Demineur </code> est la classe principale qui ouvrez la fenetre du démineur
 * @author JUSTINE Lucas, Yannis
 */
public class Demineur{
    public static void main(String[] args) {
        Fenetre fenetreDemineur = new Fenetre();
	    FenetreListener fl = new FenetreListener(fenetreDemineur);
        fenetreDemineur.addWindowListener(fl);
    }
}
