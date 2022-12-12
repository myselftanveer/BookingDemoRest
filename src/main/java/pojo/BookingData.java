package pojo;

public class BookingData {
	
	public CreateBookingPOJO bookPayload() {
		
		CreateBookingPOJO book = new CreateBookingPOJO();
		book.setFirstname("POJO");
		book.setLastname("Test");
		book.setTotalprice(1234);
		book.setDepositpaid(true);
		
		BookingDates date = new BookingDates();
		date.setCheckin("2022-01-01");
		date.setCheckout("2022-01-01");
		
		book.setBookingdates(date);
		
		book.setAdditionalneeds("Lunch");
		return book;
		
	}
	

}
