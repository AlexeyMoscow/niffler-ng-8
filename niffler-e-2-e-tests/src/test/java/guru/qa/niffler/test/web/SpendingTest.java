package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideDriver;
import guru.qa.niffler.condition.Color;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.ScreenShotTest;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.model.Bubble;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.test.web.utils.SelenideUtils;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@WebTest
public class SpendingTest {

    private static final Config CFG = Config.getInstance();
    private final SelenideDriver driver = new SelenideDriver(SelenideUtils.chromeConfig);

    @User(
            spendings = @Spending(
                    category = "Еда",
                    description = "Сырки Б Ю Александров",
                    amount = 1250.00,
                    currency = CurrencyValues.RUB
            ))

    @Test
    void spendingDescriptionShouldBeUpdatedByTableAction(UserJson user) {
        final String newDescription = "Обучение Niffler NG";

        driver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .editSpending(user.testData().spendings().getFirst().description())
                .editDescription(newDescription)
                .checkThatTableContains(newDescription);
    }

    @User(
            spendings = {@Spending(
                    category = "Еда",
                    description = "Сырки Б Ю Александров",
                    amount = 1250.00,
                    currency = CurrencyValues.RUB
            ), @Spending(
                    category = "Обучение",
                    description = "Английский язык",
                    amount = 1300.00,
                    currency = CurrencyValues.RUB
            )})
    @ScreenShotTest(value = "img/expected-stat.png")
    void checkStatComponentTest(UserJson user, BufferedImage expected) {
        driver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                //.checkStatisticDiagramInfo(List.of("Еда 1250 ₽"))
                .checkStatBubblesInAnyOrder(new Bubble(Color.green, "Еда 1250 ₽"), new Bubble(Color.yellow, "Обучение 1300 ₽"))
                .checkStatisticDiagram(expected);
    }

    @User(
            spendings = {@Spending(
                    category = "Еда",
                    description = "Сырки Б Ю Александров",
                    amount = 1250.00,
                    currency = CurrencyValues.RUB
            ), @Spending(
                    category = "Обучение",
                    description = "Английский язык",
                    amount = 1300.00,
                    currency = CurrencyValues.RUB
            )})
    @Test
    void checkSpendingTableTest(UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkSpendTable(user.testData().spendings().toArray(SpendJson[]::new));
    }

    @User(
            spendings = {
                    @Spending(
                            category = "Медицина",
                            description = "Аспирин",
                            amount = 150.00,
                            currency = CurrencyValues.RUB
                    )
            })
    @ScreenShotTest(value = "img/expected-stat-edit.png")
    void checkStatComponentAfterEditingTest(UserJson user, BufferedImage expected) throws IOException {
        driver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .editSpending(user.testData().spendings().getFirst().description())
                .editSum("175")
                .checkStatisticDiagramInfo(List.of("Медицина 175 ₽"))
                .checkStatisticDiagram(expected);

    }

    @User(
            spendings = @Spending(
                    category = "Ремонт",
                    description = "Обои",
                    amount = 15250.00,
                    currency = CurrencyValues.RUB
            ))
    @ScreenShotTest(value = "img/expected-stat-delete.png")
    void checkStatComponentAfterDeletingSpendTest(UserJson user, BufferedImage expected) throws IOException {

        driver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .deleteSpending(user.testData().spendings().getFirst().description())
                .checkStatisticDiagram(expected);
    }

    @User(
            categories = {
                    @Category(
                            name = "Развлечения",
                            archived = true
                    ),
                    @Category(name = "Ремонт")
            },
            spendings = {
                    @Spending(
                            category = "Ремонт",
                            description = "Обои",
                            amount = 15250.00,
                            currency = CurrencyValues.RUB
                    ),
                    @Spending(
                            category = "Развлечения",
                            description = "Боулинг",
                            amount = 1050.00,
                            currency = CurrencyValues.RUB
                    )
            })
    @ScreenShotTest(value = "img/expected-stat-archived.png")
    void checkStatComponentWithArchiveSpendTest(UserJson user, BufferedImage expected) throws IOException {

        driver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkStatisticDiagramInfo(List.of("Archived 15250 ₽", "Боулинг 1050 ₽"))
                .checkStatisticDiagram(expected);
    }
}
