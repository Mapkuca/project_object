package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private int extractBalance(String text){
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    public int getCardBalance(int index) {
        var card = cards.get(index);
        val text = card.text();
        return extractBalance(text);
    }

    public int minBalance() {
        int index = 0;
        int min = getCardBalance(index);
        int size = cards.size();
        for (int i = 0; i < size; i++) {
            int balance = getCardBalance(i);
            if (balance < min) {
                min = balance;
                index = i;
            }
        }
        return index;
    }

    public int minBalanceIndex() {
        return minBalance();
    }

    public RefillPage transferTo(int index) {
        SelenideElement button = $("[data-test-id=action-deposit]", index);
        button.click();
        return new RefillPage();
    }

    public void validBalance(int index, int expectedBalance) {
        var card = cards.get(index);
        card.shouldBe(visible);
        Assertions.assertEquals(getCardBalance(index), expectedBalance);
    }
}
