package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class SimpleBooking {

	public static void main(String[] args) {

		String Token;
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		System.out.println("*************************************************************************Create token for future use");
		RequestSpecification create = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n"
						+ "    \"username\" : \"admin\",\r\n"
						+ "    \"password\" : \"password123\"\r\n"
						+ "}");
				String token = create.when().post("/auth").then().log().all().header("server",equalTo("Cowboy"))
						
						.assertThat().statusCode(200)
				.extract().response().asString();
		
				Response res=create.when().post("/auth");
				System.out.println("res.getStatusCode()========"+res.getStatusCode());
				System.out.println("res.getStatusLine()========"+res.getStatusLine());
				System.out.println("res.getStatusLineID()========"+res.getHeaders().getValue("server"));
				System.out.println("res.getStatusLineID()========"+res.getHeaders().getValue("Content-Type"));
		
				System.out.println(res.getTime());
				
				assertEquals(res.getStatusCode(), 200);
				assertEquals(res.getStatusLine(), "HTTP/1.1 200 OK");
				assertEquals("Cowboy",res.getHeaders().getValue("server"));

		System.out.println("Token===========" +token);
		
		JsonPath js = new JsonPath(token);
		Token = js.get("token");
		System.out.println(Token);
//		
		System.out.println("**************************************************************************************************Create Booking");
		String createBooking = given().log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"firstname\": \"XZ\",\r\n"
				+ "    \"lastname\": \"ZY\",\r\n"
				+ "    \"totalprice\": 100,\r\n"
				+ "    \"depositpaid\": true,\r\n"
				+ "    \"bookingdates\": {\r\n"
				+ "        \"checkin\": \"2022-01-01\",\r\n"
				+ "        \"checkout\": \"2022-02-01\"\r\n"
				+ "    },\r\n"
				+ "    \"additionalneeds\": \"Dinner\"\r\n"
				+ "}").
		when().post("/booking").then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println(createBooking+"createBookingcreateBookingcreateBookingcreateBooking");
		JsonPath js1 = new JsonPath(createBooking);
		int BookingId = js1.get("bookingid");
		System.out.println(BookingId);
		
		System.out.println("**********************************************************************************************Get Created Booking");
		String getBooking = given().log().all().header("Content-Type","application/json")
		.when().get("booking/"+BookingId+"").then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println("Get Booking"+getBooking);
		
		System.out.println("**********************************************************************************************Delete Created Booking");
		
		String deleteBooking = given().log().all().header("Content-Type","application/json").header("Cookie","token "+Token+"")
		.when().delete("booking/"+BookingId+"").then().log().all().extract().response().asString();
		
		System.out.println("deleteBookingdeleteBookingdeleteBookingdeleteBooking"+deleteBooking);
		

	}

}
