package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RefillPage {

    private ElementsCollection headings = $$(".heading");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public RefillPage() {
        headings.find(Condition.exactText("Пополнение карты")).shouldBe(visible);
    }

    private SelenideElement card = $("[data-test-id=to] input");

    public String validCard() {
        if (card.val().equals(DataHelper.getInvisibleCard().getCardNumber())) {
            return DataHelper.getSecondCard().getCardNumber();
        } else {
            return DataHelper.getFirstCard().getCardNumber();
        }
    }

    public DashboardPage transferAmount(DataHelper.TransferAmount transferAmount) {
        amountField.setValue(transferAmount.getAmount());
        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.click();
        fromField.setValue(validCard());
        transferButton.click();
        return new DashboardPage();
    }
}
