package br.com.wcanquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Test;

public class FileTest {

	@Test
	public void deveObrigarEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) //Deveria ser 400
			.body("error", is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void deveFazerUploadDeArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/man.png"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("man.png"))
		;
	}
}
