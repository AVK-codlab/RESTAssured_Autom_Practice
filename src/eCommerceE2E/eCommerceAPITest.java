package eCommerceE2E;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class eCommerceAPITest {

	@Test
	public void eCommerce() {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("apvk@gmail.com");
		loginRequest.setUserPassword("Avk@1234");

		RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);

		LoginResponse loginResponse = reqLogin.when().post("api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);

		System.out.println(loginResponse.getToken());
		String token = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId = loginResponse.getUserId();

		// Add Product

		RequestSpecification reqAddProdBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqAddProd = given().spec(reqAddProdBase).param("productName", "qwert")
				.param("productAddedBy", userId).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "11500")
				.param("productDescription", "AddiasOriginals").param("productFor", "women")
				.multiPart("productImage", new File("C:\\Users\\Lenovo\\Pictures\\Screenshots\\Screenshot (1).png"));

		String responseAddProduct = reqAddProd.when().post("api/ecom/product/add-product").then().log().all().extract()
				.response().asString();

		JsonPath js = new JsonPath(responseAddProduct);
		String productId = js.get("productId");
		System.out.println(productId);

		// Order Product
		RequestSpecification reqOrderProdBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);

		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		orderDetailList.add(orderDetails);

		orders orders = new orders();
		orders.setOrders(orderDetailList);

		RequestSpecification reqOrderProduct = given().spec(reqOrderProdBase).body(orders);
		OrderSummary orderProductResponse = reqOrderProduct.when().post("api/ecom/order/create-order").then().log()
				.all().extract().response().as(OrderSummary.class);

		String orderid = orderProductResponse.getOrders().get(0);

		// View Orders Details
		RequestSpecification reqViewOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqViewOrder = given().spec(reqViewOrderBase).queryParam("id", orderid);

		String viewOrder = reqViewOrder.when().get("api/ecom/order/get-orders-details").then().log().all().extract()
				.response().asString();
		System.out.println(viewOrder);

		// Delete Product
		RequestSpecification reqDeleteProdBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqDeleteProd = given().spec(reqDeleteProdBase).pathParam("productId", productId);
		String deleteProd = reqDeleteProd.when().delete("api/ecom/product/delete-product/{productId}").then().log()
				.all().extract().response().asString();

		JsonPath js2 = new JsonPath(deleteProd);
		String deleteMsg = js2.get("message");

		Assert.assertEquals(deleteMsg, "Product Deleted Successfully");

	}

}
