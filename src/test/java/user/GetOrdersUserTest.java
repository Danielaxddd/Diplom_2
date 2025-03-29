package user;

import basic.BasicTest;
import constant.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constant.Url.CREATE_USER;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersUserTest extends BasicTest {

    @Test
    @DisplayName("GET Получение заказов конкретного пользователя c авторизацией")
    @Description("get-запрос /api/orders")
    public void getOrderUserTest(){
        basicGetOrderUser(accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("GET Получение заказов конкретного пользователя без авторизацией")
    @Description("get-запрос /api/orders")
    public void getOrderUserWithoutAutoTest(){
        basicGetOrderUser("").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
