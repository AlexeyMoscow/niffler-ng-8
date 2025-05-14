package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {
    private final SelenideElement avatar = $("input[type='file']").parent().$("#image_input");
    private final SelenideElement pictureInput = $("input[type='file']");
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement nameInput = $("input[name='name']");
    private final SelenideElement saveChangesBtn = $("button[type='submit']");
    private final SelenideElement categoryInput = $("input[name='category']");
    private final SelenideElement showArchivedSwitch = $("input[type='checkbox']");
    private final ElementsCollection categories = $$(".MuiChip-label");

    @Step("Click on archived category switch")
    public ProfilePage clickOnArchivedCategorySwitch() {
        showArchivedSwitch.click();
        return new ProfilePage();
    }

    @Step("Check category '{0}' exists")
    public ProfilePage checkCategoryExists(String category) {
        categories.find(text(category)).shouldBe(visible);
        return new ProfilePage();
    }
}
