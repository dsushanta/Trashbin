package com.bravo.johny.dto;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TrashcallRequest {

    private int requestId;
    private String requestorId;
    private TrashcallStatus requestStatus;
    private String comments;
    private Date requestDate;
    private Date garbageCollectionDate;
    private TimeOfDay timeOfDay;
    private List<Link> links = new ArrayList<>();

    public TrashcallRequest() {
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public TrashcallStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(TrashcallStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date date) {
        this.requestDate = date;
    }

    public Date getGarbageCollectionDate() {
        return garbageCollectionDate;
    }

    public void setGarbageCollectionDate(Date garbageCollectionDate) {
        this.garbageCollectionDate = garbageCollectionDate;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void addLink(String url, String rel) {
        Link l = new Link();
        l.setLink(url);
        l.setRel(rel);
        links.add(l);
    }

    public List<Link> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return "TrashcallRequest{" +
                "requestId=" + requestId +
                ", requestorId='" + requestorId + '\'' +
                ", requestStatus=" + requestStatus +
                ", comments='" + comments + '\'' +
                ", requestDate=" + requestDate +
                ", garbageCollectionDate=" + garbageCollectionDate +
                ", timeOfDay=" + timeOfDay +
                ", links=" + links +
                '}';
    }
}
