package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String number;
    }

    public static CardInfo getCard(String id) {
        CardInfo cardInfo = new CardInfo(null);
        if (id.equals("1")) {
            return cardInfo = new CardInfo("5559000000000001");
        }
        if (id.equals("2")) {
            return cardInfo = new CardInfo("5559000000000002");
        }
        return cardInfo;
    }
}
