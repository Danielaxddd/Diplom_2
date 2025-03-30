package api;


import constant.ChangeDataForUser;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static constant.Url.*;
import static constant.Url.GET_USER_ORDERS;
import static io.restassured.RestAssured.given;

public class ClientApi {

    @Step("POST ручка для создания пользователя")
    public void basicCreateApi (DataUser user){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(CREATE_USER);

    }

    @Step("DELETE ручка для удаления пользователя")
    public void basicDeleteApi (DeleteClient deleteClient){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        String accessToken =  given()
                .header("Content-type", "application/json")
                .body(deleteClient)
                .when()
                .post(USER_LOGIN).then().extract().path("accessToken").toString();
        given()
                .header("Content-type", "application/json")
                .header( "Authorization",accessToken)
                .when()
                .delete(DELETE_USER);
    }
    @Step("Логин пользователя")
    public Response login(DeleteClient credentials) {
        return (Response) given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(USER_LOGIN).then().extract();
    }
    @Step("POST ручка создание и входа пользователя")
    public static Response basicPostApi(Object a, String api){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(a)
                        .when()
                        .post(api);
        return response;
    }

    @Step("DELETE ручка для удаления пользователя")
    public Response basicDeleteApi (String token){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .header( "Authorization",token)
                        .when()
                        .delete(DELETE_USER);
        return response;
    }

    @Step("PATCH ручка для изменения данных пользователя")
    public Response basicChangeUserData(ChangeDataForUser a, String token){
        Response response =
                given()
                        .header("Authorization",token)
                        .header("Content-type", "application/json")
                        .body(a)
                        .when()
                        .patch(CHANGE_USER_DATA);
        return response;
    }

    @Step("POST Создание заказа")
    public Response basicCreateOrder(Object a, String token){
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
    public Response basicGetOrderUser(String token){
        Response response =
                given()
                        .header("Authorization",token)
                        .header("Content-type", "application/json")
                        .when()
                        .get(GET_USER_ORDERS);
        return response;
    }

}
