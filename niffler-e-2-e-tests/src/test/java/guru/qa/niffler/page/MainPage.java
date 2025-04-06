package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.utils.MenuItems;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

  private final SelenideElement spendingTable = $("#spendings");
  private final SelenideElement statisticsFigure = $("#stat");
  private final SelenideElement headerBlock = $("#root header");
  private final SelenideElement avatar = $("svg[data-testid='PersonIcon']");
  private final SelenideElement menu = $("ul[role='menu']");
  private final ElementsCollection menuItems = $$("li[role='menuitem'] a");
  private final ElementsCollection tableRows = $$("#spendings tbody tr");

  public EditSpendingPage editSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription))
        .$$("td")
        .get(5)
        .click();
    return new EditSpendingPage();
  }

  public void checkThatTableContains(String spendingDescription) {
    tableRows.find(text(spendingDescription))
        .should(visible);
  }

  public void verifyMainPageOpened() {
    spendingTable.shouldBe(visible);
    statisticsFigure.shouldBe(visible);
    headerBlock.shouldBe(visible);
  }

  public MainPage openMenu() {
    headerBlock.shouldBe(visible);
    avatar.click();
    return this;
  }

  public void clickMenuItem(MenuItems menuItem) {
    menu.shouldBe(visible);
    menuItems.find(text(menuItem.toString())).click();
  }

}
