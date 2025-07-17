import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import files.reusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class libraryAPI {
	
	@Test(dataProvider="BookData")
	public void libraryBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String bookResponse = given().log().all().header("Content-Type", "application/json").body(payload.addBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = reusableMethod.JsonParse(bookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	@DataProvider(name="BookData")
	public Object[][] data() {
		return new Object[][] {{"aase","147"},{"qwa","126"},{"ppa","197"}};
	}

}
