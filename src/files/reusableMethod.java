package files;

import io.restassured.path.json.JsonPath;

public class reusableMethod {
	
	public static JsonPath JsonParse(String response) {
		
		JsonPath js = new JsonPath(response);
		return js;
	}

}
