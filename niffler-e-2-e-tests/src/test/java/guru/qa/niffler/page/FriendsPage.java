package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage {
    public static final String URL = Config.getInstance().authUrl() + "people/friends";

    private final ElementsCollection friendNames = $$("#friends");
    private final ElementsCollection friendRequest = $$("#requests");
    private final SelenideElement lonelyNifflerImg = $("img[alt='Lonely niffler']");
    private final SelenideElement searchField = $("input[type='text']");

    @Step("Check friend with name {0} in \"My friends\" list")
    public FriendsPage checkFriendExistsInList(String username) {
        friendNames.find(text(username)).shouldBe(visible);
        return this;
    }

    @Step("Check no friends in \"My friends\" list")
    public FriendsPage checkNoFriendsInList() {
        friendNames.shouldHave(size(0));
        lonelyNifflerImg.shouldBe(visible);
        return this;
    }

    @Step("Check friend request from user '{0}'")
    public FriendsPage checkFriendRequest(String username) {
        friendRequest.find(text(username)).shouldBe(visible);
        return this;
    }

    public FriendsPage searchFriend(String spend) {
        searchField.sendKeys(spend);
        searchField.sendKeys(Keys.ENTER);
        return this;
    }
}