package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.page.RegisterPage;
import guru.qa.niffler.test.web.utils.RandomDataUtils;
import org.junit.jupiter.api.Test;

@WebTest
public class RegistrationTest {


    @Test
    void shouldRegisterNewUser() {
        String username = RandomDataUtils.randomUsername();
        String password = RandomDataUtils.randomPassword();
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .doRegister(username, password, password)
                .doLogin(username, password)
                .checkMainPageIsOpened();
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        String username = "nevermindia";
        String password = RandomDataUtils.randomPassword();
        String errorText = "Username `" + username + "` already exists";
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .setUserName(username)
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration()
                .checkErrorMessage(errorText);
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        String username = RandomDataUtils.randomUsername();
        String password = RandomDataUtils.randomPassword();
        String submitPassword = RandomDataUtils.randomPassword();
        String errorText = "Passwords should be equal";
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .setUserName(username)
                .setPassword(password)
                .setPasswordSubmit(submitPassword)
                .submitRegistration()
                .checkErrorMessage(errorText);
    }
}