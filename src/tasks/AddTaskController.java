/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import createcase.CaseFile;
import createcase.CreateCaseController;
import hibernate.HibernateUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class AddTaskController implements Initializable {

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField taskNameTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ChoiceBox<String> taskPrioChoiceBox;

    @FXML
    private Button createTaskButton;

    @FXML
    private TextArea taskDescTextArea;

    @FXML
    void handleCreateTaskButton(ActionEvent event) {
        System.out.println("Clicked on Create Task Button");
        Task newTask = new Task();

        newTask.setTaskName(taskNameTextField.getText());
        newTask.setTaskDescription(taskDescTextArea.getText());
        newTask.setStartDate(startDatePicker.getValue());
        newTask.setEndDate(endDatePicker.getValue());

        if (taskPrioChoiceBox.getValue().equals("1 - High")) {
            newTask.setPriority(1);
        } else if (taskPrioChoiceBox.getValue().equals("2 - Medium")) {
            newTask.setPriority(2);
        } else if (taskPrioChoiceBox.getValue().equals("3 - Low")) {
            newTask.setPriority(3);
        }

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        CaseFile currentCase = (CaseFile) session.get(CaseFile.class, CreateCaseController.getCaseNumber());
        currentCase.getCaseTasks().add(newTask);
        newTask.setCaseFile(currentCase);

        session.saveOrUpdate(currentCase);
        session.saveOrUpdate(newTask);

        session.getTransaction().commit();
        session.close();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        // save to Task table
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        taskPrioChoiceBox.getItems()
                .addAll(
                        "1 - High", "2 - Medium", "3 - Low"
                );
    }

}
