package br.com.wcanquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
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
	
	@Test
	public void naoDeveFazerUploadDeArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/DSC_0359.JPG"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(10000L))
			.statusCode(413)
		;
	}
	
	@Test
	public void deveBaixarArquivo() throws IOException {
		byte[] image = given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()
			.statusCode(200)
			.extract().asByteArray()
		;
		
		File file = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(file);
		out.write(image);
		out.close();
		
		Assert.assertThat(file.length(), lessThan(100000L));
	}
}
