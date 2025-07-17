import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class bugReportJira {
	
	@Test
	public void createBug() {
		
		RestAssured.baseURI = "https://ananduvk00.atlassian.net/";
		
		String createBugresponse = given().header("Content-Type","application/json").header("Authorization","Basic YW5hbmR1dmswMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwbkdPdW1kZWtGWU5nOG1oOElUV2UzT3RHdW9SSDE0VUh3c2pBbHJ2dzU3UXFGVE0zc05PSjluYkVHWnZpb3ZvdGx1TkJBVms2b1dYdHdLa25aZF8tSTBqRTdWUUNSZVRhU3lLS2J2bTM2UmNLdkdGb0NVdFJXOWVHMWpEVUtmVEIxOXlXZVNxMG5qVnl6T2h2dGx1NklMTm5OUUF4NDdrRmJJSnhIWjRZMGJjPTk3NDc3MUJG")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Sign up Button not working\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}\r\n"
				+ "")
		.log().all()
		.when().post("rest/api/3/issue")
		.then().assertThat().statusCode(201).log().all()
		.extract().response().asString();
		
		JsonPath js = new JsonPath(createBugresponse);
		String bugid = js.getString("id");
		System.out.println(bugid);
		
		//Add attachment
		given().pathParam("key", bugid)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic YW5hbmR1dmswMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwbkdPdW1kZWtGWU5nOG1oOElUV2UzT3RHdW9SSDE0VUh3c2pBbHJ2dzU3UXFGVE0zc05PSjluYkVHWnZpb3ZvdGx1TkJBVms2b1dYdHdLa25aZF8tSTBqRTdWUUNSZVRhU3lLS2J2bTM2UmNLdkdGb0NVdFJXOWVHMWpEVUtmVEIxOXlXZVNxMG5qVnl6T2h2dGx1NklMTm5OUUF4NDdrRmJJSnhIWjRZMGJjPTk3NDc3MUJG")
		.multiPart("file", new File("C:\\Users\\Lenovo\\Pictures\\Screenshots\\Screenshot (1).png")).log().all()
		.when().post("rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get Bug
	String getBugResponse =	given().pathParam("key", bugid)
		.header("Authorization","Basic YW5hbmR1dmswMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwbkdPdW1kZWtGWU5nOG1oOElUV2UzT3RHdW9SSDE0VUh3c2pBbHJ2dzU3UXFGVE0zc05PSjluYkVHWnZpb3ZvdGx1TkJBVms2b1dYdHdLa25aZF8tSTBqRTdWUUNSZVRhU3lLS2J2bTM2UmNLdkdGb0NVdFJXOWVHMWpEVUtmVEIxOXlXZVNxMG5qVnl6T2h2dGx1NklMTm5OUUF4NDdrRmJJSnhIWjRZMGJjPTk3NDc3MUJG")
		.when().get("rest/api/3/issue/{key}")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
		JsonPath js1 = new JsonPath(getBugResponse);
		String fileName = js1.getString("fields.attachment[0].filename");
		System.out.println(fileName);
	}

}
