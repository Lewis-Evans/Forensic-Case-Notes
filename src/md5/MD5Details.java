/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5;

import createcase.CaseFile;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Lewis
 */
@Entity
@Table (name = "CASE_MD5S")
public class MD5Details {
    
    @Id
    @GeneratedValue
    @Column (name="MD5_ID")
    private int MD5ID;
    private String fileName;
    private String filePath;
    private String MD5Value;
    @ManyToOne
    @JoinColumn(name="CASE_ID")
    private CaseFile caseFile;

    public CaseFile getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(CaseFile caseFile) {
        this.caseFile = caseFile;
    }


    public String getMD5Value() {
        return MD5Value;
    }

    public void setMD5Value(String MD5Value) {
        this.MD5Value = MD5Value;
    }

    public int getMD5ID() {
        return MD5ID;
    }

    public void setMD5ID(int MD5ID) {
        this.MD5ID = MD5ID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    

}
