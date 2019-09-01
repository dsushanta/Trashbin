package com.bravo.johny.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GarbageCollection {

    private int collectionId;
    private String helperId;
    private int requestId;
    private String comment;
    private Date garbageCollectionDate;
    private TimeOfDay timeOfDay;
    private Date gcCollectedDate;
    private TimeOfDay gcCollectedTimeOfDay;
    private List<Link> links = new ArrayList<>();

    public GarbageCollection() {
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getHelperId() {
        return helperId;
    }

    public void setHelperId(String helperId) {
        this.helperId = helperId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Date getGcCollectedDate() {
        return gcCollectedDate;
    }

    public void setGcCollectedDate(Date gcCollectedDate) {
        this.gcCollectedDate = gcCollectedDate;
    }

    public TimeOfDay getGcCollectedTimeOfDay() {
        return gcCollectedTimeOfDay;
    }

    public void setGcCollectedTimeOfDay(TimeOfDay gcCollectedTimeOfDay) {
        this.gcCollectedTimeOfDay = gcCollectedTimeOfDay;
    }

    public void addLink(String url, String rel) {
        Link l = new Link();
        l.setLink(url);
        l.setRel(rel);
        links.add(l);
    }
}
