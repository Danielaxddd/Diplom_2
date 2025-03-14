package basic;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import static constant.Pens.*;
import static io.restassured.RestAssured.given;

public class BasicUrl {
    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_URL;
    }
    @Step("POST ручка создание и входа пользователя")
    public static Response BasicPostApi(Object a, String api){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(a)
                        .when()
                        .post(api);
        return response;
    }

    @Step("DELETE ручка для удаления пользователя")
    public Response BasicDeleteApi (String token){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .header( "Authorization",token)
                        .when()
                        .delete(DELETE_USER);
        return response;
    }

    @Step("PATCH ручка для изменения данных пользователя")
    public Response BasicChangeUserData(Object a, String token){
        Response response =
                given()
                        .header("Authorization",token)
                        .body(a)
                        .when()
                        .patch(CHANGE_USER_DATA);
        return response;
    }

    @Step("POST Создание заказа")
    public Response BasicCreateOrder(Object a, String token){
        Response response =
                given()
                        .header("Authorization",token)
                        .header("Content-type", "application/json")
                        .body(a)
                        .when()
                        .post(CREATE_ORDER);
        return response;
    }
    @Step("GET Получение заказов конкретного пользователя")
    public Response BasicGetOrderUser(String token){
        Response response =
                given()
                        .header("Authorization",token)
                        .header("Content-type", "application/json")
                        .when()
                        .get(GET_USER_ORDERS);
        return response;
    }
}
