package user;

import basic.BasicTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersUserTest extends BasicTest {

    @Test
    @DisplayName("GET Получение заказов конкретного пользователя c авторизацией")
    @Description("get-запрос /api/orders")
    public void getOrderUserTest(){
        api.basicGetOrderUser(accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("GET Получение заказов конкретного пользователя без авторизацией")
    @Description("get-запрос /api/orders")
    public void getOrderUserWithoutAutoTest(){
        api.basicGetOrderUser("").then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
