package com.bravo.johny.webresource.filterbeans;

import javax.ws.rs.QueryParam;

public class UserFilterBean {

    private @QueryParam("apartmentNo") String apartmentNo;
    private @QueryParam("offset") int offset;
    private @QueryParam("limit") int limit;

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

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
