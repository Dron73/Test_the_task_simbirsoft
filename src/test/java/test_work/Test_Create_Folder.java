package test_work;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class Test_Create_Folder {
    @Test
    public void testCreateFolder() {
        String nameFolder = "000";
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("Тест на создание:");
        try {
            Response response = given()
                    .contentType("application/json; charset=utf-8")
                    .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                    .when()
                    .put("https://cloud-api.yandex.net/v1/disk/resources?path=" + nameFolder)
                    .then().extract().response();

            JsonPath jsonPath = new JsonPath(response.asString());
            int codeStatus = response.getStatusCode();
            if (codeStatus == 201) {
                String href = jsonPath.get("href").toString();
                String method = jsonPath.get("method").toString();
                String templated = jsonPath.get("templated").toString();
                stringBuilder.append("Href: " + href + "\n"
                        + "Method: " + method + "\n"
                        + "Templated: " + templated + "\n"
                        + "CodeStatus status: " + codeStatus + "\n");
                System.out.println(stringBuilder.toString());
            } else {
                String message = jsonPath.get("message").toString();
                String description = jsonPath.get("description").toString();
                String error = jsonPath.get("error").toString();

                stringBuilder.append("Message: " + message + ".\n"
                        + "Description: " + description + ".\n"
                        + "Error: " + error + ".\n");
                System.out.println(stringBuilder);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}



