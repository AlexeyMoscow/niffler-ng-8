package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage {
    private final SelenideElement searchInput = $("input[aria-label='search']");
    private final SelenideElement emptyFriendsTable = $("#simple-tabpanel-friends");
    private final SelenideElement tabsSwitcher = $("a[aria-selected='false']");
    private final ElementsCollection allPeopleRows = $$("tbody#all tr");


    public void verifyNameInTableByType(String type, String name) {
        $("#" + type).shouldHave(text(name));
    }

    public void verifyFriendsTableEmpty() {
        emptyFriendsTable.shouldBe(visible).shouldHave(text("There are no users yet"));
    }

    public FriendsPage setValueIntoSearch(String value) {
        searchInput.setValue(value).sendKeys(Keys.ENTER);
        return this;
    }

    public void verifyUserPresentInAllPeopleTableAndCheckStatus(String name, String status) {
        allPeopleRows.findBy(text(name)).$("span").shouldHave(text(status)).shouldBe(visible);
    }

    public FriendsPage switchTab() {
        tabsSwitcher.click();
        return this;
    }
}
