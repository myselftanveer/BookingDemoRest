package resources;

import java.io.IOException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {

	public static RequestSpecification req;
	public static ResponseSpecification res;

//	public static PrintStream printLog() throws FileNotFoundException {
//		PrintStream ps = new PrintStream(new FileOutputStream("./reports/allLogs"));
//		return ps;
//	}

	public static RequestSpecification request() throws IOException {

		if (req == null) {
			req = new RequestSpecBuilder().setBaseUri(Utils.getConfig("baseURI"))
					.setContentType(ContentType.JSON).build();
//					.addFilter(RequestLoggingFilter.logRequestTo(Specification.printLog())).build();
			return req;
		}
		return req;

	}

	public static ResponseSpecification response() {

		res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		return res;
	}

}
