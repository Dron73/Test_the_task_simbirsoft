package test_work;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class Test_Create_Folder {

    StringBuilder stringBuilder = new StringBuilder();

    @Test
    public void testCreateFolder() {
        String nameFolder = "024";
        System.out.println("Тест на создание: ");
        try {
            Response response = given()
                    .contentType("application/json; charset=utf-8")
                    .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                    .when()
                    .put("https://cloud-api.yandex.net/v1/disk/resources?path=" + nameFolder)
                    .then()
                    .extract().response();
            JsonPath jp = new JsonPath(response.asString());
            int codeStatus = response.getStatusCode();
            if (codeStatus == 201) {
                String href = jp.get("href").toString();
                String method = jp.get("method").toString();
                String templated = jp.get("templated").toString();
                stringBuilder.append("href: " + href + "\n"
                        + "method: " + method + "\n"
                        + "templated: " + templated + "\n"
                        + "codeStatus status: " + codeStatus);
                System.out.println(stringBuilder.toString());
            } else {
                String message = jp.get("message").toString();
                stringBuilder.append("Message: " + message + "\n"
                        + "codeStatus status: " + codeStatus);
                System.out.println(stringBuilder.toString());
            }
            System.out.println();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}



