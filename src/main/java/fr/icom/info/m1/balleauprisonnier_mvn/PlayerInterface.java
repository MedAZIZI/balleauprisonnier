package fr.icom.info.m1.balleauprisonnier_mvn;


import javafx.util.Duration;

public interface PlayerInterface {
	
	public static final double x = 0;
	public static final double y = 0;

	double angle = 90; 
	public final double step = 0;
	public static final String playerColor = "";
	public static final String side = "";

	public  Boolean vie = true;

	
	
	void display();
	void moveLeft();
	void moveRight();
	void turnLeft();
	void turnRight();
	void shoot();
	void spriteAnimate();
	double getY();
	double getX();
	double getAngle();
	Sprite getSprite();
	void setVie(boolean V);
	String getSide();

}
