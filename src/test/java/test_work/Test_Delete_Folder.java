package test_work;

import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class Test_Delete_Folder {
    @Test
    public void testDeleteFolder() {
        String nameFolder = "321";
        System.out.println("Тест на удаление:");
        try {
            Response response = given()
                    .contentType("application/json")
                    .headers("Authorization", "OAuth AQAAAAAaz8MGAADLW2OZywZUMEcHtQH8YoPcLb4")
                    .when()
                    .delete("https://cloud-api.yandex.net:443/v1/disk/resources?path=" + nameFolder + "&permanently=false")
                    .then()
                    .extract().response();
            if (response.getStatusCode() == 204) {
                System.out.println("Папка успешно удалена" + response.getStatusCode());
            } else if (response.getStatusCode() == 404) {
                System.out.println("Папка " + nameFolder + " не найден!");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
