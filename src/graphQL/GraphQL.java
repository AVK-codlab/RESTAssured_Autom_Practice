package graphQL;
import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

public class GraphQL {

	public static void main(String[] args) {
		
		//Query
		int characterId = 15557;
		String queryResponse = given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"query($locationId:Int!, $characterId:Int!, $episodeId:Int!){\\n  location(locationId: $locationId)\\n  {\\n    name\\n    type\\n  }\\n  character(characterId:$characterId){\\n    name\\n    type\\n    status\\n    species\\n  }\\n  episode(episodeId:$episodeId)\\n  {\\n    id\\n    name\\n    air_date\\n    episode\\n    characters{\\n      id\\n      name\\n      status\\n      species\\n    }\\n  }\\n}\\n\",\"variables\":{\"locationId\":22041,\"characterId\":"+characterId+",\"episodeId\":15143}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		JsonPath js = new JsonPath(queryResponse);
		String characterName = js.get("data.character.name");
		System.out.println(characterName);
		
		//Mutation
		String mutationResponse = given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"mutation($locationName:String!,$characterName:String!,$episodeName:String!)\\n{\\n  createLocation(location: {name: $locationName, type: \\\"Europe\\\", dimension: \\\"111\\\"}) \\n  {\\n    id\\n  }\\n  \\n  createCharacter(character:{name:$characterName,type:\\\"Build\\\",status:\\\"alive\\\",species:\\\"Human\\\",gender:\\\"male\\\",image:\\\"png\\\",originId:22041,locationId:22041})\\n  {\\n    id\\n  }\\n  createEpisode(episode:{name:$episodeName,air_date:\\\"2025\\\",episode:\\\"082025\\\"})\\n  {\\n    id\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"Australia\",\"characterName\":\"Tom\",\"episodeName\":\"Fight and Get\"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		System.out.println(mutationResponse);

	}

}
