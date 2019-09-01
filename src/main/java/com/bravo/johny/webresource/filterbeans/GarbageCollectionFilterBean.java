package com.bravo.johny.webresource.filterbeans;

import javax.ws.rs.QueryParam;

public class GarbageCollectionFilterBean {

    private @QueryParam("helperId") String helperId;
    /*private @QueryParam("requestId") int requestId;
    private @QueryParam("garbagecollectiondate") String garbagecollectiondate;
    private @QueryParam("timeofday") String timeofday;*/
    private @QueryParam("offset") int offset;
    private @QueryParam("limit") int limit;

    public String getHelperId() {
        return helperId;
    }

    public void setHelperId(String helperId) {
        this.helperId = helperId;
    }

    /*public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getGarbagecollectiondate() {
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
