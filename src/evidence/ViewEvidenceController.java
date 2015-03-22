/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidence;

import casesummary.CaseSummaryController;
import createcase.CaseNote;
import createcase.CreateCaseController;
import hibernate.HibernateUtilities;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.text.DateFormatter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class ViewEvidenceController implements Initializable {
    
    @FXML
    private TextField evidenceIDTextField;
    
    @FXML
    private TextField evidenceTypeTextField;
    
    @FXML
    private Button addNoteButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private TextField evidenceManufacturerTextField;
    
    @FXML
    private TextField modelTextField;
    
    @FXML
    private ImageView evidenceImageViewer;
    
    @FXML
    private Button printNotesButton;
    
    @FXML
    private TextField manufacturedDateTextField;
    
    @FXML
    private Button browseForImageButton;
    
    @FXML
    private TextArea evidenceNoteTextArea;
    
    @FXML
    void browseForImage(ActionEvent event) {
        
    }
    
    @FXML
    void handleAddNotesButton(ActionEvent event) {
        
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/evidence/AddEvidenceNote.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Evidence Note");
            stage.getIcons().add(new Image("logobig.jpg"));
            stage.setScene(new Scene(root));
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaseSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    void handleRefreshButton(ActionEvent event) {
        evidenceNoteTextArea.clear();
        evidenceNoteTextArea.getTransforms().clear();
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("from EvidenceNote where evidence = " + ViewEvidenceListController.getCurrentOpenedEvidenceID());
        
        List<EvidenceNote> notesForEvidence = (List<EvidenceNote>) query.list();
        
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        notesForEvidence.stream().map((notesForEvidence1) -> {
            evidenceNoteTextArea.appendText(notesForEvidence1.getDateTimeAdded().format(format) + "\n");
            return notesForEvidence1;
        }).forEach((notesForEvidence1) -> {
            evidenceNoteTextArea.appendText(notesForEvidence1.getNote() + "\n\n");
        });
        
        session.close();
    }
    
    @FXML
    void handlePrintNotesButton(ActionEvent event) {
        print(evidenceNoteTextArea);
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();
        // fill fields
        
        Evidence currentOpenedEvidence = (Evidence) session.get(Evidence.class, ViewEvidenceListController.getCurrentOpenedEvidenceID());
        evidenceIDTextField.setText(Long.toString(currentOpenedEvidence.getEvidenceID()));
        evidenceManufacturerTextField.setText(currentOpenedEvidence.getEvidenceManufacturer());
        evidenceTypeTextField.setText(currentOpenedEvidence.getEvidenceType());
        modelTextField.setText(currentOpenedEvidence.getEvidenceModel());
        
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH);
   
        
        // Get the Image
        byte[] imagebytes = currentOpenedEvidence.getEvidenceImage();
        if (imagebytes != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(imagebytes);
            BufferedImage buffimage = null;
            try {
                buffimage = ImageIO.read(bais);
            } catch (IOException ex) {
                Logger.getLogger(ViewEvidenceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            WritableImage writeableimage = new WritableImage(500, 300);
            Image image = SwingFXUtils.toFXImage(buffimage, writeableimage);
            evidenceImageViewer.setImage(image);
        }
        

        
        
        manufacturedDateTextField.setText(currentOpenedEvidence.getEvidenceManufacturedDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

     
        DateTimeFormatter datetimeformat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        
        evidenceNoteTextArea.clear();
        
        Query query = session.createQuery("from EvidenceNote where evidence = " + ViewEvidenceListController.getCurrentOpenedEvidenceID());
        
        List<EvidenceNote> notesForEvidence = (List<EvidenceNote>) query.list();

        
        notesForEvidence.stream().map((notesForEvidence1) -> {
            evidenceNoteTextArea.appendText(notesForEvidence1.getDateTimeAdded().format(datetimeformat) + "\n");
            return notesForEvidence1;
        }).forEach((notesForEvidence1) -> {
            evidenceNoteTextArea.appendText(notesForEvidence1.getNote() + "\n\n");
        });
        
        session.close();
    }
    
}
