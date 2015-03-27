/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewcases;

import createcase.CaseFile;
import createcase.CreateCaseController;
import evidence.Evidence;
import hibernate.HibernateUtilities;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class ViewCasesController implements Initializable {

    // FXML Annotated Components
    @FXML
    private Button openCaseButton;
    @FXML private Button refreshButton;
    @FXML
    private TableColumn<CaseFile, Integer> column1CaseID;
    @FXML
    private TableView<CaseFile> availableCasesTableView;
    @FXML
    private TableColumn<CaseFile, String> column2CaseType;
    @FXML
    private TableColumn<CaseFile, LocalDateTime> column3DateTimeAdded;
    @FXML TableColumn<CaseFile, String> column4CaseStatus;
    @FXML
    private Button createCaseButton;
    @FXML
    private TextField caseSelectionTextField;

    @FXML
    private void handleCreateCaseButton(ActionEvent event) {
        Parent root;
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("/createCase/CreateCase.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Case Creation Screen");
            //stage.getIcons().add(new Image("/cssthemes/logobig.jpg"));
            stage.getIcons().add(new Image("LogoLetters.png"));
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest((WindowEvent t) -> {
                System.out.println("CLOSING");
                System.exit(1);
            });
        } catch (IOException ex) {
            Logger.getLogger(ViewCasesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    private void handleDeleteButton(ActionEvent event){
          System.out.println("Clicked on Delete Case Button inside View Cases Window");
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        session.delete(session.get(CaseFile.class, Long.parseLong(caseSelectionTextField.getText())));

        caseSelectionTextField.setVisible(false);
        caseSelectionTextField.setVisible(true);

        session.getTransaction().commit();
        session.close();
    }
    
    @FXML
    private void handleRefreshButton(ActionEvent event){
        availableCasesTableView.getItems().clear();
        getAllCaseFiles();
    }

    //  private CaseFile currentSelectedCase;
    @FXML
    private void handleOpenCaseButton(ActionEvent event) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        CaseFile openedCase = (CaseFile) session.get(CaseFile.class, Long.parseLong(caseSelectionTextField.getText()));
        CreateCaseController.setNewCase(openedCase);
        CreateCaseController.setCaseNumber(Integer.parseInt(caseSelectionTextField.getText()));

        GridPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/casesummary/CaseSummary.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Case Summary Screen - " + "[CaseID: " + openedCase.getCaseID() + "]  " + "[Case Type: " + openedCase.getCaseType() + "]");
        
            stage.getIcons().add(new Image("LogoLetters.png"));
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest((WindowEvent t) -> {

                System.out.println("CLOSING");
                System.exit(1);
            });
            //hide this current window
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ViewCasesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        session.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        column1CaseID.setCellValueFactory(new PropertyValueFactory("caseID"));

        column2CaseType.setCellValueFactory(new PropertyValueFactory("caseType"));
        column2CaseType.setPrefWidth(150);

        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
         
        column3DateTimeAdded.setCellValueFactory(new PropertyValueFactory("dateTimeAdded"));
        column3DateTimeAdded.setCellFactory(column -> {
            return new TableCell<CaseFile, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(format.format(item));
                    }
                }
            };
        });
        
        column4CaseStatus.setCellValueFactory(new PropertyValueFactory("caseStatus"));
        
        
        getAllCaseFiles();

    }

    private void getAllCaseFiles() {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        //session.createCriteria(CaseFile.class).list();
        List<CaseFile> caseFiles = session.createCriteria(CaseFile.class).list();
        for (CaseFile aCaseFile : caseFiles) {
            System.out.println("Case File: " + aCaseFile.getCaseID());

            availableCasesTableView.getItems().add(aCaseFile);
        }

        session.close();
    }

}
