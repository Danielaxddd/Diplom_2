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

import static constant.Pens.CREATE_USER;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersUserTest extends BasicUrl {
    public static String accessToken;

    @Before
    @Step("Создание пользователя")
    public void createProfileUser(){
        CreateUser createUser = new CreateUser("luhula@yandex.ru", "123456", "lalalala");
        accessToken = BasicPostApi(createUser, CREATE_USER).then().extract().path("accessToken").toString();
    }

    @After
    @Step("Удаление пользователя")
    public void deleteProfileUser(){
        BasicDeleteApi(accessToken).then().assertThat().statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("GET Получение заказов конкретного пользователя c авторизацией")
    @Description("get-запрос /api/orders")
    public void getOrderUserTest(){
        BasicGetOrderUser(accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("GET Получение заказов конкретного пользователя без авторизацией")
    @Description("get-запрос /api/orders")
    public void getOrderUserWithoutAutoTest(){
        BasicGetOrderUser("").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }
}
