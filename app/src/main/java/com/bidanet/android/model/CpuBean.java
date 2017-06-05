package com.bidanet.android.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xuejike on 2017/3/28.
 */
public class CpuBean {
    private String hotelName;
    private Float cpu;
    private Date time;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Float getCpu() {
        return cpu;
    }

    public void setCpu(Float cpu) {
        this.cpu = cpu;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
