package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.DisabledByIssue;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.utils.RandomDataUtils.randomUsername;

@WebTest
public class LoginTest {

  private static final Config CFG = Config.getInstance();
  LoginPage loginPage;

  @Test
  @DisabledByIssue("3")
  @User
  void mainPageShouldBeDisplayedAfterSuccessLogin(UserJson user) {
    loginPage.successLogin(user.username(), user.testData().password())
            .assertStatisticsIsVisible()
            .assertHistorySpendingIsVisible();
  }

  @Test
  void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
    LoginPage loginPage = Selenide.open(CFG.frontUrl(), LoginPage.class);
    loginPage.login(randomUsername(), "BAD");
    loginPage.checkError("Bad credentials");
  }
}
