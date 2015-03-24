/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import createcase.CaseFile;
import java.time.LocalDate;
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
@Table (name = "CASE_TASKS")
public class Task {
    
    @Id @GeneratedValue @Column (name = "TASK_ID")
    private long taskID;
    @Column (name = "TASK_NAME")
    private String taskName;
    @Column (name = "TASK_DESCRIPTION")
    private String taskDescription;
    @Column (name = "TASK_START_DATE")
    private LocalDate startDate;
    @Column (name = "TASK_END_DATE")
    private LocalDate endDate;
    @Column (name = "TASK_PRIORITY")
    private int taskPriority;
    @Column (name = "TASK_STATUS")
    private String taskStatus;

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseFile caseFile;
    

    public CaseFile getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(CaseFile caseFile) {
        this.caseFile = caseFile;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPriority() {
        return taskPriority;
    }

    public void setPriority(int priority) {
        this.taskPriority = priority;
    }
    
    
    
}
