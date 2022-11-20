package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Projectile {
	double x;     // position horizontale du Projectiole
	double y; 	  // position verticale du Projectiole
	private int vitesse ; 
	private double direction ; 
	
	Sprite sprite;
	
	GraphicsContext graphicsContext;
	Image ProjImg;
	ImageView ProjectileImg;
	
	/**
	   * Constructeur du Proectile
	   * 
	   * @param gc ContextGraphic dans lequel on va afficher le joueur
	   * @param color couleur du joueur
	   * @param yInit position verticale
	   */
	Projectile(GraphicsContext gc,double xInit, double yInit, int Vit, double Dir )
  	{
		x = xInit;               
	    y = yInit;
		graphicsContext = gc;
		direction = Dir;
		
		ProjImg = new Image("assets/ball.png");
		
		ProjectileImg = new ImageView();
		ProjectileImg.setImage(ProjImg);
		ProjectileImg.setFitWidth(10);
		ProjectileImg.setPreserveRatio(true);
		ProjectileImg.setSmooth(true);
		ProjectileImg.setCache(true);
		
        
  	}
	  /**
	   *  Affichage du Projectile
	   */
	void display()
	{
		  graphicsContext.save(); // saves the current state on stack, including the current transform
		  graphicsContext.drawImage(ProjImg, x, y);
		  graphicsContext.restore();
	}

	public int getVitesse() {
		return vitesse;
	}
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
	public double getDirection() {
		return direction;
	}
	public void setDirection(double direction) {
		this.direction = direction;
	}
	public void tir() {
		y +=  Math.sin(getDirection());
		x +=  Math.cos(getDirection());
		this.display();
		
	}
		
		
}
