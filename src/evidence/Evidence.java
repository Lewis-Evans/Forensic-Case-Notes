/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidence;

import createcase.CaseFile;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.image.Image;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Lewis
 */
@Entity
@Table(name = "EVIDENCE")
public class Evidence implements Serializable {

    @Id
    @Column(name = "EVIDENCE_ID")
    private long evidenceID;
    @Column(name = "EVIDENCE_TYPE")
    private String evidenceType;
    @Column(name = "EVIDENCE_MANUFACTURER")
    private String evidenceManufacturer;
    @Column(name = "EVIDENCE_MANUFACTURED_DATE")
    private LocalDate evidenceManufacturedDate;
    @Column(name = "EVIDENCE_MODEL")
    private String evidenceModel;
    @Column(name = "EVIDENCE_NOTES")
    @OneToMany(mappedBy = "evidence", cascade = CascadeType.ALL)
    private Collection<EvidenceNote> evidenceNotes = new ArrayList<>();
    @Column(name = "EVIDENCE_IMAGE")
    @Lob
    private byte[] evidenceImage;
    @Column(name = "EVIDENCE_DATE_TIME_ADDED")
    private LocalDateTime evidenceDateTimeAdded;
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseFile caseFile;

   

    public LocalDateTime getEvidenceDateTimeAdded() {
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        evidenceDateTimeAdded.format(format);
        return evidenceDateTimeAdded;
    }

    public void setEvidenceDateTimeAdded(LocalDateTime evidenceDateTimeAdded) {
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        evidenceDateTimeAdded.format(format);
        this.evidenceDateTimeAdded = evidenceDateTimeAdded;
    }

    public Collection<EvidenceNote> getEvidenceNotes() {
        return evidenceNotes;
    }

    public void setEvidenceNotes(Collection<EvidenceNote> evidenceNotes) {
        this.evidenceNotes = evidenceNotes;
    }

    public byte[] getEvidenceImage() {
        return evidenceImage;
    }

    public void setEvidenceImage(byte[] evidenceImage) {
        this.evidenceImage = evidenceImage;
    }

    public Image evidenceTooltipFactory() {

        return null;
    }

    public CaseFile getCaseFile() {

        return caseFile;
    }

    public void setCaseFile(CaseFile caseFile) {
        this.caseFile = caseFile;
    }

    public long getEvidenceID() {
        return evidenceID;
    }

    public void setEvidenceID(long evidenceID) {
        this.evidenceID = evidenceID;
    }

    public String getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    public String getEvidenceManufacturer() {
        return evidenceManufacturer;
    }

    public void setEvidenceManufacturer(String evidenceManufacturer) {
        this.evidenceManufacturer = evidenceManufacturer;
    }

    public LocalDate getEvidenceManufacturedDate() {
        return evidenceManufacturedDate;
    }

    public void setEvidenceManufacturedDate(LocalDate evidenceManufacturedDate) {
        this.evidenceManufacturedDate = evidenceManufacturedDate;
    }

    public String getEvidenceModel() {
        return evidenceModel;
    }

    public void setEvidenceModel(String evidenceModel) {
        this.evidenceModel = evidenceModel;
    }

}
