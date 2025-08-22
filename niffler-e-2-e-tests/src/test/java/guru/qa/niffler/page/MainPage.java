package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.condition.Color;
import guru.qa.niffler.model.Bubble;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.test.web.utils.ScreenDiffResult;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.Keys;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static guru.qa.niffler.condition.SpendConditions.spend;
import static guru.qa.niffler.condition.StatConditions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainPage {

    private final ElementsCollection tableRows = $$("#spendings tbody tr");
    private final ElementsCollection headerElements = $$(".MuiTypography-h5");
    private final SelenideElement contextMenuInAvatarBtn = $("button[aria-label='Menu']");
    private final ElementsCollection contextMenuElements = $$(".MuiList-padding li");
    private final SelenideElement searchField = $("input[type='text']");
    private final SelenideElement diagramElement = $("canvas[role='img']");
    private final SelenideElement profileImage = $(".MuiAvatar-img");
    private final ElementsCollection statisticCells = $$("#legend-container li");
    private final SelenideElement deleteBtn = $("#delete");
    private final SelenideElement dialogWindow = $("div[role='dialog']");


    @Step("Edit spending with description {0}")
    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription))
                .$$("td")
                .get(5)
                .click();
        return new EditSpendingPage();
    }

    @Step("Delete spending with description {0}")
    public MainPage deleteSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription))
                .$$("td")
                .get(0)
                .click();
        deleteBtn.click();
        dialogWindow.$(byText("Delete")).click();
        return new MainPage();
    }

    @Step("Check that table contains description {0}")
    public MainPage checkThatTableContains(String spendingDescription) {
        tableRows.find(text(spendingDescription))
                .should(visible);
        return this;
    }

    @Step("Check Main page is opened")
    public MainPage checkMainPageIsOpened() {
        headerElements.find(text("Statistics")).shouldBe(visible);
        headerElements.find(text("History of Spendings")).shouldBe(visible);
        headerElements.find(text("Niffler")).shouldBe(visible);
        return this;
    }

    @SneakyThrows
    @Step("Check statistic diagram")
    public MainPage checkStatisticDiagram(BufferedImage expected) {
        Selenide.sleep(3000);
        BufferedImage actual = ImageIO.read(diagramElement.screenshot());
        assertFalse(new ScreenDiffResult(expected, actual), "Screen comparison failure");
        return this;
    }

    @Step("Check statistic cell")
    public MainPage checkStatisticDiagramInfo(List<String> spendInfo) {
        for (String info : spendInfo){
            statisticCells.findBy(text(info)).shouldBe(visible);
        }
        return this;
    }

    @Step("Check statistic bubble color")
    public MainPage checkBubbles(Color... expectedColor) {
        statisticCells.shouldHave(color(expectedColor));
        return this;
    }

    @Step("Check stat bubbles")
    public MainPage checkStatBubbles(Bubble... expectedBubbles) {
        statisticCells.shouldHave(bubble(expectedBubbles));
        return this;
    }

    @Step("Check stat bubbles in any order")
    public MainPage checkStatBubblesInAnyOrder(Bubble... expectedBubbles) {
        statisticCells.shouldHave(bubblesInAnyOrder(expectedBubbles));
        return this;
    }

    @Step("Check stat bubbles contains")
    public MainPage checkStatBubblesContains(Bubble... expectedBubbles) {
        statisticCells.shouldHave(bubblesContains(expectedBubbles));
        return this;
    }

    @Step("Check spending table")
    public MainPage checkSpendTable(SpendJson... expectedSpends) {
        tableRows.shouldHave(spend(expectedSpends));
        return this;
    }

    @Step("Go to Profile")
    public ProfilePage goToProfile() {
        contextMenuInAvatarBtn.click();
        contextMenuElements.find(text("Profile")).click();
        return new ProfilePage();
    }

    @Step("Go to Friends")
    public FriendsPage goToFriendsList() {
        contextMenuInAvatarBtn.click();
        contextMenuElements.find(text("Friends")).click();
        return new FriendsPage();
    }

    @Step("Go to All People")
    public AllPeoplePage goToAllPeopleList() {
        contextMenuInAvatarBtn.click();
        contextMenuElements.find(text("All People")).click();
        return new AllPeoplePage();
    }

    public MainPage search(String spend) {
        searchField.sendKeys(spend);
        searchField.sendKeys(Keys.ENTER);
        return this;
    }
}
