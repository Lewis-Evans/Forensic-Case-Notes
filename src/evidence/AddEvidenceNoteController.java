/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidence;

import createcase.CaseNote;
import createcase.CreateCaseController;
import hibernate.HibernateUtilities;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class AddEvidenceNoteController implements Initializable {

    @FXML
    private Button addEvidenceNoteButton;

    @FXML
    private TextArea evidenceNoteTextArea;

    @FXML
    void handleAddNoteButton(ActionEvent event) {
        System.out.println("Clicked on Add Note Button");

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session theSession = sFactory.openSession();
        theSession.beginTransaction();

        // Create a new Case Note
        EvidenceNote evidenceNote = new EvidenceNote();

        evidenceNote.setDateTimeAdded(LocalDateTime.now());
        evidenceNote.setNote(evidenceNoteTextArea.getText());
        // Add the note to the case

       
        evidenceNote.setEvidence(ViewEvidenceListController.getCurrentOpenedEvidence());

        
        theSession.saveOrUpdate(evidenceNote);

        theSession.getTransaction().commit();
        
        
        
        
        theSession.close();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
