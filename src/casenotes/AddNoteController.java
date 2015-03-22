/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casenotes;

import casesummary.CaseSummaryController;
import createcase.CaseFile;
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
public class AddNoteController implements Initializable {

    @FXML private Button addNoteButton;
    @FXML private TextArea caseNoteTextArea;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }    
    
    @FXML 
    void handleAddNoteButton(ActionEvent event){
        // Open a session to interact with database
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session theSession = sFactory.openSession();
        theSession.beginTransaction();

        // Create a new Case Note
        CaseNote newNote = new CaseNote();
        
        // Set the CaseNote's DateTimeCreated to the current Date/Time
        newNote.setDateTimeAdded(LocalDateTime.now());
        // Get the text in the text area and add it to the CaseNote object
        newNote.setNotes(caseNoteTextArea.getText());

        // Add the note to the case
        newNote.setCaseFile(CreateCaseController.getNewCase());
      
        theSession.saveOrUpdate(newNote);
        theSession.getTransaction().commit();
        theSession.close();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
