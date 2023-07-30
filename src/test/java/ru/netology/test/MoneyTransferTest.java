package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        int expected = dashboardPage.getCardBalance("1") + 1000;
        int expected1 = dashboardPage.getCardBalance("2") - 1000;
        dashboardPage.depositToFirst();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("2"), "1000");
        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        int expected = dashboardPage.getCardBalance("2") + 1000;
        int expected1 = dashboardPage.getCardBalance("1") - 1000;
        dashboardPage.depositToSecond();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("1"), "1000");
        int actual = dashboardPage.getCardBalance("2");
        int actual1 = dashboardPage.getCardBalance("1");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

    @Test
    void shouldNotTransferMoreThanBalance() {
        int expected = dashboardPage.getCardBalance("1");
        int expected1 = dashboardPage.getCardBalance("2");
        String balance = String.valueOf(dashboardPage.getCardBalance("1") + 1000);
        dashboardPage.depositToSecond();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("1"), balance);
        transferPage.getError();
        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

}
