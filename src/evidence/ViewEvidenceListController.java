    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidence;

import casesummary.CaseSummaryController;
import createcase.CreateCaseController;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import hash.ChecksumDetails;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class ViewEvidenceListController implements Initializable {

    @FXML
    private TextField evidenceIDTextField;
    @FXML
    private Button viewEditEvidenceButton;
    @FXML
    private Button addEvidenceButton;
    @FXML
    private TableColumn<Evidence, String> columnEvidenceManufacturer;
    @FXML
    private TableColumn<Evidence, String> columnEvidenceModel;

    @FXML
    private TableColumn<Evidence, Long> columnEvidenceID;
    @FXML
    private TableView<Evidence> evidenceTableView;
    @FXML
    private TableColumn<Evidence, String> columnEvidenceType;
    @FXML
    private TableColumn<Evidence, LocalDateTime> dateTimeAddedColumn;
    @FXML
    private Button deleteEvidenceButton;

    private static long currentOpenedEvidenceID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        //List<Evidence> evidenceList = session.createQuery("from Evidence ").list();
        long currentCaseId = CreateCaseController.getCaseNumber();
        Query query = session.createQuery("from Evidence where caseFile = " + currentCaseId);
        System.out.println(query.toString());

        List<Evidence> evidenceForCase = (List<Evidence>) query.list();
        System.out.println(evidenceForCase);

        columnEvidenceID.setCellValueFactory(new PropertyValueFactory("evidenceID"));
        columnEvidenceType.setCellValueFactory(new PropertyValueFactory("evidenceType"));
        columnEvidenceManufacturer.setCellValueFactory(new PropertyValueFactory("evidenceManufacturer"));
        columnEvidenceModel.setCellValueFactory(new PropertyValueFactory("evidenceModel"));
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);

        dateTimeAddedColumn.setCellValueFactory(new PropertyValueFactory("evidenceDateTimeAdded"));
        dateTimeAddedColumn.setCellFactory(column -> {
            return new TableCell<Evidence, LocalDateTime>() {
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
        

        evidenceTableView.getItems().addAll(evidenceForCase);
        session.getTransaction().commit();
        session.close();
    }

    @FXML
    void handleAddEvidenceButton(ActionEvent event) {
        System.out.println("Clicked on Add Evidence Button inside View Evidence Window");
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/evidence/AddEvidence.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Evidence..");
            stage.getIcons().add(new Image("logobig.jpg"));
            stage.setScene(new Scene(root, 720, 430));
            stage.setMinHeight(430);
            stage.setMinWidth(720);
            stage.setMaxHeight(430);
            stage.setMaxWidth(720);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleViewEditEvidenceButton() {
//        setCurrentOpenedEvidenceID(Long.parseLong(evidenceIDTextField.getText()));

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        Evidence openedEvidence = (Evidence) session.get(Evidence.class, Long.parseLong(evidenceIDTextField.getText()));
        ViewEvidenceListController.setCurrentOpenedEvidence(openedEvidence);
        ViewEvidenceListController.setCurrentOpenedEvidenceID(Integer.parseInt(evidenceIDTextField.getText()));

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/evidence/ViewEvidence.fxml"));
            Stage stage = new Stage();
            stage.setTitle("View Evidence");
            stage.getIcons().add(new Image("logobig.jpg"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleRefreshButton(ActionEvent event) {
        System.out.println("Clicked on View/Edit Evidence Button inside View Evidence Window");
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        // List<Evidence> emptyList = new ArrayList<>();
        evidenceTableView.getItems().clear();

        //List<Evidence> evidenceList = session.createQuery("from Evidence ").list();
        long currentCaseId = CreateCaseController.getCaseNumber();
        Query query = session.createQuery("from Evidence where caseFile = " + currentCaseId);
        System.out.println(query.toString());

        List<Evidence> evidenceForCase = (List<Evidence>) query.list();
        System.out.println(evidenceForCase);

        evidenceTableView.getItems().addAll(evidenceForCase);

        session.getTransaction().commit();
        session.close();
    }

    @FXML
    void handleDeleteEvidenceButton(ActionEvent event) {
        System.out.println("Clicked on Delete Evidence Button inside View Evidence Window");
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        session.delete(session.get(Evidence.class, Long.parseLong(evidenceIDTextField.getText())));

        session.getTransaction().commit();
        session.close();
    }

    public static long getCurrentOpenedEvidenceID() {
        return currentOpenedEvidenceID;
    }

    public static void setCurrentOpenedEvidenceID(long currentOpenedEvidenceID) {
        ViewEvidenceListController.currentOpenedEvidenceID = currentOpenedEvidenceID;
    }

    static Evidence currentOpenedEvidence;

    public static Evidence getCurrentOpenedEvidence() {
        return currentOpenedEvidence;
    }

    public static void setCurrentOpenedEvidence(Evidence currentOpenedEvidence) {
        ViewEvidenceListController.currentOpenedEvidence = currentOpenedEvidence;
    }

    public TableView<Evidence> getEvidenceTableView() {
        return evidenceTableView;
    }

    public void setEvidenceTableView(TableView<Evidence> evidenceTableView) {
        this.evidenceTableView = evidenceTableView;
    }

}
