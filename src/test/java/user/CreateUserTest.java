package user;

import basic.BasicTest;
import constant.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static constant.Url.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends BasicTest {

    @Test
    @DisplayName("Успешное создание пользователя")
    @Description("Post-запрос /api/auth/register")
    public void CreateNewUserTest(){
        CreateUser createUser = new CreateUser("1lalas3424lla232@yandex.ru", "123456", "lalala");
        dataUser.setEmail("1lalas3424lla232@yandex.ru");
        dataUser.setPassword("123456");
        dataUser.setName("lalala");
        basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Cоздание пользователя c email который уже существует")
    @Description("Post-запрос /api/auth/register")
    public void CreateSecondUserTest(){
        CreateUser createUser = new CreateUser(dataUser.getEmail(), dataUser.getPassword(), dataUser.getName());
        basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));

    }
    @Test
    @DisplayName("Cоздание пользователя без email")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutEmailTest(){
        CreateUser createUser = new CreateUser("", "1234567", "lalalala");
        basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cоздание пользователя без password")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutPasswordTest(){
        CreateUser createUser = new CreateUser("lalala@yandex.ru", "", "lalalala");
        basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cоздание пользователя без name")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutNameTest(){
        CreateUser createUser = new CreateUser("lalala@yandex.ru", "123456", "");
        basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
