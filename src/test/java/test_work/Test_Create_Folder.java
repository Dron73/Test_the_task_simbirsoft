package test_work;

import com.jayway.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class Test_Create_Folder {

    @Test
    public void testCreateFolder201() {
        String nameFolder = "Тест201"; // OK

        JsonPath responseJsonPath = given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder)
                .then()
                .assertThat().statusCode(201).body("method", equalTo("GET"))
                .extract().jsonPath();
    }

    @Test
    public void testCreateFolder400() {
        String nameFolder = ""; // Некорректные данные. (без имени)

        JsonPath responseJsonPath = given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder)
                .then()
                .assertThat().statusCode(400).body("error", equalTo("FieldValidationError"))
                .extract().jsonPath();
    }

    @Test
    public void testCreateFolder401() {
        String nameFolder = "Тест401"; // Не авторизован.

        JsonPath responseJsonPath = given()
                .contentType("application/json; charset=utf-8")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder)
                .then()
                .assertThat().statusCode(401).body("error", equalTo("UnauthorizedError"))
                .extract().jsonPath();
    }

    @Test
    public void testCreateFolder404() {
        String nameFolder = "Тест404"; // Не удалось найти запрошенный ресурс.

        JsonPath responseJsonPath = given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resourcessss?path=" + nameFolder)
                .then()
                .assertThat().statusCode(404).body("error", equalTo("NotFoundError"))
                .extract().jsonPath();
    }

    @Test
    public void testCreateFolder409() {
        String nameFolder = "Тест409"; // Попытка создать уже созданную папку

        given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder);

        JsonPath responseJsonPath = given()
                .contentType("application/json; charset=utf-8")
                .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                .when()
                .put("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder)
                .then()
                .assertThat().statusCode(409).body("error", equalTo("DiskPathPointsToExistentDirectoryError"))
                .extract().jsonPath();
    }
}



