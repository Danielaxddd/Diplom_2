package client;

import api.DataUser;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class RandomClient {
    public static DataUser getUser() {

        String email = randomAlphabetic(10).toLowerCase() + "@yandex.ru";
        String password = randomAlphabetic(3) + "qwe";
        String name = randomAlphabetic(10);

        return new DataUser(email, password, name);
    }
}