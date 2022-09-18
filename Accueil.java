/**
 * La classe <code> Accueil </code> permet de créer un panneau représentant le menu d'accueil
 * @author JUSTINE Lucas, Yannis
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
 
public class Accueil extends JPanel{
    /**
     * <code> boutonContinuer </code> représente le bouton Continuer
     */
    private JButton boutonContinuer;

    /**
     * Initialise un accueil contenant les boutons Jouer, Continuer ainsi que Quitter
     */
    public Accueil(ActionListener controleur){
        super();
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(ClassNotFoundException e){
            System.err.println("classNotFOund");
        }catch(InstantiationException e){
            System.err.println("InstantiationException");
        }catch(IllegalAccessException e){
            System.err.println("IllegalAccessException");
        }catch(UnsupportedLookAndFeelException e){
            System.err.println("UnsupportedLookAndFeelException");
        }

        JButton bouton1 = this.bouton("Jouer");
        this.boutonContinuer = this.bouton("Continuer"); 
        JButton bouton3 = this.bouton("Quitter");
        JLabel text = new JLabel("Démineur",SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 70));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(text);
        bouton1.addActionListener(controleur);
        this.boutonContinuer.addActionListener(controleur);
        bouton3.addActionListener(controleur);        
        /** 
        *   Box.Filler permet de créer de la marge avec des dimensions minimales, préférées et maximales
        */
        this.add(Box.createVerticalGlue());
        this.add(new Box.Filler(new Dimension(0,0), 
                                new Dimension(0, 50), 
                                new Dimension(0,50)));
        this.add(bouton1);
        this.add(new Box.Filler(new Dimension(0,0), 
                                new Dimension(0, 50), 
                                new Dimension(0,50)));
        this.add(boutonContinuer);
        this.add(new Box.Filler(new Dimension(0,0), 
                                new Dimension(0, 50), 
                                new Dimension(0,50)));
        this.add(bouton3);
        this.add(Box.createVerticalGlue());
        this.add(Box.createVerticalGlue());
    }
    // Permet d'ajouter l'image en fond 
    @Override
    protected void paintComponent(Graphics pinceau) {
        super.paintComponent(pinceau);
        Image dessin = Toolkit.getDefaultToolkit().getImage("./Image/image.png");
        Graphics secondPinceau = pinceau.create();
        secondPinceau.drawImage(dessin,0,0,this.getWidth(),this.getHeight(),this);
    }
  
    /**
     * Initialise un bouton en fonction de son nom
     * @param text le nom du bouton
     * @return JButton 
     */
    private JButton bouton (String text){
            JButton bouton = new JButton(text);
            bouton.setFocusPainted(false);
            bouton.setBorderPainted(false);
            bouton.setBackground(new Color(50,50,50));
            bouton.setForeground(new Color(255,255,255));
            bouton.setFont(new Font("Arial", Font.BOLD, 50));
            bouton.setVerticalAlignment(JButton.CENTER);
            bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
           
            return bouton;
    }

    /**
     * Permet de changer la valeur du bouton continuer du menu d'accueil  
     * @param b un boolean 
     */
    public void boutonContinuerEnable(boolean b){
        this.boutonContinuer.setEnabled(b);
    }
    

}