package dataDrivenExcel;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import files.reusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class libraryAPI {
	
	@Test
	public void libraryBook() throws IOException {
		
		ExcelData testdata = new ExcelData();
		ArrayList data = testdata.excelData("Add Book", "Library API");
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));
		
		RestAssured.baseURI = "http://216.10.245.166";
		String bookResponse = given().log().all().header("Content-Type", "application/json").body(map)
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = reusableMethod.JsonParse(bookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}

}
