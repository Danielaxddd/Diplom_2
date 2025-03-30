package basic;

import api.ClientApi;
import api.DataUser;
import api.DeleteClient;
import client.RandomClient;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static constant.Url.*;


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

}
