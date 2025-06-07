package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.test.web.utils.ScreenDiffResult;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProfilePage {
    private final SelenideElement avatar = $(".MuiAvatar-img");
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

    @SneakyThrows
    @Step("Check profile avatar")
    public ProfilePage checkAvatar(BufferedImage expected) {
        Selenide.sleep(3000);
        BufferedImage actual = ImageIO.read(avatar.screenshot());
        assertFalse(new ScreenDiffResult(expected, actual));
        return this;
    }

    @SneakyThrows
    @Step("Upload avatar")
    public ProfilePage uploadAvatar(String path) {
        pictureInput.uploadFromClasspath(path);
        return this;
    }
}
