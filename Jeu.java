/**
 * La classe <code> Jeu </code> représente une partie de Démineur
 * @author JUSTINE Lucas, Yannis
 */

import java.util.Random;
import java.awt.event.MouseListener;

public class Jeu {
    private Case[][] tableau;
    private boolean estFini;
    private int lignes;
    private int colonnes;
    private int nombreBombe;
    private int nombreEtoile;
    private int nombreCaseRevele;

    /**
     * Initialise une grille d'une dimension donné par ses arguments et la rempli aléatoire de bombe
     * @param lignes nombre de lignes (3 < size < 30)
     * @param colonnes nombre de lignes (3 < size < 30)
     * @param bombe nombre de bombe a placé (0 < bombe < size^2)
     * @throws IllegalArgumentException si les arguments ne respectent pas les bornes
     */
    public Jeu(int lignes, int colonnes, int bombes) throws IllegalArgumentException{
        if(lignes < 4 || lignes > 30)
            throw new IllegalArgumentException("Lignes n'est pas valide (3 < Nbr lignes < 31 )");
        if(colonnes < 4 || colonnes > 30)
            throw new IllegalArgumentException("Colonnes n'est pas valide (3 < Nbr colonnes < 31 )");
        if(bombes <= 0)
            throw new IllegalArgumentException("Pas assez de bombes");
        if(bombes > lignes * colonnes - 1)
            throw new IllegalArgumentException("Trop de bombes : " + bombes + " pour " + lignes * colonnes + " cases");
        this.tableau = new Case[lignes][colonnes];
        this.nombreEtoile = 0;
        this.nombreCaseRevele = 0;
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.nombreBombe = bombes;
        this.estFini = false;
        for(int i = 0; i < this.lignes; i++){
            for(int j = 0; j < this.colonnes; j++){
                this.tableau[i][j] = new Case(Fenetre.DIMENSION_MAX_GRILLE / (lignes>colonnes?lignes:colonnes));
            }
        }
        this.creeBombe();
    }

    /**
     * Initialise une partie déjà commencé. Effectue une série de tests sur les données pour savoir si les données ont été corrompus
     * @param lignes le nombre de lignes 
     * @param colonnes le nombre de colonnes
     * @param bombes le nombre de bombes
     * @param tabClique tableau de booléen représentant pour chaque case si elle est revelée
     * @param tabCliquable tableau de booléen représentant pour chaque case si elle est cliquable
     * @param tabEstMine tableau de booléen représentant pour chaque case si elle est une bombe
     * @param tabBombeVoisin tableau d'entier represéntant le nombre de voisin de chaque case (Possibilité d'être calculé)
     * @param tabFlag tableau d'entier represéntant l'état du flag de chaque case
     * @throws IllegalArgumentException si il y a une erreur dans les paramètres donnés en argument
     */
    public Jeu(int lignes, int colonnes, int bombes, boolean[] tabClique, boolean[] tabEstMine, int[] tabBombeVoisin, int[] tabFlag)  throws IllegalArgumentException{
        if(lignes < 4 || lignes > 30)
            throw new IllegalArgumentException("Données corrompu");
        if(colonnes < 4 || colonnes > 30)
            throw new IllegalArgumentException("Données corrompu");
        if(bombes < 0)
            throw new IllegalArgumentException("Données corrompu");
        if(bombes > lignes * colonnes - 1)
            throw new IllegalArgumentException("Données corrompu");
        this.tableau = new Case[lignes][colonnes];
        this.nombreEtoile = 0;
        this.nombreCaseRevele = 0;
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.nombreBombe = bombes;
        this.estFini = false;
        for(int i = 0 ; i < lignes; i++){
            for(int j = 0 ; j < colonnes; j++){
                this.tableau[i][j] = new Case(Fenetre.DIMENSION_MAX_GRILLE / (lignes>colonnes?lignes:colonnes));

                if(tabEstMine[i*lignes+j]){
                    this.tableau[i][j].setMine();
                    if(tabClique[i*lignes+j]){
                        throw new IllegalArgumentException("Une bombe est cliquée");
                    }
                }

                for(int k = 0 ; k < tabBombeVoisin[i*lignes+j]; k++)
                    this.tableau[i][j].ajouterBombeVoisin();

                if(tabFlag[i*lignes+j] > 2 )
                    throw new IllegalArgumentException("Données corrompu");
                this.tableau[i][j].setFlag(tabFlag[i*lignes+j]);

                if(tabFlag[i*lignes+j] != 0){
                    this.tableau[i][j].repaint();
                    if(tabFlag[i*lignes+j] == 1)
                        this.nombreEtoile++;
                }

                if(tabClique[i*lignes+j]){
                    this.tableau[i][j].reveler();
                    this.nombreCaseRevele++;
                }
            }
        }
        if(this.nombreCaseRevele >= (this.lignes * this.colonnes) - this.nombreBombe)
            throw new IllegalArgumentException("Données corrompu");
        if(this.nombreEtoile > this.nombreBombe)
            throw new IllegalArgumentException("Données corrompu");
    }

