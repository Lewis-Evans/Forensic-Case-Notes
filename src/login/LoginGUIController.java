package login;

import createcase.CaseFile;
import hibernate.HibernateUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class LoginGUIController implements Initializable {

    // FXML Annotated Components
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;

    private String appUserName = "Lewis";
    private String appPassword = "lol";

    private CaseFile cas;

    @FXML
    void loginActionButton(ActionEvent event) {

        authenticateUser(event);
    }

    /**
     * Provide authentication
     *
     * @param event
     * @throws HibernateException
     */
    private void authenticateUser(ActionEvent event) throws HibernateException {
        if (usernameTextField.getText().equals(appUserName)) {
            System.out.println("Username Correct");
            if (passwordPasswordField.getText().equals(appPassword)) {
                try {

                    System.out.println("Password Correct");
                    SessionFactory sFactory = HibernateUtilities.getSessionFactory();
                    Session session = sFactory.openSession();
                    session.beginTransaction();

                    // get the cases and store in a list
                    List<CaseFile> list = session.createCriteria(CaseFile.class).list();

                    session.close();

                    //list.get(0).getClass()==CaseFile.class
                    if (list.size() >= 1) {
                        // there are cases in the database - ask the user to choose
                        openViewCasesWindow();
                    } else {
                        // Create a new Case
                        openCreateCaseWindow();
                    }
                    //hide this current window
                    ((Node) (event.getSource())).getScene().getWindow().hide();

//                stage.setTitle("Login to CaseCadet");
//                stage.setScene(scene);
//                stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Credentials Incorrect");
        }
    }

    private void openViewCasesWindow() throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/viewcases/ViewCases.fxml"));
        Stage stage = new Stage();
        stage.setTitle("View Cases");
        stage.getIcons().add(new Image("LogoLetters.png"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest((WindowEvent t) -> {
            System.out.println("CLOSING");
            System.exit(1);
        });
    }

    private void openCreateCaseWindow() throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/createCase/CreateCase.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Case Creation Screen");
        stage.getIcons().add(new Image("LogoLetters.png"));
        //stage.getIcons().add(new Image("/cssthemes/logobig.jpg"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest((WindowEvent t) -> {
            System.out.println("CLOSING");
            System.exit(1);
        });
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // provide the correct details to get into app quicker - testing purposes 
        usernameTextField.setText("Lewis");
        passwordPasswordField.setText("lol");

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();
        System.out.println("--- Printing All Cases in Database ---");
        //System.out.println(session.createCriteria(CaseFile.class).list());
        List<CaseFile> list = session.createCriteria(CaseFile.class).list();
        for (CaseFile list1 : list) {
            System.out.println("------ Case " + list1 + "------");
            System.out.println(list1.getCaseID());
            System.out.println(list1.getCaseType());
            System.out.println(list1.getCaseNotes());
            System.out.println(list1.getDateCaseOpened());
        }
        System.out.println("--- End of List ---");
        session.close();

    }

}
