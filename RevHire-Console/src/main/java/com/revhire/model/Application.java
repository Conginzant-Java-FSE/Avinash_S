package com.revhire.model;

import java.sql.Timestamp;

public class Application {
    private int id;
    private int jobId;
    private int seekerId;
    private String resumeSnapshot;
    private String status;
    private String withdrawReason;
    private Timestamp appliedAt;

    public Application() {
    }

    public Application(int id, int jobId, int seekerId, String resumeSnapshot, String status, String withdrawReason,
            Timestamp appliedAt) {
        this.id = id;
        this.jobId = jobId;
        this.seekerId = seekerId;
        this.resumeSnapshot = resumeSnapshot;
        this.status = status;
        this.withdrawReason = withdrawReason;
        this.appliedAt = appliedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(int seekerId) {
        this.seekerId = seekerId;
    }

    public String getResumeSnapshot() {
        return resumeSnapshot;
    }

    public void setResumeSnapshot(String resumeSnapshot) {
        this.resumeSnapshot = resumeSnapshot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWithdrawReason() {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason) {
        this.withdrawReason = withdrawReason;
    }

    public Timestamp getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Timestamp appliedAt) {
        this.appliedAt = appliedAt;
    }

    @Override
    public String toString() {
        return "Application{id=" + id + ", jobId=" + jobId + ", status='" + status + "'}";
    }
}
