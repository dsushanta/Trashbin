package com.bravo.johny.dto;

public enum TrashcallStatus {

    COLLECTED (2),
    NOT_COLLECTED (1);

    private int statusInt;

    TrashcallStatus(int v) {
        statusInt = v;
    }

    public int getTrashcallStatus() {
        return statusInt;
    }
}