    /**
     * Parcours le tableau {@link Jeu#tableau} et ajoute aléatoirement {@link Jeu#nombreBombe} dedans.
     * Pour limiter les accès à ce tableau on incrémente le nombre de bombe à tous ses voisins à chaque bombe posée.
     */
    private void creeBombe(){
        //if(this.tableauBombe[0] != null) return;
        Random r = new Random();
        for(int i = 0; i < this.nombreBombe; i++){
            int ligne = r.nextInt(this.lignes);
            int colonne = r.nextInt(this.colonnes);
            while(this.tableau[ligne][colonne].estMine()){
                ligne = r.nextInt(this.lignes);
                colonne = r.nextInt(this.colonnes);
            }
            //Ajoute une mine
            this.tableau[ligne][colonne].setMine();
            //Ajoute les indices des mines dans le tableau des mines
            //Incrémente le nombre de mines des voisins
            for(int ligneVoisin = ligne-1; ligneVoisin <= ligne+1; ligneVoisin++){
                for(int colonneVoisin = colonne-1; colonneVoisin <= colonne+1; colonneVoisin++){
                    if(estValide(ligneVoisin, colonneVoisin ) && !this.tableau[ligneVoisin][colonneVoisin].estMine())
                        this.tableau[ligneVoisin][colonneVoisin].ajouterBombeVoisin();
                }
            }
        }
    }

    /**
     * Ajoute un MouseListener à chaque case du tableau 
     * @param listener le MouseListener 
    */
    public void setListener(MouseListener listener){
        for(int i = 0; i < this.lignes; i++){
            for(int j = 0; j < this.colonnes; j++){
                this.tableau[i][j].addMouseListener(listener);
            }
        }
    }

    /**
     * Retourne true si la case identifié par les arguments appartient à la grille et n'est pas une bombe
     * @param i ligne de la case
     * @param j colonne de la case
     * @return True si la case est valide, false sinon.
    */
    public boolean estValide(int i, int j){
        if(i >= 0 && i <= this.lignes - 1 && j >= 0 && j <= this.colonnes - 1)
            return true;
        else
            return false;
    }
    /**
     * Renvoie la ligne et la colonne dans le tableau de la case demandé 
     * @param c La case a trouver
     * @return Tableau d'entier (ligne, colonne)
     * @throws IllegalArgumentException La case demandé n'est pas dans cette grille
     */
    public int[] trouverCase(Case c) throws IllegalArgumentException{
        int[] tab = new int[2];
        for(int i = 0; i < this.lignes; i++) {
            for(int j = 0; j < this.colonnes; j++) {
                if(this.tableau[i][j] == c){
                    tab[0] = i;
                    tab[1] = j;
                    break;
                }
            }
        }
        if(tab.length < 2)
            throw new IllegalArgumentException("La case ne se trouve pas dans cette grille");
        return tab;
    }

    /**
     * Révèle les cases adjacentes si elles sont vides en utilisant l'algorithme de Flood-Fill
     * @param i ligne de la case
     * @param j colonne de la case
     */
    public void revelerCase(int i, int j){
        if(estValide(i, j) && this.tableau[i][j].getFlag() == 0 && !this.tableau[i][j].estClique()){
            this.tableau[i][j].reveler();
            this.nombreCaseRevele++;
            if(this.tableau[i][j].getVoisin() > 0){
                return;
            }
            else{
                revelerCase(i-1, j);
                revelerCase(i+1, j);
                revelerCase(i, j-1);
                revelerCase(i, j+1);
            }
        }
    }

    /**
     * Change le flag de la case donné en argument
     * @param c La case dont le flag doit être changé
     */
    public void changeFlag(Case c){
        if(c.getFlag() == 0){
            c.ajouteFlag();
            //Passe de vide à ? si le nombre maximal d'étoile est déjà affiché
            if(this.nombreEtoile < this.nombreBombe)
                this.nombreEtoile++;
            else c.ajouteFlag();  
            c.repaint(); 
        }
        else if(c.getFlag() == 1){
            c.ajouteFlag();
            this.nombreEtoile--;
            c.repaint();
        }    
        else if(c.getFlag() == 2){
            c.ajouteFlag();
            c.repaint();
        }
    }

    /**
     * Révèle toutes les bombes et le jeu se termine
     * @param c La bombe qui a été déclenché
     */
    public void gameOver(Case c){
        //change la couleur de la bombe cliqué
        c.exploser();
        for(int i = 0; i < this.lignes; i++) {
            for(int j = 0; j < this.colonnes; j++) {
                if (!this.tableau[i][j].estMine() && this.tableau[i][j].getFlag() == 1){
                    this.tableau[i][j].reveler();
                } 
                else if (this.tableau[i][j].estMine() && this.tableau[i][j] != c){
                    this.tableau[i][j].reveler();
                }
            }
        }
        this.estFini = true;
    }

    /**
     * Vérifie si le jeu est terminé.
     * S'effectue a chaque clic sur une case
     * @return True si le joueur a gagné, false sinon.
     */
    public boolean verifierWin(){
        if( this.nombreCaseRevele == (this.lignes * this.colonnes) - this.nombreBombe){
            this.estFini = true;
            return true;
        }
        return false;
    }

    /**
     * Retourne un panneau avec toutes les cases du tableau
     * @return le tableau de Case
     */
    public Case[][] getCases(){
        return this.tableau;
    }

    /**
     * Retourne le nombre de lignes du jeu 
     * @return Le nombre de ligne du jeu
     */
    public int getLignes(){
        return this.lignes;
    }

    /**
     * Retourne le nombre de colonne du jeu 
     * @return Le nombre de colonne du jeu
     */
    public int getColonnes(){
        return this.colonnes;
    }

    /**
     * Retourne le nombre de bombe/mine du jeu 
     * @return Le nombre de nomne/mine du jeu
     */
    public int getBombes(){
        return this.nombreBombe;
    }

    /**
     * Retourne true si la partie est fini, false sinon
     * @return L'état de la partie
     */
    public boolean estFini(){
        return this.estFini;
    }

    /**
     * Permet de connaître le nombre de bombes restantes
     * @return integer
     * * S'effectue a chaque clic droit sur une case
     */
    public int getNombreBombesRestantes(){
        return this.nombreBombe - this.nombreEtoile;
    }

}