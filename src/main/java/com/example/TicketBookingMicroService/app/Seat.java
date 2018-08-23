package com.example.TicketBookingMicroService.app;

import java.util.Date;

public class Seat {
    private Status status;

    public Date getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(Date blockedTime) {
        this.blockedTime = blockedTime;
    }

    private Date blockedTime;
    Seat (){
        status = Status.FREE;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
