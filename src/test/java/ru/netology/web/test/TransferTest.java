package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class TransferTest {
    LoginPage loginPage;

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldTransfer() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var balanceOfFirstCard = dashboardPage.getCardBalance(firstCard.getIndex());
        var balanceOfSecondCard = dashboardPage.getCardBalance(secondCard.getIndex());
        var transferAmount = DataHelper.generateValidAmount(balanceOfSecondCard);
        var expectedBalanceOfFirstCard = balanceOfFirstCard + transferAmount;
        var expectedBalanceOfSecondCard = balanceOfSecondCard - transferAmount;
        var refillPage = dashboardPage.transferTo(firstCard.getIndex());
        dashboardPage = refillPage.ValidTransfer(String.valueOf(transferAmount), secondCard);
        var actualBalanceOfFirstCard = dashboardPage.getCardBalance(firstCard.getIndex());
        var actualBalanceOfSecondCard = dashboardPage.getCardBalance(secondCard.getIndex());
        Assertions.assertEquals(expectedBalanceOfFirstCard, actualBalanceOfFirstCard);
        Assertions.assertEquals(expectedBalanceOfSecondCard, actualBalanceOfSecondCard);
    }
}