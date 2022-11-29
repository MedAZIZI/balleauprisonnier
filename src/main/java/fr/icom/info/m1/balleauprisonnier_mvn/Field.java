package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import controllers.PlayerController;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas {
	
	
	private static Field single_field = null;
	
	/** Joueurs */
	PlayerInterface [] joueurs = new PlayerInterface[2];
	PlayerInterface [] computers = new PlayerInterface[4];
	/** tirs */
	Projectile []  tir = new Projectile[6];
	PlayerController JoueurFactory = new PlayerController();
	
	/** Couleurs possibles */
	String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();

    final GraphicsContext gc;
    final int width;
    final int height;
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
    Image bgImg =  new Image("assets/field.png");
    
    
    // verifier la creation d'un seul terrain de jeu  a l'aide du design pattern singleton
    // @Singelton
    public static Field Field(Scene scene, int w, int h)
    {
        // To ensure only one instance is created
        if (single_field == null) {
        	single_field = new Field(scene,w,h);
        }
        return single_field;
    }
    
    
	public Field(Scene scene, int w, int h) 
	{
			super(w, h); 
			width = w;
			height = h;
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		
        gc = this.getGraphicsContext2D();
  
        /** On initialise le terrain de jeu */
        /** initialisation des joueurs */
        computers[0] = JoueurFactory.getPlayer("Computer", gc, colorMap[0], w/2 + 40 , h-65, "bottom");
        computers[0].display();
    	
        computers[1] = JoueurFactory.getPlayer("Computer",gc, colorMap[0], w/2 - 80 , h-65, "bottom");
        computers[1].display();
    	
        computers[2] = JoueurFactory.getPlayer("Computer",gc, colorMap[1], w/2 + 40, 20, "top");
        computers[2].display();
        
        computers[3] = JoueurFactory.getPlayer("Computer",gc, colorMap[1], w/2 - 80 , 20, "top");
        computers[3].display();

    	/** initialisation des joueurs */
        joueurs[0]  = JoueurFactory.getPlayer("Player",gc, colorMap[1], w/2-20 , 20, "top");
    	joueurs[0].display();
    	
    	joueurs[1] = JoueurFactory.getPlayer("Player",gc, colorMap[1], w/2-20, h-65, "bottom");
    	joueurs[1].display();
    	
    	

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            // only add once... prevent duplicates
	    	            if ( !input.contains(code) )
	    	                input.add( code );
	    	        }
	    	    });

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            input.remove( code );
	    	        }
	    	    });
	    
	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
	    new AnimationTimer() 
	    {
	    	
	        public void handle(long currentNanoTime)
	        {	
	        	
	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);
	            gc.drawImage(bgImg, 0, 0);
	        	
	            // Deplacement et affichage des joueurs
	        	for (int i = 0; i < joueurs.length; i++) 
	    	    {   
	        		if (i==0 && input.contains("LEFT"))
	        		{
	        			joueurs[i].moveLeft();
	        		} 
	        		if (i==0 && input.contains("RIGHT")) 
	        		{
	        			joueurs[i].moveRight();	        			
	        		}
	        		if (i==0 && input.contains("UP"))
	        		{
	        			joueurs[i].turnLeft();
	        		} 
	        		if (i==0 && input.contains("DOWN")) 
	        		{
	        			joueurs[i].turnRight();	        			
	        		}
	        		if (i==0 && input.contains("M")){
	        			
	        			joueurs[i].shoot();
	        			if(tir[i] == null) {
	        				tir[i] = new Projectile(gc,joueurs[i].getX()+30,joueurs[i].getY()+80 ,4,joueurs[i].getAngle(),"top");
	        			}
					}
	        		if (i==1 && input.contains("Q"))
	        		{
	        			joueurs[i].moveLeft();
	        		} 
	        		if (i==1 && input.contains("D")) 
	        		{
	        			joueurs[i].moveRight();	        			
	        		}
	        		if (i==1 && input.contains("Z"))
	        		{
	        			joueurs[i].turnLeft();
	        		} 
	        		if (i==1 && input.contains("S")) 
	        		{
	        			joueurs[i].turnRight();	        			
	        		}
	        		if (i==1 && input.contains("SPACE")){
	        			joueurs[i].shoot();
	        			if(tir[i] == null) {
	        				tir[i] = new Projectile(gc,joueurs[i].getX()+30,joueurs[i].getY()+60 ,4,joueurs[i].getAngle(),"bottom");
	        			}
	        		}
	        		
	        		joueurs[i].display();
	        		computers[i].display();
	        		computers[i+2].display(); // kayen double nta3 "i" f computer 
	        		distroy_projectile(tir);
	        		colision(tir,computers);
	        		colision(tir,joueurs);
	        		//moveComputer(computers);
	    	    }
	        	for (int i = 0; i < tir.length; i++) 
	    	    {
	        		if(tir[i] != null) {
	        			tir[i].tir();
	        		}
	    	    }
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
		
	}	
	public void distroy_projectile(Projectile [] P ) {
		for(int i = 0;i<P.length;i++) {
			if(P[i] != null) {
				double x = P[i].getX();
				double y = P[i].getY();
				if (x>600 || x<0 ||y>600 || y<0) {
					P[i] = null;
				}
			}
		}
	}
	public void colision(Projectile [] P,PlayerInterface[] player) {
		for(int i =0;i<P.length;i++) {
			
			if(P[i] != null) {
				for(int j=0;j<player.length;j++) {
//					System.out.println( "side " + player[i].getSide() + " "+  P[i].getX() + " " + player[j].getX());
					if(P[i].side!= player[j].getSide() && 
							    P[i].getX()    > player[j].getX() 
							&&  P[i].getX()    < player[j].getX()+90) {
						
						if(     P[i].getY()    > player[j].getY() &&  
								P[i].getY()    < player[j].getY()+95) {
							
							// to do apres colision
							System.out.print("t9aaaaaaaas");
							P[i] = null; 			// arreter le projectile 
							player[j].setVie(false); 		// mettre fin a la vie du joueur
							player[j].spriteAnimate();	// faire disparaitre le joueur
							break; 					// sortir de la boucle 
							
						}
					}
						
				}
			}
			
		}
	}
	public void moveComputer(PlayerInterface[] computers2) {
		for(int i =0;i<computers2.length;i++) {
			if(computers2[i].getX() < this.width && computers2[i].getX() > 0) {
				Random randomGenerator = new Random();
				int m = randomGenerator.nextInt(2);
				//fake IA 
				if (m == 0) {
					computers2[i].moveLeft();
//					C[i].moveLeft();
				}else {
					computers2[i].moveRight();
//					C[i].moveRight();
				}
				
			}
		}
	}
	public PlayerInterface[] getJoueurs() {
		return joueurs;
	}
	public PlayerInterface[] getComputers() {
		return computers;
	}
	
}
