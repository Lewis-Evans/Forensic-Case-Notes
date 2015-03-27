package evidence;

import createcase.CreateCaseController;
import hibernate.HibernateUtilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class AddEvidenceController {

    // FXML Annotated Components
    @FXML
    private TextField evidenceIDTextField;
    @FXML
    private TextField evidenceTypeTextField;
    @FXML
    private TextField evidenceManufacturerTextField;
    @FXML
    private TextArea evidenceTextArea;
    @FXML
    private TextField modelTextField;
    @FXML
    private Button browseForImageButton;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private Button addEvidenceButton;
    @FXML
    private ChoiceBox evidenceTypeChoiceBox;
    @FXML
    private DatePicker manufactureDateDatePicker;
    @FXML
    private ImageView evidenceImageViewer;

    byte[] byteFile;
    File file;

    @FXML
    void browseForImage(ActionEvent event) throws IOException {
        System.out.println("Clicked on Image Browse Button");

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        // chooser.setInitialDirectory(new File("C:\\Users\\Lewis\\Dropbox\\Uni 14-15\\Project\\Images"));

        file = chooser.showOpenDialog(new Stage());
        String localUrl = file.toURI().toURL().toString();
        Image localImage = new Image(localUrl, false);

        System.out.println(localImage);

        byteFile = new byte[(int) file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(byteFile);
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        evidenceImageViewer.setImage(localImage);
    }

    @FXML
    void handleAddEvidenceButton(ActionEvent event) {
        System.out.println("Add Evidence Button clicked in Add Evidence Window");

        Evidence theEvidence = new Evidence();

        // Set Evidence ID
        theEvidence.setEvidenceID(Integer.parseInt(evidenceIDTextField.getText()));
        // Set Evidence Type
        theEvidence.setEvidenceType(evidenceTypeChoiceBox.getValue().toString());
        // Set Evidence Manufacturer
        theEvidence.setEvidenceManufacturer(evidenceManufacturerTextField.getText());
        // Set Evidence Model
        theEvidence.setEvidenceModel(modelTextField.getText());
        // Set the Manufactured Date
        LocalDate locald = manufactureDateDatePicker.getValue();
        theEvidence.setEvidenceManufacturedDate(locald);
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        LocalDateTime now = LocalDateTime.now();
        now.format(format);
        theEvidence.setEvidenceDateTimeAdded(now);
        // Create a EvidenceNote Object to store the note

        theEvidence.setCaseFile(CreateCaseController.getNewCase());

        // Set the Evidence Image 
        theEvidence.setEvidenceImage(byteFile);

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session theSession = sFactory.openSession();
        theSession.beginTransaction();
        theSession.saveOrUpdate(theEvidence);

        theSession.getTransaction().commit();
        theSession.close();

        theSession = sFactory.openSession();
        theSession.beginTransaction();

//        System.out.println(getEvidence.getEvidenceNotes());
        theSession.close();
        if (toggleButton.isSelected()) {
            // clear the fields
            evidenceIDTextField.clear();;
            evidenceTypeChoiceBox.setValue("Other");
            evidenceManufacturerTextField.clear();
            modelTextField.clear();
            evidenceTextArea.clear();
            evidenceImageViewer.setImage(new Image("placeholder.png"));
        } else {
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }

    }

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        Tooltip toggleButtonTooltip = new Tooltip(
                "ON - Do Not Automatically Close Window\n");
        Tooltip.install(toggleButton, toggleButtonTooltip);

        // initialise ChoiceBox Values
        evidenceTypeChoiceBox.getItems()
                .addAll(
                        "Mobile Phone", "HDD", "SSD", "File", "Flash Drive",
                        "Other", "Printer", "Scanner", "Tablet", "Gaming Console"
                );
    }

}
