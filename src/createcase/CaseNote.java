package createcase;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Lewis
 */
@Entity
@Table(name = "CASE_NOTES")
public class CaseNote {

    @Id
    @GeneratedValue
    @Column(name = "CASE_NOTE_ID")
    private int caseNoteID;
    @Column(name = "CASE_NOTES", length = 1000)
    @Lob
    private String notes;
    private LocalDateTime dateTimeAdded;
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseFile caseFile;

    public LocalDateTime getDateTimeAdded() {
        return dateTimeAdded;
    }

    public void setDateTimeAdded(LocalDateTime dateTimeAdded) {
        this.dateTimeAdded = dateTimeAdded;
    }

    public CaseFile getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(CaseFile caseFile) {
        this.caseFile = caseFile;
    }

    public int getCaseNoteID() {
        return caseNoteID;
    }

    public void setCaseNoteID(int caseNoteID) {
        this.caseNoteID = caseNoteID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
