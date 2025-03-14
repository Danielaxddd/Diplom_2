package User;

import basic.BasicUrl;
import constant.ChangeDataForUser;
import constant.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constant.Pens.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ChangeUserDataTest extends BasicUrl{
    public static String accessToken;

    @Before
    @Step("Создание пользователя")
    public void createProfileUser(){
        CreateUser createUser = new CreateUser("laladsala@yandex.ru", "123456", "lalalala");
        accessToken = BasicPostApi(createUser, CREATE_USER).then().extract().path("accessToken").toString();
    }

    @After
    @Step("Удаление пользователя")
    public void deleteProfileUser(){
        BasicDeleteApi(accessToken).then().assertThat().statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("PATCH Изменение данных пользователя c авторизацией")
    @Description("Post-запрос /api/auth/user")
    public void changeDataUserAutorizedTest(){
        ChangeDataForUser changeDataForUser = new ChangeDataForUser("lolollo@yandex.ru", "lolololo");
        BasicChangeUserData(changeDataForUser,accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("PATCH Изменение данных пользователя без авторизации")
    @Description("Post-запрос /api/auth/user")
    public void changeDataWithoutAutorizedTest(){
        BasicChangeUserData("","").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }
}
