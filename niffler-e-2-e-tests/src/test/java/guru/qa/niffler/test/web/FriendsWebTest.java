package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.MenuItems;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType.Type.*;

@ExtendWith({UsersQueueExtension.class, BrowserExtension.class})
public class FriendsWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void friendShouldBeDisplayedInFriendsTable(@UsersQueueExtension.UserType(WITH_FRIEND) UsersQueueExtension.StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.password())
                .openMenu()
                .clickMenuItem(MenuItems.FRIENDS);

        new FriendsPage()
                .setValueIntoSearch(user.friend())
                .verifyNameInTableByType("friends",user.friend());
    }

    @Test
    void friendsTableShouldBeEmptyForNewUser(@UsersQueueExtension.UserType(EMPTY) UsersQueueExtension.StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.password())
                .openMenu()
                .clickMenuItem(MenuItems.FRIENDS);

        new FriendsPage()
                .verifyFriendsTableEmpty();
    }

    @Test
    void incomeInvitationShouldBePresentedInFriendsTable(@UsersQueueExtension.UserType(WITH_INCOME_REQUEST) UsersQueueExtension.StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.password())
                .openMenu()
                .clickMenuItem(MenuItems.FRIENDS);

        new FriendsPage()
                .verifyNameInTableByType("requests", user.income_request());
    }

    @Test
    void outcomeInvitationShouldBePresentedInFriendsTable(@UsersQueueExtension.UserType(WITH_OUTCOME_REQUEST) UsersQueueExtension.StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user.username(), user.password())
                .openMenu()
                .clickMenuItem(MenuItems.FRIENDS);

        new FriendsPage()
                .switchTab()
                .setValueIntoSearch(user.outcome_request())
                .verifyUserPresentInAllPeopleTableAndCheckStatus(user.outcome_request(), "Waiting");
    }
}
