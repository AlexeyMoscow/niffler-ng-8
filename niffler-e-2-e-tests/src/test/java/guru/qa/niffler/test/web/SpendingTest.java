package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.UserProfilePage;
import guru.qa.niffler.utils.MenuItems;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class SpendingTest {

  private static final Config CFG = Config.getInstance();

  @User(
      username = "alex",
      categories = @Category(
              archived = false
      ),
      spendings = @Spend(
              category = "Обучение",
              description = "Обучение Niffler 2.0",
              amount = 89000.00
      )
  )
  @Test
  void spendingDescriptionShouldBeUpdatedByTableAction(SpendJson spend) {
    final String newDescription = "Обучение Niffler NG";

    Selenide.open(CFG.frontUrl(), LoginPage.class)
        .doLogin("alex", "123")
        .editSpending(spend.description())
        .editDescription(newDescription);

    new MainPage().checkThatTableContains(newDescription);
  }

  @User(
          username = "alex",
          categories = @Category(
                  archived = false
          ),
          spendings = @Spend(
                  category = "Обучение",
                  description = "Обучение Niffler 2.0",
                  amount = 89000.00
          )
  )
  @Test
  void categoryDescriptionShouldBeChangedFromTable(SpendJson spend) {
    final String newDescription = "Обучение Niffler Next Generation";

    Selenide.open(CFG.frontUrl(), LoginPage.class)
            .doLogin("alex", "123")
            .editSpending(spend.description())
            .editDescription(newDescription);

    new MainPage().checkThatTableContains(newDescription);
  }

  @User(
          username = "alex",
          categories = @Category(
                  archived = false
          )
  )
  @Test
  void archivedCategoryShouldBePresentedInList(CategoryJson category) {
    Selenide.open(CFG.frontUrl(), LoginPage.class)
            .doLogin("alex", "123")
            .openMenu()
            .clickMenuItem(MenuItems.PROFILE);
    new UserProfilePage()
            .archiveCategory(category.name())
            .confirmArchiveCategory()
            .switchArchivedCategoriesToggle()
            .verifyArchivedCategoryInList(category.name());
  }

  @User(
          username = "alex",
          categories = @Category(
                  archived = true
          )
  )
  @Test
  void activeCategoryShouldBePresentedInListAfterRestored(CategoryJson category) {
    Selenide.open(CFG.frontUrl(), LoginPage.class)
            .doLogin("alex", "123")
            .openMenu()
            .clickMenuItem(MenuItems.PROFILE);
    new UserProfilePage()
            .switchArchivedCategoriesToggle()
            .restoreCategory(category.name())
            .confirmRestoreCategory()
            .verifyActiveCategoryInList(category.name());
  }
}
