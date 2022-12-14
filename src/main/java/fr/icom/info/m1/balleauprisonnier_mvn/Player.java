package fr.icom.info.m1.balleauprisonnier_mvn;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * 
 * Classe gerant un joueur
 *
 */
public class Player {
	double x; // position horizontale du joueur
	double y; // position verticale du joueur

	double angle = 90; // rotation du joueur, devrait toujour être en -90 et 90
	double step; // pas d'un joueur
	String playerColor;
	String side;
	//vie 
	Boolean vie;
	// On une image globale du joueur
	Image directionArrow;
	Sprite sprite;
	ImageView PlayerDirectionArrow;
	Projectile P;
	GraphicsContext graphicsContext;
	Image tilesheetImage;
	/**
	 * Constructeur du Joueur
	 * 
	 * @param gc    ContextGraphic dans lequel on va afficher le joueur
	 * @param color couleur du joueur
	 * @param yInit position verticale
	 */
	Player(GraphicsContext gc, String color, int xInit, int yInit, String side) {
		// Tous les joueurs commencent au centre du canvas,
		vie = true;
		x = xInit;
		y = yInit;
		graphicsContext = gc;
		playerColor = color;
		this.side =side;
		angle = 0;
		
		// On charge la representation du joueur
		if (side == "top") {
			directionArrow = new Image("assets/PlayerArrowDown.png");
			tilesheetImage = new Image("assets/PlayerRed.png");
		} else {
			directionArrow = new Image("assets/PlayerArrowUp.png");
			tilesheetImage = new Image("assets/PlayerBlue.png");
		}

		PlayerDirectionArrow = new ImageView();
		PlayerDirectionArrow.setImage(directionArrow);
		PlayerDirectionArrow.setFitWidth(10);
		PlayerDirectionArrow.setPreserveRatio(true);
		PlayerDirectionArrow.setSmooth(true);
		PlayerDirectionArrow.setCache(true);

		// Image tilesheetImage = new Image("assets/orc.png");
		sprite = new Sprite(tilesheetImage, 0, 0, Duration.seconds(.2), side);
		sprite.setX(x);
		sprite.setY(y);
		// directionArrow = sprite.getClip().;

		// Tous les joueurs ont une vitesse aleatoire entre 0.0 et 1.0
		Random randomGenerator = new Random();
		step = randomGenerator.nextFloat();

		// Pour commencer les joueurs ont une vitesse / un pas fixe
		// step = 1;

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Affichage du joueur
	 */
	void display() {
		if(vie) {
			graphicsContext.save(); // saves the current state on stack, including the current transform
			rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
			graphicsContext.drawImage(directionArrow, x, y);
			graphicsContext.restore(); // back to original state (before rotation)
		}
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	/**
	 * Deplacement du joueur vers la gauche, on cantonne le joueur sur le plateau de
	 * jeu
	 */

	void moveLeft() {
		if (x > 10 && x < 520) {
			spriteAnimate();
			x -= step;
		}
	}

	/**
	 * Deplacement du joueur vers la droite
	 */
	void moveRight() {
		if (x > 10 && x < 520) {
			spriteAnimate();
			x += step;
		}
	}

	/**
	 * Rotation du joueur vers la gauche
	 */
	void turnLeft() {
		if (angle >= -90 && angle < 90) {
			angle += 1;
		} else {
			// angle += 1;
		}
		//System.out.println(angle);

	}

	/**
	 * Rotation du joueur vers la droite
	 */
	void turnRight() {
		if (angle > -90 && angle <= 90) {
			angle -= 1;
		} else {
			// angle -= 1;
		}
		//System.out.println(angle);
	}

	void shoot() {
		sprite.playShoot();
	}

	/**
	 * Deplacement en mode boost
	 */
	void boost() {
		x += step * 2;
		spriteAnimate();
	}

	void spriteAnimate() {
		
		if(vie) {
			if (!sprite.isRunning) {
				sprite.playContinuously();
				
			}
			sprite.setX(x);
			sprite.setY(y);
		}
		else {
			sprite.setImage(null);
			
		}
	}

}
//public class Human extand Player {
//	
//	
//	
//	
//}
