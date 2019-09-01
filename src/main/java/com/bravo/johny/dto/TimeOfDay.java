package com.bravo.johny.dto;

public enum TimeOfDay {

    MORNING (1),
    EVENING (2);

    private int timeOfDayInt;

    TimeOfDay(int v) {
        timeOfDayInt = v;
    }

    public int getTimeOfDay() {
        return timeOfDayInt;
    }
}
