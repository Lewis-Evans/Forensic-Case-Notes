/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import casesummary.CaseSummaryController;
import createcase.CreateCaseController;
import evidence.Evidence;
import hibernate.HibernateUtilities;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class ViewTasksListController implements Initializable {

    @FXML
    private TableColumn<Task, LocalDate> columnTaskDueDate;

    @FXML
    private TextField taskIDTextField;

    @FXML
    private Button markCompleteButton;

    @FXML
    private Button deleteTaskButton;

    @FXML
    private TableColumn<Task, Integer> columnTaskID;

    @FXML
    private TableColumn<Task, Integer> columnPriority;

    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> columnTaskName;

    @FXML
    private Button addTaskButton;

    @FXML
    private TableColumn<Task, LocalDate> columnTaskStartDate;
    @FXML
    private TableColumn<Task, String> columnDescription;
    @FXML
    private TableColumn<Task, String> columnTaskStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnTaskID.setCellValueFactory(new PropertyValueFactory("taskID"));
        columnTaskName.setCellValueFactory(new PropertyValueFactory("taskName"));
        columnTaskStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        columnTaskStartDate.setCellFactory(column -> {
            return new TableCell<Task, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(format.format(item));

                        // Style all dates in March with a different color.
                    }
                }
            };
        });
        columnTaskDueDate.setCellValueFactory(new PropertyValueFactory("endDate"));
        columnTaskDueDate.setCellFactory(column -> {
            return new TableCell<Task, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(format.format(item));

                        // Style all dates in March with a different color.
                    }
                }
            };
        });
        columnTaskStatus.setCellValueFactory(new PropertyValueFactory("taskStatus"));
        //columnPriority.setCellValueFactory(new PropertyValueFactory("taskPriority"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<Task, Integer>("taskPriority"));
        columnDescription.setCellValueFactory(new PropertyValueFactory("taskDescription"));

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Task where caseFile = " + CreateCaseController.getCaseNumber());
        List<Task> tasksForCase = (List<Task>) query.list();

        taskTableView.getItems().addAll(tasksForCase);

        session.getTransaction().commit();
        session.close();

    }

    @FXML
    void handleAddTaskButton(ActionEvent event) {
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
    void handleRefreshButton(ActionEvent event) {
        taskTableView.getItems().clear();
        columnTaskID.setCellValueFactory(new PropertyValueFactory("taskID"));
        columnTaskName.setCellValueFactory(new PropertyValueFactory("taskName"));
        columnTaskStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        columnTaskStartDate.setCellFactory(column -> {
            return new TableCell<Task, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(format.format(item));

                        // Style all dates in March with a different color.
                    }
                }
            };
        });
        columnTaskDueDate.setCellValueFactory(new PropertyValueFactory("endDate"));
        columnTaskDueDate.setCellFactory(column -> {
            return new TableCell<Task, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(format.format(item));

                        // Style all dates in March with a different color.
                    }
                }
            };
        });
        columnTaskStatus.setCellValueFactory(new PropertyValueFactory("taskStatus"));
        //columnPriority.setCellValueFactory(new PropertyValueFactory("taskPriority"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<Task, Integer>("taskPriority"));
        columnDescription.setCellValueFactory(new PropertyValueFactory("taskDescription"));

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Task where caseFile = " + CreateCaseController.getCaseNumber());
        List<Task> tasksForCase = (List<Task>) query.list();

        taskTableView.getItems().addAll(tasksForCase);

        session.getTransaction().commit();
        session.close();

    }

    @FXML
    void handleMarkCompleteButton(ActionEvent event) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        Task currentTask = (Task) session.get(Task.class, Long.parseLong(taskIDTextField.getText()));

        currentTask.setTaskStatus("Completed");

        session.getTransaction().commit();
        session.close();

        handleRefreshButton(event);
    }

    @FXML
    void handleDeleteTaskButton(ActionEvent event) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        session.delete(session.get(Task.class, Long.parseLong(taskIDTextField.getText())));

        session.getTransaction().commit();
        session.close();
    }

}
