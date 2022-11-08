package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RefillPage {

    private ElementsCollection headings = $$(".heading");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public RefillPage() {
        headings.find(Condition.exactText("Пополнение карты")).shouldBe(visible);
    }

    public void transferAmount(String transferAmount, DataHelper.CardNumber cardNumber) {
        amountField.setValue(transferAmount);
        fromField.setValue(cardNumber.getCardNumber());
        transferButton.click();
    }
    public DashboardPage ValidTransfer(String transferAmount, DataHelper.CardNumber cardNumber) {
        transferAmount(transferAmount, cardNumber);
        return new DashboardPage();
    }

    public void errorMessage(String expectedText) {
        errorMessage.shouldHave(Condition.text(expectedText),
                Duration.ofSeconds(15)).shouldBe(visible);
    }
}
