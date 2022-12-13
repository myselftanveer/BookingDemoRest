package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintStream;

import org.testng.annotations.Test;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.BookingData;
import pojo.LoginData;
import resources.Specification;
import resources.Utils;

public class BookingTest {
	LoginData data = new LoginData();
	BookingData booking = new BookingData();
	String Token;
	static int BookingId;

	@Test(priority = 1)
	public void createToken() throws IOException {
//		LoginPOJO login = new LoginPOJO();
//		login.setUsername("admin");
//		login.setPassword("password123");

//		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
//
//		System.out.println("*************************************************************************Create token for future use");
//		RequestSpecification create = given().log().all().header("Content-Type", "application/json").body(login);
//		String token = create.when().post("/auth").then().log().all().header("server", equalTo("Cowboy"))
//				.header("Content-Type", equalTo("application/json; charset=utf-8")).assertThat().statusCode(200).extract().response().asString();
//
//		Response res = create.when().post("/auth");
//		System.out.println("res.getStatusCode()========" + res.getStatusCode());
//		System.out.println("res.getStatusLine()========" + res.getStatusLine());
//		System.out.println("res.getStatusLineID()========" + res.getHeaders().getValue("server"));
//		System.out.println("res.getStatusLineID()========" + res.getHeaders().getValue("Content-Type"));
//
//		System.out.println(res.getTime());
//
//		assertEquals(res.getStatusCode(), 200);
//		assertEquals(res.getStatusLine(), "HTTP/1.1 200 OK");
//		assertEquals("Cowboy", res.getHeaders().getValue("server"));
//
//		System.out.println("Token===========" + token);
//
//		JsonPath js = new JsonPath(token);
//		Token = js.get("token");
//		System.out.println(Token);
		
		PrintStream login = Utils.printLog("./reports/createToken.txt");
		
		System.out.println("*************************************************************************Create token for future use");
		String payload = Utils.StringtoJSON(data.loginData());
		RequestSpecification create = given().log().all().spec(Specification.request()).body(payload)
				.filter(RequestLoggingFilter.logRequestTo(login))
				.filter(ResponseLoggingFilter.logResponseTo(login));

		String token = create.when().post("/auth").then().log().all().header("server", equalTo("Cowboy"))
				.header("Content-Type", equalTo("application/json; charset=utf-8")).spec(Specification.response())
				.extract().response().asString();
		Response res = create.when().post("/auth");

		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getStatusLine(), "HTTP/1.1 200 OK");
		assertEquals("Cowboy", res.getHeaders().getValue("server"));

		JsonPath js = Utils.convertRawToJson(token);
		Token = js.get("token");
		System.out.println("Token===========" + token);
	}

	@Test(priority = 2)
	public void createBooking() throws IOException {

		PrintStream addBooking = Utils.printLog("./reports/createBooking.txt");
		System.out.println("**************************************************************************************************Create Booking");
		RequestSpecification create = given().log().all().spec(Specification.request()).body(booking.bookPayload())
				.filter(RequestLoggingFilter.logRequestTo(addBooking))
				.filter(ResponseLoggingFilter.logResponseTo(addBooking));
		String createBooking = create.when().post("/booking").then().log().all().assertThat().statusCode(200)
				.body("booking.firstname", equalTo("POJO")).extract().response().asString();
		System.out.println("CREATE==========="+createBooking);
		
		

		JsonPath js = Utils.convertRawToJson(createBooking);
		BookingId = js.get("bookingid");
		System.out.println(BookingId);
		
		Response createBook = create.when().post("/booking");
//		System.out.println("Status Code========" + createBook.getStatusCode());
//		System.out.println("Status Line========" + createBook.getStatusLine());
//		System.out.println("Server========" + createBook.getHeaders().getValue("server"));
//		System.out.println("Content Type========" + createBook.getHeaders().getValue("Content-Type"));
//		System.out.println("Response Time========" + createBook.getTime());
		
		assertEquals(createBook.getStatusCode(), 200);
		assertEquals(createBook.getStatusLine(), "HTTP/1.1 200 OK");
		assertEquals("Cowboy", createBook.getHeaders().getValue("server"));
	}

	@Test(priority = 3)
	public void getCreatedBooking() throws IOException {

		PrintStream getCreatedBooking = Utils.printLog("./reports/getBooking.txt");
		System.out.println("**********************************************************************************************Get Created Booking");
		RequestSpecification get = given().log().all().spec(Specification.request())
				.filter(RequestLoggingFilter.logRequestTo(getCreatedBooking))
				.filter(ResponseLoggingFilter.logResponseTo(getCreatedBooking));
		String getBooking=get.when().get("booking/" + BookingId + "")
				.then().log().all().assertThat().statusCode(200).header("server", equalTo("Cowboy"))
				.header("Content-Type", equalTo("application/json; charset=utf-8")).body("firstname", equalTo("POJO")).extract().response().asString();
		System.out.println("GET Booking==========="+getBooking);
	}

	@Test(priority = 4)
	public void deleteCreatedBooking() throws IOException {

		PrintStream deleteCreatedBooking = Utils.printLog("./reports/deleteBooking.txt");
		System.out.println("**********************************************************************************************Delete Created Booking");

		RequestSpecification delete = given().log().all().spec(Specification.request()).header("Cookie", "token=" +Token+"")
				.filter(RequestLoggingFilter.logRequestTo(deleteCreatedBooking))
				.filter(ResponseLoggingFilter.logResponseTo(deleteCreatedBooking));
		String deleteBooking=delete.when().delete("booking/" + BookingId + "").then().log().all()
				.extract().response().asString();

		System.out.println("DELETE Booking==========="+deleteBooking);

	}

}
