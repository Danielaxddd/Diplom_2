package User;

import basic.BasicUrl;
import constant.CreateOrder;
import constant.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static constant.Pens.CREATE_USER;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest extends BasicUrl  {
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
    @DisplayName("POST Создание заказа")
    @Description("Post-запрос /api/orders")
    public void CreateOrderAutorizedUserTest(){
        CreateOrder createOrder = new CreateOrder(List.of("61c0c5a71d1f82001bdaaa76","61c0c5a71d1f82001bdaaa75"));
        BasicCreateOrder(createOrder, accessToken).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("POST Создание заказа без авторизации")
    @Description("Post-запрос /api/orders")
    public void CreateOrderWithoutAutorizedUserTest(){
        CreateOrder createOrder = new CreateOrder(List.of("61c0c5a71d1f82001bdaaa76","61c0c5a71d1f82001bdaaa75"));
        BasicCreateOrder(createOrder, "").then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("POST Создание заказа с неверным хешем ингредиентов")
    @Description("Post-запрос /api/orders")
    public void CreateOrderWrongHash(){
        CreateOrder createOrder = new CreateOrder(List.of("61c0c5a1f82001b","61c0c51f82001b"));
        BasicCreateOrder(createOrder, accessToken).then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
    @Test
    @DisplayName("POST Создание заказа без ингредиентов")
    @Description("Post-запрос /api/orders")
    public void CreateOrderWithoutIngred(){
        BasicCreateOrder("", accessToken).then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

}
