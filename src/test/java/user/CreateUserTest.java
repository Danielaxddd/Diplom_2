package user;

import basic.BasicTest;
import constant.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static constant.Url.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends BasicTest {

    @Test
    @DisplayName("Успешное создание пользователя")
    @Description("Post-запрос /api/auth/register")
    public void CreateNewUserTest(){
        CreateUser createUser = new CreateUser("new" + dataUser.getEmail(),"new" + dataUser.getPassword(), "new" + dataUser.getName());
        dataUser.setEmail("new" + dataUser.getEmail());
        dataUser.setPassword("new" + dataUser.getPassword());
        dataUser.setName("new" + dataUser.getName());
        api.basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Cоздание пользователя c email который уже существует")
    @Description("Post-запрос /api/auth/register")
    public void CreateSecondUserTest(){
        CreateUser createUser = new CreateUser(dataUser.getEmail(), dataUser.getPassword(), dataUser.getName());
        api.basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));

    }
    @Test
    @DisplayName("Cоздание пользователя без email")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutEmailTest(){
        CreateUser createUser = new CreateUser("", dataUser.getPassword(), dataUser.getName());
        api.basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cоздание пользователя без password")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutPasswordTest(){
        CreateUser createUser = new CreateUser(dataUser.getEmail(), "", dataUser.getName());
        api.basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Cоздание пользователя без name")
    @Description("Post-запрос /api/auth/register")
    public void CreateUserWithoutNameTest(){
        CreateUser createUser = new CreateUser(dataUser.getEmail(), dataUser.getPassword(), "");
        api.basicPostApi(createUser, CREATE_USER).then().assertThat().statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
