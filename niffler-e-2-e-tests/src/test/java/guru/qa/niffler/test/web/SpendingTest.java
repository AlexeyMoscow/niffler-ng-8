package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.condition.Color;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.ScreenShotTest;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.Bubble;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.List;

@WebTest
public class SpendingTest {

    private static final Config CFG = Config.getInstance();

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

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .editSpending(user.testData().spendings().getFirst().description())
                .editDescription(newDescription);

        new MainPage().checkThatTableContains(newDescription);
    }

    @User(
            spendings = {@Spending(
                    category = "Еда",
                    description = "Сырки Б Ю Александров",
                    amount = 1250.00,
                    currency = CurrencyValues.RUB
            ), @Spending(
                    category = "Хобби",
                    description = "Казино",
                    amount = 777.00,
                    currency = CurrencyValues.RUB
            )})
    @ScreenShotTest(value = "img/expected-stat.png")
    void checkStatComponentTest(UserJson user, BufferedImage expected) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkStatBubblesInAnyOrder(new Bubble(Color.green, "Еда 1250 ₽"), new Bubble(Color.yellow, "Хобби 777 ₽"))
                .checkStatisticDiagram(expected);
    }
@User(
        spendings = {
                @Spending(
                        category = "Авто",
                        description = "ТО",
                        amount = 777.0,
                        currency = CurrencyValues.RUB
                )
        }
)
    @ScreenShotTest("img/expected-stat-edited")
    void checkStatComponentAfterEditingTest(UserJson user, BufferedImage expected) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .editSpending(user.testData().spendings().getFirst().description())
                .editSum("777")
                .checkStatisticDiagramInfo(List.of("Авто 777 ₽"))
                .checkStatisticDiagram(expected);
    }

    @User(
            spendings = @Spending(
                    category = "Учеба",
                    description = "CSS",
                    amount = 1000.00,
                    currency = CurrencyValues.RUB
            )
    )
    @ScreenShotTest("img/expected-stat-delete")
    void checkStatComponentAfterDeletingSpendTest(UserJson user, BufferedImage expected) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .deleteSpending(user.testData().spendings().getFirst().description())
                .checkStatisticDiagram(expected);
    }

    @User(
            categories = {
                    @Category(
                            name = "Спорт",
                            archived = true
                    ),
                    @Category(name = "Авито")
            },
            spendings = {
                    @Spending(
                            category = "Авито",
                            description = "Альбом",
                            amount = 1500.00,
                            currency = CurrencyValues.RUB
                    ),
                    @Spending(
                            category = "Развлечения",
                            description = "Кино",
                            amount = 5000.00,
                            currency = CurrencyValues.RUB
                    )
            })
    @ScreenShotTest(value = "img/expected-stat-archive.png")
    void checkStatComponentWithArchiveSpendTest(UserJson user, BufferedImage expected) {

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkStatisticDiagramInfo(List.of("Archived 1500 ₽", "Кино 5000 ₽"))
                .checkStatisticDiagram(expected);
    }
}
