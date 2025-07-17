import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serialization.Location;
import serialization.MapsBody;

public class SerializationMapsApi {

	@Test
	public void MapsApi() {

		MapsBody b = new MapsBody();

		b.setAccuracy(50);
		b.setName("AVK");
		b.setAddress("2nd Street, Gandhi Nagar");
		b.setPhone_number("+91 9876543210");
		b.setWebsite("https://rahulshettyacademy.com");
		b.setLanguage("French-IN");

		Location l = new Location();
		l.setLat(32.015478);
		l.setLng(-31.025872);
		b.setLocation(l);

		List<String> type = new ArrayList<String>();
		type.add("shoe park");
		type.add("shop");
		b.setTypes(type);

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().queryParam("key", "qaclick123").body(b).log().all().when()
				.post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		System.out.println(response);

	}

}
