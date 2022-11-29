package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.util.concurrent.TimeUnit;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas {
	
	/** Joueurs */
	Player [] joueurs = new Player[2];
	Player [] computers = new Player[4];
	/** tirs */
	Projectile []  tir = new Projectile[6];
	 
	
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
        computers[0] = new Player(gc, colorMap[0], w/2 + 40 , h-65, "bottom");
        computers[0].display();
    	
        computers[1] = new Player(gc, colorMap[0], w/2 - 80 , h-65, "bottom");
        computers[1].display();
    	
        computers[2] = new Player(gc, colorMap[1], w/2 + 40, 20, "top");
        computers[2].display();
        
        computers[3] = new Player(gc, colorMap[1], w/2 - 80 , 20, "top");
        computers[3].display();

    	/** initialisation des joueurs */
        joueurs[0] = new Player(gc, colorMap[1], w/2-20 , 20, "top");
    	joueurs[0].display();
    	
    	joueurs[1] = new Player(gc, colorMap[1], w/2-20, h-65, "bottom");
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
	        				tir[i] = new Projectile(gc,joueurs[i].x+30,joueurs[i].y+80 ,4,joueurs[i].angle,"top");
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
	        				tir[i] = new Projectile(gc,joueurs[i].x+30,joueurs[i].y+60 ,3,joueurs[i].angle,"bottom");
	        			}
	        		}
	        		
	        		joueurs[i].display();
	        		computers[i].display();
	        		computers[i+2].display(); // kayen double nta3 "i" f computer 
	        		distroy_projectile(tir);
	        		colision(tir,computers);
	        		colision(tir,joueurs);
	        		moveComputer(computers);
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
	public void colision(Projectile [] P,Player[] J) {
		for(int i =0;i<P.length;i++) {
			
			if(P[i] != null) {
				for(int j=0;j<J.length;j++) {
					if(P[i].side!=J[j].side && P[i].getX() > J[j].getX() &&  P[i].getX()+10 < J[j].getX()+95) {
						if(P[i].getY() > J[j].getY() &&  P[i].getY()+10 < J[j].getY()+95) {
							// to do apres colision
							P[i] = null; 			// arreter le projectile 
							J[j].vie = false; 		// mettre fin a la vie du joueur
							J[j].spriteAnimate();	// faire disparaitre le joueur
							break; 					// sortir de la boucle 
						}
					}
						
				}
			}
			
		}
	}
	public void moveComputer(Player[] C) {
		for(int i =0;i<C.length;i++) {
			if(C[i].getX() < this.width && C[i].getX() > 0) {
				Random randomGenerator = new Random();
				int m = randomGenerator.nextInt(2);
				//fake IA 
				if (m == 0) {
					C[i].moveLeft();
//					C[i].moveLeft();
				}else {
					C[i].moveRight();
//					C[i].moveRight();
				}
				
			}
		}
	}
	public Player[] getJoueurs() {
		return joueurs;
	}
	public Player[] getComputers() {
		return computers;
	}
	
}
