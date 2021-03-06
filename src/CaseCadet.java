import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Lewis
 */
public class CaseCadet extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 
     * @param stage 
     */
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login/LoginGUI.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Login to Case Cadet");
            stage.getIcons().add(new Image("LogoLetters.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseCadet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
