package User;

import basic.BasicUrl;
import constant.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static constant.Pens.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends BasicUrl {

    @Test
    @DisplayName("Успешное создание пользователя")
    @Description("Post-запрос /api/auth/register")
    public void CreateNewUserTest(){
        CreateUser createUser = new CreateUser("lalalla@yandex.ru", "123456", "lalala");
        String accessToken = BasicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true)).extract().path("accessToken").toString();
        BasicDeleteApi(accessToken).then().assertThat().statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("Cоздание пользователя c email который уже существует")
    @Description("Post-запрос /api/auth/register")
    public void CreateSecondUserTest(){
        CreateUser createUser = new CreateUser("lalaGla@yandex.ru", "1234567", "lalGalala");
        String accessToken = BasicPostApi(createUser, CREATE_USER).then().extract().path("accessToken").toString();
        BasicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("message", equalTo("User already exists"));
        BasicDeleteApi(accessToken).then().assertThat().statusCode(SC_ACCEPTED);

    }
    @Test
    @DisplayName("Cоздание пользователя без email")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutEmailTest(){
        CreateUser createUser = new CreateUser("", "1234567", "lalalala");
        BasicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cоздание пользователя без password")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutPasswordTest(){
        CreateUser createUser = new CreateUser("lalala@yandex.ru", "", "lalalala");
        BasicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cоздание пользователя без name")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutNameTest(){
        CreateUser createUser = new CreateUser("lalala@yandex.ru", "123456", "");
        BasicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
