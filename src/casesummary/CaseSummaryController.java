package casesummary;

import createcase.CaseFile;
import createcase.CaseNote;
import createcase.CreateCaseController;
import hibernate.HibernateUtilities;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.Query;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class CaseSummaryController {

    // The current opened case
    CaseFile theCurrentCase;

    // FXML Annotated Components
    @FXML
    private MenuItem closeButton;
    @FXML
    private TabPane caseNotesTabs;
    @FXML
    private MenuItem testButton;
    @FXML
    private ScatterChart timelineScatterChart;
    @FXML
    private TabPane evidenceTabPane;
    @FXML
    private MenuItem addEvidenceButton;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem saveButton;
    @FXML 
    private MenuItem caseTasksMenuItem;
    @FXML
    private MenuItem MD5MenuItem;
    @FXML
    private MenuItem viewEvidenceMenuItem;
    @FXML
    private TableView caseToDoTable;
    @FXML
    private TableColumn columnA;
    @FXML
    private TableColumn columnB;
    @FXML
    private TextArea caseFile;
    @FXML
    private PieChart pieChart;
    @FXML
    private Button addNoteButton;
    @FXML
    private Button printButton;
    @FXML
    private TextArea caseNotesTextArea;
    @FXML
    private Button refreshButton;

    @FXML
    void handleCloseButton(ActionEvent event) {
        System.out.println("Cancel button clicked");
        System.exit(1);

    }

    @FXML
    void handleCreateTaskButton(ActionEvent event) {
        System.out.println("Create Evidence Button Clicked");
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/tasks/AddTask.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Create Task");
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleAddEvidenceButton(ActionEvent event) {
        System.out.println("Add Evidence Button Clicked!");
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/evidence/AddEvidence.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Evidence..");
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void handleSaveButton(ActionEvent event) {
        System.out.println("Clicked on Save Button");

    }

    @FXML
    private TableView<CaseFile> table;

    @FXML
    void handleTestButton(ActionEvent event) {
        SessionFactory sFactory = hibernate.HibernateUtilities.getSessionFactory();
        Session theSession = sFactory.openSession();
        theSession.beginTransaction();
        System.out.println("Getting the Current Case Details..");
        //CaseDetails testCase = new CaseDetails();
        CaseFile testCase = (CaseFile) theSession.get(CaseFile.class, CreateCaseController.getCaseNumber());
        System.out.println("Case ID: " + testCase.getCaseID());
        System.out.println("Case Type: " + testCase.getCaseType());
        System.out.println("Case notes: " + testCase.getCaseNotes());
        System.out.println("Case date: " + testCase.getDateCaseOpened().toString());
        theSession.close();
    }

    @FXML
    void handleAboutButton(ActionEvent event) {
        try {
            System.out.println("Clicked on About Button");
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/casesummary/AboutDialogBox.fxml"));
            Stage stage = new Stage();
//            stage.setMaxWidth(350);
//            stage.setMaxHeight(300);
//            stage.setMinWidth(350);
//            stage.setMinHeight(300);
            stage.setTitle("About CaseCadet");
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleHashGenButton(ActionEvent event) {
        try {
            System.out.println("Clicked on MD5 File Button");
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/hash/HashFiles.fxml"));
            Stage stage = new Stage();
            stage.setTitle("MD5 a File");
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleViewTasksButton(ActionEvent event){
        try {
            System.out.println("Clicked on View Tasks Button");
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/tasks/ViewTasksList.fxml"));
            Stage stage = new Stage();
            stage.setTitle("View Tasks");
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Initializes the controller class.
     */
    public void initialize() {

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        caseNotesTextArea.clear();

        Query query = session.createQuery("from CaseNote where caseFile = " + CreateCaseController.getCaseNumber());

        List<CaseNote> notesForCase = (List<CaseNote>) query.list();

        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        for (CaseNote notesForCase1 : notesForCase) {
            caseNotesTextArea.appendText(notesForCase1.getDateTimeAdded().format(format) + "\n");
            caseNotesTextArea.appendText(notesForCase1.getNotes() + "\n\n");
        }

        session.close();
    }

    @FXML
    void handleAddNoteButton(ActionEvent event) {
        System.out.println("Clicked on Add Note Button");
        try {
            System.out.println("Clicked on Add Note in the Case Summary Screen");
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/casenotes/AddNote.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Note to Case: " + CreateCaseController.getCaseNumber());
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handlePrintButton(Event event) {

        print(caseNotesTextArea);

    }

    void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
    
  

    @FXML
    private void handleViewEvidenceButton(ActionEvent event) {
        try {
            System.out.println("Clicked on View Evidence Button in Case Summary Screen");
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/evidence/ViewEvidenceList.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Evidence for Case: " + CreateCaseController.getCaseNumber());
            stage.getIcons().add(new Image("logoletters.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleRefreshButton(ActionEvent event) {
        caseNotesTextArea.getTransforms().clear();
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        caseNotesTextArea.clear();

        Query query = session.createQuery("from CaseNote where caseFile = " + CreateCaseController.getCaseNumber());

        List<CaseNote> notesForCase = (List<CaseNote>) query.list();

        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        notesForCase.stream().map((notesForCase1) -> {
            caseNotesTextArea.appendText(notesForCase1.getDateTimeAdded().format(format) + "\n");
            return notesForCase1;
        }).forEach((notesForCase1) -> {
            caseNotesTextArea.appendText(notesForCase1.getNotes() + "\n\n");
        });

        session.close();
    }
}
