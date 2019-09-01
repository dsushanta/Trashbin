package com.bravo.johny.webresource.filterbeans;

import javax.ws.rs.QueryParam;

public class TrashcallRequestFilterBean {

//    private @QueryParam("requestorId") String requestorId;
    private @QueryParam("requeststatus") int requeststatus;
    /*private @QueryParam("garbagecollectiondate") String garbagecollectiondate;
    private @QueryParam("timeofday") String timeofday;*/
    private @QueryParam("offset") int offset;
    private @QueryParam("limit") int limit;

    /*public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }*/

    public int getRequeststatus() {
        return requeststatus;
    }

    public void setRequeststatus(int requeststatus) {
        this.requeststatus = requeststatus;
    }

    /*public String getGarbagecollectiondate() {
        return garbagecollectiondate;
    }

    public void setGarbagecollectiondate(String garbagecollectiondate) {
        this.garbagecollectiondate = garbagecollectiondate;
    }

    public String getTimeofday() {
        return timeofday;
    }

    public void setTimeofday(String timeofday) {
        this.timeofday = timeofday;
    }*/

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
