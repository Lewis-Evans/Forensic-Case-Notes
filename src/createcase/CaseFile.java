/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createcase;

import evidence.Evidence;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import md5.ChecksumDetails;
import tasks.Task;

/**
 *
 * @author Lewis
 */
@Entity
@Table(name = "CASE_FILES")
public class CaseFile implements Serializable {

    @Id
    @Column(name = "CASE_ID")
    private long caseID;
    private String caseType;
    private LocalDate caseDateOpened;
    private LocalDateTime dateTimeAdded;
    private String caseStatus;

    @OneToMany(mappedBy = "caseFile", cascade = CascadeType.ALL)
    private Collection<CaseNote> caseNotes = new ArrayList<>();

    @OneToMany(mappedBy = "caseFile", cascade = CascadeType.ALL)
    private Collection<ChecksumDetails> md5Details = new ArrayList<>();

    @OneToMany(mappedBy = "caseFile", cascade = CascadeType.ALL)
    private List<Evidence> caseEvidence = new ArrayList<>();

    @OneToMany(mappedBy = "caseFile", cascade = CascadeType.ALL)
    private Collection<Task> caseTasks = new ArrayList<>();

     public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public List<Evidence> getCaseEvidence() {
        return caseEvidence;
    }

    public Collection<Task> getCaseTasks() {
        return caseTasks;
    }

    public void setCaseTasks(Collection<Task> caseTasks) {
        this.caseTasks = caseTasks;
    }

    public void setCaseEvidence(List<Evidence> caseEvidence) {
        this.caseEvidence = caseEvidence;
    }

    public LocalDateTime getDateTimeAdded() {
         DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
         this.dateTimeAdded.format(format);
        return dateTimeAdded;
    }

    public void setDateTimeAdded(LocalDateTime dateTimeAdded) {
        this.dateTimeAdded = dateTimeAdded;
    }

    public Collection<ChecksumDetails> getMd5Details() {
        return md5Details;
    }

    public void setMd5Details(Collection<ChecksumDetails> md5Details) {
        this.md5Details = md5Details;
    }

    public CaseFile(int caseID, String caseType, LocalDate caseDateOpened) {
        this.caseID = caseID;
        this.caseType = caseType;
        this.caseDateOpened = caseDateOpened;
    }

    public CaseFile() {
        
    }

    public LocalDate getCaseDateOpened() {
        return caseDateOpened;
    }

    public void setCaseDateOpened(LocalDate caseDateOpened) {
        this.caseDateOpened = caseDateOpened;
    }

    public Collection<CaseNote> getCaseNotes() {
        return caseNotes;
    }

    public void setCaseNotes(Collection<CaseNote> caseNotes) {
        this.caseNotes = caseNotes;
    }

    public long getCaseID() {
        return caseID;
    }

    public void setCaseID(long caseID) {
        this.caseID = caseID;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public LocalDate getDateCaseOpened() {
        return caseDateOpened;
    }

    public void setDateCaseOpened(LocalDate dateCaseOpened) {
        this.caseDateOpened = dateCaseOpened;
    }

}
