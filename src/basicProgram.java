import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;

public class basicProgram {

	public static void main(String[] args) {
		// Given - inputs
		// When - Submit API - resource, http method
		// Then - Validate response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Add place
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.addPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);  //for parsing json
		String placeid = js.getString("place_id");
		
		System.out.println(placeid);
		
		//Edit Place - Put
		String newAddress = "70 Summer walk, USA";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+ placeid +"\",\r\n"
				+ "\"address\":\""+ newAddress +"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String getPlaceresponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js1 = new JsonPath(getPlaceresponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		
	}
}
