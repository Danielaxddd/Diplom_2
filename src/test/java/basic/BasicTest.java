package basic;

import api.ClientApi;
import api.DataUser;
import api.DeleteClient;
import client.RandomClient;
import constant.ChangeDataForUser;
import constant.CreateUser;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static constant.Url.*;
import static io.restassured.RestAssured.given;

public class BasicTest {
    public static final DataUser dataUser = RandomClient.getUser();
    public ClientApi api;
    public String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_URL;
        api = new ClientApi();
        api.basicCreateApi(dataUser);
        DeleteClient deleteClient = new DeleteClient(dataUser.getEmail(), dataUser.getPassword());
        accessToken = api.login(deleteClient).then().extract().path("accessToken").toString();
    }

    @After
    @Step("Удаление пользователя")
    public void deleteProfileUser(){
        DeleteClient deleteClient = new DeleteClient(dataUser.getEmail(), dataUser.getPassword());
        Response response = api.login(deleteClient);
        if (response.body().jsonPath().getString("accessToken") != null) {
            api.basicDeleteApi(deleteClient);
        }
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
