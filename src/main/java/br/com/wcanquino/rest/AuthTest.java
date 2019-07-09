package br.com.wcanquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.Test;

public class AuthTest {

	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.co/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		;
	}
	
	//https://api.openweathermap.org/data/2.5/weather?q=fortaleza,BR&appid=e1c682db71002e7e5a91fb775e4d6a67&units=metric
	@Test
	public void deveObterClima() {
		given()
		.log().all()
		.queryParam("q", "fortaleza,BR")
		.queryParam("appid", "e1c682db71002e7e5a91fb775e4d6a67")
		.queryParam("units", "metric")
	.when()
		.get("https://api.openweathermap.org/data/2.5/weather")
	.then()
		.log().all()
		.statusCode(200)
		.body("name", is("Fortaleza"))
		.body("coord.lon", is(-38.52f))
		.body("coord.lat", is(-3.73f))
		.body("main.temp", Matchers.greaterThan(25.0f))
	;
	}
}
