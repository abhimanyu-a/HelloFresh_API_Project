package com.hellofresh.modals;

public class BookingModal
{
    private int bookingid;

    private int roomid;

    private String firstname;

    private String lastname;
    
    private String email;
    
    private String phone;

    private boolean depositpaid;

    private BookingDatesModal bookingdates;

    public void setBookingid(int bookingid){
        this.bookingid = bookingid;
    }
    public int getBookingid(){
        return this.bookingid;
    }
    public void setRoomid(int roomid){
        this.roomid = roomid;
    }
    public int getRoomid(){
        return this.roomid;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public String getLastname(){
        return this.lastname;
    }
    public void setDepositpaid(boolean depositpaid){
        this.depositpaid = depositpaid;
    }
    public boolean getDepositpaid(){
        return this.depositpaid;
    }
    public void setBookingdates(BookingDatesModal bookingdates){
        this.bookingdates = bookingdates;
    }
    public BookingDatesModal getBookingdates(){
        return this.bookingdates;
    }
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
