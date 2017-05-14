package test_work;

import com.jayway.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class Test_Delete_Folder {

    @Test
    public void testDeleteFolder204() {
        String nameFolder = "delete204"; //  	OK

        given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder);

        JsonPath response = given()
                .contentType("application/json")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .delete("https://cloud-api.yandex.net:443/v1/disk/resources?path="
                        + nameFolder + "&permanently=false")
                .then().statusCode(204)
                .extract().jsonPath();
    }

    @Test
    public void testDeleteFolder400() { // Некорректные данные.
        String nameFolder = "delete400";

        given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder);

        JsonPath responseJsonPath = given()
                .contentType("application/json")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .delete("https://cloud-api.yandex.net:443/v1/disk/resources?path="
                        + "&permanently=false")
                .then()
                .assertThat().statusCode(400)
                .body("error", equalTo("FieldValidationError"))
                .and()
                .body("description", equalTo("Error validating field \"path\": This field is required."))
                .and()
                .body("message", equalTo("Ошибка проверки поля \"path\": Это поле является обязательным."))
                .extract().jsonPath();
    }

    @Test
    public void testDeleteFolder401() { // Не авторизован.
        String nameFolder = "delete401";

        given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder);

        JsonPath responseJsonPath = given()
                .contentType("application/json")
                .when()
                .delete("https://cloud-api.yandex.net:443/v1/disk/resources?path="
                        + "&permanently=false")
                .then()
                .assertThat().statusCode(401)
                .body("description", equalTo("Unauthorized"))
                .and()
                .body("message", equalTo("Не авторизован."))
                .and()
                .body("error", equalTo("UnauthorizedError"))
                .extract().jsonPath();
    }

    @Test
    public void testDeleteFolder404() { // Не удалось найти запрошенный ресурс.
        String nameFolder = "delete404";

        given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder);

        JsonPath responseJsonPath = given()
                .contentType("application/json")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .delete("https://cloud-api.yandex.net:443/v1/disk/resources?path="
                        + nameFolder + "fail" + "&permanently=false")
                .then()
                .assertThat().statusCode(404)
                .body("description", equalTo("Resource not found."))
                .and()
                .body("message", equalTo("Не удалось найти запрошенный ресурс."))
                .and()
                .body("error", equalTo("DiskNotFoundError"))
                .extract().jsonPath();
    }

}
