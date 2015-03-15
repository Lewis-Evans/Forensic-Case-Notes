/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createcase;

import hibernate.HibernateUtilities;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import login.LoginGUIController;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class CreateCaseController implements Initializable {

    // FXML Annotated Components
    @FXML
    private TextField caseIDTextField;
    @FXML
    private DatePicker dateCaseOpenedDatePicker;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addCaseButton;
    @FXML
    private ChoiceBox caseTypeChoiceBox;
    @FXML
    private ProgressIndicator caseProgressIndicator;
    @FXML
    private TextArea caseNotesTextArea;
    @FXML
    private ProgressBar createCaseProgressBar;
    @FXML
    private TextField caseDateTimeCaseCreated;

    static CaseFile newCase;

    public static CaseFile getNewCase() {
        return newCase;
    }

    public static void setNewCase(CaseFile caseToSet) {
        newCase = caseToSet;
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        System.out.println("Clicked on Cancel Button");
        System.exit(1);
    }

    private static long caseNumber;

    public static long getCaseNumber() {
        return caseNumber;
    }

    public static void setCaseNumber(long caseNumber) {
        CreateCaseController.caseNumber = caseNumber;
    }

    @FXML
    void handleAddCaseButton(ActionEvent event) {

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session theSession = sFactory.openSession();
        theSession.beginTransaction();

        caseNumber = Integer.parseInt(caseIDTextField.getText());
        System.out.println("Clicked on Add Case Button");

        newCase = new CaseFile();
        newCase.setCaseID(caseNumber);

        newCase.setCaseType(caseTypeChoiceBox.getValue().toString());
        newCase.setDateCaseOpened(LocalDate.now());
        newCase.setCaseStatus("Active");

        // Set Date and Time the Case was Created (Current Date/Time)
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        currentDateTime.format(format);

        newCase.setDateTimeAdded(currentDateTime);

        // test output of date/time
        System.out.println(currentDateTime.format(format));

        theSession.saveOrUpdate(newCase);
        // theSession.saveOrUpdate(initialNotes);
        theSession.getTransaction().commit();
        theSession.close();

        // save the case which has just been created
        // Config the summary screen to show the Case ID and Type of Case currently open
        System.out.println("Case Details - " + "CaseID: " + caseNumber + " Case Type: " + caseTypeChoiceBox.getValue().toString());
        try {
            // launch the case!
            GridPane root;
            root = FXMLLoader.load(getClass().getResource("/casesummary/CaseSummary.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Case Summary Screen - " + "[CaseID: " + caseIDTextField.getText() + "]  " + "[Case Type: " + caseTypeChoiceBox.getValue().toString() + "]");
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest((WindowEvent t) -> {

                System.out.println("CLOSING");
                System.exit(1);
            });
            //hide this current window
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(LoginGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set the current date and time for the creation date of this electornic case file
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        caseDateTimeCaseCreated.setText(LocalDateTime.now().format(format));

        // provide the current date as the default from which the physical case was created
//        dateCaseOpenedDatePicker.setValue(LocalDate.now());
        caseTypeChoiceBox.getItems().addAll(
                "Child Exploitation", "Cyber Extortion", "Cyber Stalking", "Cyber Terrorism",
                "Fraud", "Hacking", "Identity Theft", "Malicious Software", "Theft", "Other"
        );

    }

}
