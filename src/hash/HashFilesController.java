/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

import createcase.CaseFile;
import createcase.CreateCaseController;
import hibernate.HibernateUtilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class HashFilesController implements Initializable {

    @FXML
    private Button refreshButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Checksum> checksumTableView;

    @FXML
    private TableColumn<Checksum, String> md5Column;

    @FXML
    private TableColumn<Checksum, String> filePathColumn;

    @FXML
    private TableColumn<Checksum, Integer> checksumIDColumn;

    @FXML
    private TableColumn<Checksum, String> fileNameColumn;

    @FXML
    private TableColumn<Checksum, LocalDateTime> dateTimeGeneratedColumn;

    @FXML
    Button browseForFileButton;
    @FXML
    Label md5ClipboardLabel;
    @FXML
    TextField filePathTextField, md5ResultTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Checksum where caseFile = " + CreateCaseController.getCaseNumber());
        List<Checksum> checksumsForCase = (List<Checksum>) query.list();

        checksumIDColumn.setCellValueFactory(new PropertyValueFactory("checksumID"));
        fileNameColumn.setCellValueFactory(new PropertyValueFactory("fileName"));
        filePathColumn.setCellValueFactory(new PropertyValueFactory("filePath"));
        dateTimeGeneratedColumn.setCellValueFactory(new PropertyValueFactory("dateTimeGenerated"));

        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        dateTimeGeneratedColumn.setCellFactory(column -> {
            return new TableCell<Checksum, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(format.format(item));
                    }
                }
            };
        });

        md5Column.setCellValueFactory(new PropertyValueFactory("MD5Value"));
      

        checksumTableView.getItems().addAll(checksumsForCase);
        session.getTransaction().commit();
        session.close();

    }

    @FXML
    protected void locateFile(ActionEvent event) {
        // FileInputStream fis = null;
        FileChooser chooser = new FileChooser();
        chooser.getInitialDirectory();
        chooser.setTitle("Open File");
        List<File> list = chooser.showOpenMultipleDialog(new Stage());
        list.stream().forEach((list1) -> {
            generateChecksums(list1);
        });//        filePathTextField.setText(files.getAbsolutePath());
//        generateMD5(files);
    }

    @FXML
    protected void handleSearchButton(ActionEvent event) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        checksumTableView.getItems().clear();

        Query query = session.createQuery("FROM ChecksumDetails E WHERE E.id = " + searchField.getText());
        List<Checksum> checksumsForCase = (List<Checksum>) query.list();

        checksumTableView.getItems().addAll(checksumsForCase);

        session.getTransaction().commit();
        session.close();
    }

    private void generateChecksums(File file) {

        FileInputStream md5fis;
    
        String md5 = "";

        try {
            md5fis = new FileInputStream(file);

            md5 = DigestUtils.md5Hex(md5fis);

            md5fis.close();

        } catch (IOException ex) {
            Logger.getLogger(HashFilesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Checksum aChecksum = new Checksum();
        aChecksum.setMD5Value(md5);
        ;
        aChecksum.setFileName(file.getName());
        aChecksum.setFilePath(file.getPath());

        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        LocalDateTime currentDateTime = LocalDateTime.now();
        currentDateTime.format(format);

        aChecksum.setDateTimeGenerated(currentDateTime);

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(aChecksum);

        CaseFile currentCase = (CaseFile) session.get(CaseFile.class, CreateCaseController.getCaseNumber());

        currentCase.getMd5Details().add(aChecksum);
        aChecksum.setCaseFile(currentCase);

        session.getTransaction().commit();
        session.close();

        checksumTableView.getItems().add(aChecksum);

        System.out.println(aChecksum.getMD5Value());
        System.out.println(aChecksum.getFileName());
        System.out.println(aChecksum.getFilePath());

    }

    @FXML
    void handleRefreshButton(ActionEvent event) {
        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();

        checksumTableView.getItems().clear();

        Query query = session.createQuery("from ChecksumDetails where caseFile = " + CreateCaseController.getCaseNumber());
        List<Checksum> checksumsForCase = (List<Checksum>) query.list();

        checksumTableView.getItems().addAll(checksumsForCase);

        session.getTransaction().commit();
        session.close();
    }
}
