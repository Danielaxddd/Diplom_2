package api;


import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClientApi {

    @Step("POST ручка для создания пользователя")
    public void basicCreateApi (DataUser user){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("api/auth/register/").then().statusCode(200);

    }

    @Step("DELETE ручка для удаления пользователя")
    public void basicDeleteApi (DeleteClient deleteClient){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        String accessToken =  given()
                .header("Content-type", "application/json")
                .body(deleteClient)
                .when()
                .post("/api/auth/login").then().extract().path("accessToken").toString();
        given()
                .header("Content-type", "application/json")
                .header( "Authorization",accessToken)
                .when()
                .delete("/api/auth/user");
    }
    @Step("Логин пользователя")
    public Response login(DeleteClient credentials) {
        return (Response) given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post("api/auth/login/").then().extract();
    }

}
