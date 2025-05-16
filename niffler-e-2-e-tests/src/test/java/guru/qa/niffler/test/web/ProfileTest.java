package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.ScreenShotTest;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

@WebTest
public class ProfileTest {

    @User(
            categories = @Category(
                    archived = true
            )
    )
    @Test
    void archivedCategoryShouldPresentInCategoriesList(UserJson user) {
        final CategoryJson archivedCategory = user.testData().categories().getFirst();
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened()
                .goToProfile()
                .clickOnArchivedCategorySwitch()
                .checkCategoryExists(archivedCategory.name());
    }

    @User(
            categories = @Category()
    )
    @Test
    void activeCategoryShouldPresentInCategoriesList(UserJson user) {
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened()
                .goToProfile()
                .checkCategoryExists(user.testData().categories().getFirst().name());
    }

    @User
    @ScreenShotTest(value = "img/ava-expected.png")
    void checkProfileImageTest(UserJson user, BufferedImage expectedProfileImage) {
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened()
                .goToProfile()
                .uploadAvatar("img/ava.png")
                .checkAvatar(expectedProfileImage);
    }
}
