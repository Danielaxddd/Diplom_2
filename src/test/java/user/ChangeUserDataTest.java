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
        ChangeDataForUser changeDataForUser = new ChangeDataForUser("new" + dataUser.getEmail(), dataUser.getName());
        api.basicChangeUserData(changeDataForUser,accessToken).then().assertThat().statusCode(SC_OK)
                .body("user.email", equalTo("new" + dataUser.getEmail()))
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("PATCH Изменение имени пользователя c авторизацией")
    @Description("PATCH-запрос /api/auth/user")
    public void changeNameUserAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser(dataUser.getEmail(), "new" + dataUser.getName());
        api.basicChangeUserData(changeDataForUser,accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.name", equalTo("new" + dataUser.getName()));

    }

    @Test
    @DisplayName("PATCH Изменение почты пользователя без авторизации")
    @Description("Post-запрос /api/auth/user")
    public void changeEmailWithoutAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser("new" + dataUser.getEmail(), dataUser.getName());
        api.basicChangeUserData(changeDataForUser,"").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("PATCH Изменение имени пользователя без авторизации")
    @Description("Post-запрос /api/auth/user")
    public void changeNameWithoutAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser(dataUser.getEmail(), "new" + dataUser.getName());
        api.basicChangeUserData(changeDataForUser,"").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
