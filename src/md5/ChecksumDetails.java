/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5;

import createcase.CaseFile;
import java.time.LocalDateTime;
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
@Table(name = "CASE_CHECKSUMS")
public class ChecksumDetails {

    @Id
    @GeneratedValue
    @Column(name = "CHECKSUM_ID")
    private int checksumID;
    private String fileName;
    private String filePath;
    private String MD5Value;
    private String SHA1Value;
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseFile caseFile;
    private LocalDateTime dateTimeGenerated;

    public int getChecksumID() {
        return checksumID;
    }

    public void setChecksumID(int checksumID) {
        this.checksumID = checksumID;
    }

    public String getSHA1Value() {
        return SHA1Value;
    }

    public void setSHA1Value(String SHA1Value) {
        this.SHA1Value = SHA1Value;
    }

    public LocalDateTime getDateTimeGenerated() {
        return dateTimeGenerated;
    }

    public void setDateTimeGenerated(LocalDateTime dateTimeGenerated) {
        this.dateTimeGenerated = dateTimeGenerated;
    }

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
        return checksumID;
    }

    public void setMD5ID(int MD5ID) {
        this.checksumID = MD5ID;
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
