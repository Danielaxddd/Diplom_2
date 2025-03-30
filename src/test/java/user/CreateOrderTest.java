package user;

import basic.BasicTest;
import constant.CreateOrder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest extends BasicTest {

    @Test
    @DisplayName("POST Создание заказа")
    @Description("Post-запрос /api/orders")
    public void CreateOrderAutorizedUserTest(){
        CreateOrder createOrder = new CreateOrder(List.of("61c0c5a71d1f82001bdaaa76","61c0c5a71d1f82001bdaaa75"));
        api.basicCreateOrder(createOrder, accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("POST Создание заказа без авторизации")
    @Description("Post-запрос /api/orders")
    public void CreateOrderWithoutAutorizedUserTest(){
        CreateOrder createOrder = new CreateOrder(List.of("61c0c5a71d1f82001bdaaa76","61c0c5a71d1f82001bdaaa75"));
        api.basicCreateOrder(createOrder, "").then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("POST Создание заказа с неверным хешем ингредиентов")
    @Description("Post-запрос /api/orders")
    public void CreateOrderWrongHash(){
        CreateOrder createOrder = new CreateOrder(List.of("61c0c5a1f82001b","61c0c51f82001b"));
        api.basicCreateOrder(createOrder, accessToken).then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
    @Test
    @DisplayName("POST Создание заказа без ингредиентов")
    @Description("Post-запрос /api/orders")
    public void CreateOrderWithoutIngred(){
        api.basicCreateOrder("", accessToken).then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

}
