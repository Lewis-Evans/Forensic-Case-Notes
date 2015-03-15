/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5;

import createcase.CaseFile;
import createcase.CreateCaseController;
import hibernate.HibernateUtilities;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
import java.awt.datatransfer.Clipboard;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Lewis
 */
public class MD5FileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    Button browseForFileButton;
    @FXML
    Label md5ClipboardLabel;
    @FXML
    TextField filePathTextField, md5ResultTextField;

    @FXML
    protected void locateFile(ActionEvent event) {
        FileInputStream fis = null;
        FileChooser chooser = new FileChooser();
        chooser.getInitialDirectory();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        filePathTextField.setText(file.getAbsolutePath());
        generateMD5(file);
    }

    private void generateMD5(File file) {
        
        FileInputStream fis;
        String md5 = "";
        try {
            fis = new FileInputStream(file);
            md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
            md5ResultTextField.setText(md5);
            fis.close();

            md5ClipboardLabel.setText("MD5 Checksum copied to Clipboard!");

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();

            clipboard.setContents(new StringSelection(md5), null);
        } catch (IOException ex) {
            Logger.getLogger(MD5FileController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MD5Details aMD5 = new MD5Details();
        aMD5.setMD5Value(md5);
        aMD5.setFileName(file.getName());
        aMD5.setFilePath(file.getAbsolutePath());

        SessionFactory sFactory = HibernateUtilities.getSessionFactory();
        Session session = sFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(aMD5);

        CaseFile currentCase = (CaseFile) session.get(CaseFile.class, CreateCaseController.getCaseNumber());

        currentCase.getMd5Details().add(aMD5);
        aMD5.setCaseFile(currentCase);
            
        
        
        
        session.getTransaction().commit();
        session.close();
        
        
        String ext = FilenameUtils.getExtension(file.getName());
    
        System.out.println("Extension: " + ext);
        
        System.out.println(aMD5.getMD5Value());
        System.out.println(aMD5.getFileName());
        System.out.println(aMD5.getFilePath());

    }
}
