package br.com.wcanquino.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 200);
		Assert.assertEquals(200, response.statusCode());
		
		ValidatableResponse validation = response.then();
		validation.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		
		ValidatableResponse validation = response.then();
		validation.statusCode(200);
		
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given()//Pre-conditions
		.when()//Actions - HTTP verbs
			.get("http://restapi.wcaquino.me/ola")
		.then()//Validations
			.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void devoConhecerMatchersHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(123, Matchers.is(123));
		Assert.assertThat(123, Matchers.isA(Integer.class));
		Assert.assertThat(123d, Matchers.isA(Double.class));
		Assert.assertThat(123d, Matchers.greaterThan(120d));
		Assert.assertThat(123d, Matchers.lessThan(130d));
		
		List<Integer> odds = Arrays.asList(1, 3, 5, 7, 9);
		assertThat(odds, hasSize(5));
		assertThat(odds, contains(1, 3, 5, 7, 9));
		assertThat(odds, containsInAnyOrder(1, 3, 5, 7, 9));
		assertThat(odds, hasItem(1));
		assertThat(odds, hasItems(1, 3));
		
		assertThat("Maria", is(not("Joao")));
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
		
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
	}
}
