package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class UserProfilePage {
    private final ElementsCollection
            activeCategories = $$(By.xpath("//div[contains(@class, 'MuiChip-colorPrimary')]/parent::div")),
            archivedCategories = $$(By.xpath("//div[contains(@class, 'MuiChip-colorDefault')]/parent::div"));

    private final SelenideElement archiveButton = $x("//button[text()='Archive']");
    private final SelenideElement restoreButton = $x("//button[text()='Unarchive']");

    private final SelenideElement showArchivedCategoriesToggle = $(By.xpath("//input[contains(@class, 'MuiSwitch-input')]"));


    public void verifyActiveCategoryInList(String categoryName) {
        activeCategories.find(text(categoryName)).shouldBe(visible);
    }

    public void verifyArchivedCategoryInList(String categoryName) {
        archivedCategories.find(text(categoryName)).shouldBe(visible);
    }

    public UserProfilePage switchArchivedCategoriesToggle() {
        showArchivedCategoriesToggle.click();
        return this;
    }

    public UserProfilePage archiveCategory(String categoryName) {
        activeCategories.filter(text(categoryName)).first().parent().$(".MuiIconButton-sizeMedium[aria-label='Archive category']").click();
        return this;
    }

    public UserProfilePage confirmArchiveCategory() {
        archiveButton.click();
        return this;
    }

    public UserProfilePage confirmRestoreCategory() {
        restoreButton.click();
        return this;
    }

    public UserProfilePage restoreCategory(String categoryName) {
        archivedCategories.filter(text(categoryName)).first().parent().$(".MuiIconButton-sizeMedium[aria-label='Unarchive category']").click();
        return this;
    }
}
