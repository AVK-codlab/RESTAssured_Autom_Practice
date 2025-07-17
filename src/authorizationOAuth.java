import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class authorizationOAuth {
	
	@Test
	public void authorizationOAuthTest() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		//Authorization Server
		String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all().post("oauthapi/oauth2/resourceOwner/token").
		then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String accessToken = js.get("access_token");
		System.out.println(accessToken);
		
		//Get Course Details- Deserialization
		GetCourse gc = given().queryParam("access_token", accessToken)
		.when().get("oauthapi/getCourseDetails")
		.then().log().all().extract().as(GetCourse.class);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api>apiCourses = gc.getCourses().getApi();
		
		for(int i=0; i<apiCourses.size(); i++) {
			
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		List<WebAutomation> webCourse = gc.getCourses().getWebAutomation();
		for(int j=0; j<webCourse.size(); j++) {
			System.out.println(webCourse.get(j).getCourseTitle());
		}
		
	}

}
