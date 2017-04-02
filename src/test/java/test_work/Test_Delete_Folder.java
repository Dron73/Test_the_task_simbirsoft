package test_work;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class Test_Delete_Folder {
    @Test
    public void testDeleteFolder() {
        String nameFolder = "000";
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("Тест на удаление:");
        try {
            Response response = given()
                    .contentType("application/json")
                    .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                    .when()
                    .delete("https://cloud-api.yandex.net:443/v1/disk/resources?path="
                            + nameFolder + "&permanently=false")
                    .then().extract().response();

            JsonPath jsonPath = new JsonPath(response.asString());
            int codeStatus = response.getStatusCode();
            if (codeStatus == 204) {
                System.out.println("Папка " + nameFolder + " успешно удалена"
                        + "\n" + "CodeStatus: " + codeStatus);
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
