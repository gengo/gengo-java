package com.gengo.client.enums;

/**
 * Job status used in GET method.
 * Key words not to be supported in filter are commented out.
 */
public enum JobStatus {
    //QUEUED("queued"),
    AVAILABLE("available"),
    PENDING("pending"),
    REVIEWABLE("reviewable"),
    APPROVED("approved"),
    //REVISING("revising"),
    REJECTED("rejected"),
    CANCELED("canceled");
    //,HOLD("hold")

    private String status;

    private JobStatus(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return this.status;
    }
}
