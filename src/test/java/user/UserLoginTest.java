package user;

import basic.BasicTest;
import constant.UserCanLogOn;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static constant.Url.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserLoginTest extends BasicTest {

    @DisplayName("Пользователь успешно авторизуется")
    @Description("Post-запрос /api/auth/login")
    @Test
    public void UserCanLogOnTest(){
        UserCanLogOn userCanLogOn = new UserCanLogOn(dataUser.getEmail(), dataUser.getPassword());
        api.basicPostApi(userCanLogOn, USER_LOGIN).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());
    }

    @DisplayName("Авторизация с неверным полем email")
    @Description("Post-запрос /api/auth/login")
    @Test
    public void UserLogOnWrongEmail(){
        UserCanLogOn userCanLogOn = new UserCanLogOn("1" + dataUser.getEmail(), dataUser.getPassword());
        api.basicPostApi(userCanLogOn, USER_LOGIN).then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @DisplayName("Авторизация с неверным полем пароль")
    @Description("Post-запрос /api/auth/login")
    @Test
    public void UserLogOnWrongPassword(){
        UserCanLogOn userCanLogOn = new UserCanLogOn(dataUser.getEmail(), "1" + dataUser.getPassword());
        api.basicPostApi(userCanLogOn, USER_LOGIN).then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
