/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidence;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;

/**
 *
 * @author Lewis
 */
@Entity
@Table (name = "EVIDENCE_NOTES")
public class EvidenceNote {
    
    @Id @GeneratedValue @Column (name = "EVIDENCE_NOTE_ID")
    private long EvidenceNoteID;
    @ManyToOne
    @JoinColumn(name="EVIDENCE_ID")
    private Evidence evidence;
    private String note;
   
    private LocalDateTime dateTimeAdded;

    public LocalDateTime getDateTimeAdded() {
        return dateTimeAdded;
    }

    public void setDateTimeAdded(LocalDateTime dateTimeAdded) {
        this.dateTimeAdded = dateTimeAdded;
    }
    
    public long getEvidenceNoteID() {
        return EvidenceNoteID;
    }

    public void setEvidenceNoteID(long EvidenceNoteID) {
        this.EvidenceNoteID = EvidenceNoteID;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

   
    
    
    
    
}
