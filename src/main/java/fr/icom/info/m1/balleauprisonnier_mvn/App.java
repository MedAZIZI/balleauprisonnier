package fr.icom.info.m1.balleauprisonnier_mvn;
import fr.icom.info.m1.balleauprisonnier_mvn.Computer;
import fr.icom.info.m1.balleauprisonnier_mvn.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	
	/**
	 * En javafx start() lance l'application
	 *
	 * On cree le SceneGraph de l'application ici
	 * @see http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception 
	{
		// Nom de la fenetre
        stage.setTitle("BalleAuPrisonnier");
        Group root = new Group();
        Scene scene = new Scene( root );
        
        // On cree le terrain de jeu et on l'ajoute a la racine de la scene
        Field gameField = Field.Field(scene, 600, 600 );

      
        root.getChildren().add(gameField);
        root.getChildren().add(gameField.getJoueurs()[0].getSprite());
		root.getChildren().add(gameField.getJoueurs()[1].getSprite());
		
		//System.out.print(PlayerInterface.sprite);
		root.getChildren().add(gameField.getComputers()[0].getSprite());
		root.getChildren().add(gameField.getComputers()[1].getSprite());
		root.getChildren().add(gameField.getComputers()[2].getSprite());
		root.getChildren().add(gameField.getComputers()[3].getSprite());
//		
        // On ajoute la scene a la fenetre et on affiche
        stage.setScene( scene );
        stage.show();
	}
	
    public static void main(String[] args) 
    {
        //System.out.println( "Hello World!" );
    	Application.launch(args);
    }
}
