package com.hellofresh.modals;

public class CreatedBookingModal
{
    private int bookingid;

    private BookingModal booking;

    public void setBookingid(int bookingid){
        this.bookingid = bookingid;
    }
    public int getBookingid(){
        return this.bookingid;
    }
    public void setBooking(BookingModal booking){
        this.booking = booking;
    }
    public BookingModal getBooking(){
        return this.booking;
    }
}

