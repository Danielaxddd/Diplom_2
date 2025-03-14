package User;

import basic.BasicUrl;
import constant.CreateUser;
import constant.UserCanLogOn;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static constant.Pens.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest extends BasicUrl {
    public static String accessToken;

    @DisplayName("Пользователь успешно авторизуется")
    @Description("Post-запрос /api/auth/login")
    @Test
    public void UserCanLogOnTest(){
        UserCanLogOn userCanLogOn = new UserCanLogOn("laladfgdfgla@yandex.ru", "123456");
        BasicPostApi(userCanLogOn, USER_LOGIN).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @DisplayName("Авторизация с неверным полем email")
    @Description("Post-запрос /api/auth/login")
    @Test
    public void UserLogOnWrongEmail(){
        UserCanLogOn userCanLogOn = new UserCanLogOn("lerweululu@yandex.ru", "123456");
        BasicPostApi(userCanLogOn, USER_LOGIN).then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }

    @DisplayName("Авторизация с неверным полем email")
    @Description("Post-запрос /api/auth/login")
    @Test
    public void UserLogOnWrongPassword(){
        UserCanLogOn userCanLogOn = new UserCanLogOn("lalala@yandex.ru", "777777");
        BasicPostApi(userCanLogOn, USER_LOGIN).then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }

    @Before
    @Step("Создание пользователя")
    public void createProfileUser(){
        CreateUser createUser = new CreateUser("laladfgdfgla@yandex.ru", "123456", "lalalala");
        accessToken = BasicPostApi(createUser, CREATE_USER).then().extract().path("accessToken").toString();

    }

    @After
    @Step("Удаление пользователя")
    public void deleteProfileUser(){
        BasicDeleteApi(accessToken).then().assertThat().statusCode(SC_ACCEPTED);
    }
}
