package controllers;

import fr.icom.info.m1.balleauprisonnier_mvn.Computer;
import fr.icom.info.m1.balleauprisonnier_mvn.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.PlayerInterface;
import javafx.scene.canvas.GraphicsContext;

public class PlayerController {

	// creation des joueurs a l'aide du design pattern Factory
	// @Factory 
	public PlayerInterface getPlayer(String PlayerType, GraphicsContext gc, String color, int xInit, int yInit,
			String side) {

		if (PlayerType == null) {
			return null;
		}
		if (PlayerType.equalsIgnoreCase("Player")) {
			return new Player(gc, color, xInit, yInit, side);

		} else if (PlayerType.equalsIgnoreCase("Computer")) {
			return new Computer(gc, color, xInit, yInit, side);
		}
		
		return null;

	}
}
