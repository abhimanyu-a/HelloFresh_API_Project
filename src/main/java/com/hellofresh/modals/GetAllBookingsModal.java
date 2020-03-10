package com.hellofresh.modals;

import java.util.List;

public class GetAllBookingsModal
{
    private List<BookingModal> bookings;

    public void setBookings(List<BookingModal> bookings){
        this.bookings = bookings;
    }
    public List<BookingModal> getBookings(){
        return this.bookings;
    }
}
