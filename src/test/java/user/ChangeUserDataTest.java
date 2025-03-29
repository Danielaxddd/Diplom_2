package user;

import basic.BasicTest;
import constant.ChangeDataForUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class ChangeUserDataTest extends BasicTest {
    @Test
    @DisplayName("PATCH Изменение почты пользователя c авторизацией")
    @Description("PATCH-запрос /api/auth/user")
    public void changeEmailUserAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser("lolollo22@yandex.ru", dataUser.getName());
        basicChangeUserData(changeDataForUser,accessToken).then().assertThat().statusCode(SC_OK)
                .body("user.email", equalTo("lolollo22@yandex.ru"))
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("PATCH Изменение имени пользователя c авторизацией")
    @Description("PATCH-запрос /api/auth/user")
    public void changeNameUserAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser(dataUser.getEmail(), "loldololo");
        basicChangeUserData(changeDataForUser,accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.name", equalTo("loldololo"));

    }

    @Test
    @DisplayName("PATCH Изменение почты пользователя без авторизации")
    @Description("Post-запрос /api/auth/user")
    public void changeEmailWithoutAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser("lolollo22@yandex.ru", dataUser.getName());
        basicChangeUserData(changeDataForUser,"").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("PATCH Изменение имени пользователя без авторизации")
    @Description("Post-запрос /api/auth/user")
    public void changeNameWithoutAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser(dataUser.getEmail(), "loldololo");
        basicChangeUserData(changeDataForUser,"").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
