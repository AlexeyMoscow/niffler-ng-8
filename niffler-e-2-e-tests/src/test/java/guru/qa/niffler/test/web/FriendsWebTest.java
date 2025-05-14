package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

@WebTest
public class FriendsWebTest {
    private static final Config CFG = Config.getInstance();

    @User(friends = 1)
    @Test
    void friendShouldBePresentInFriendsTable(UserJson user) {
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened()
                .goToFriendsList()
                .searchFriend(user.testData().friends().get(0).username())
                .checkFriendExistsInList(user.testData().friends().get(0).username());
    }

    @User()
    @Test
    void friendsTableShouldBeEmptyForNewUser(UserJson user) {
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened()
                .goToFriendsList()
                .checkNoFriendsInList();
    }

    @User(
            incomeInvitations = 1
    )
    @Test
    void incomeInvitationBePresentInFriendsTable(UserJson user) {
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(),user.testData().password())
                .checkMainPageIsOpened()
                .goToFriendsList()
                .checkFriendRequest(user.testData().incomeInvitations().get(0).username());
    }

    @User(
            outcomeInvitations = 1
    )
    @Test
    void outcomeInvitationBePresentInAllPeoplesTable(UserJson user) {
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened()
                .goToAllPeopleList()
                .checkOutcomeRequestToUser(user.testData().outcomeInvitations().get(0).username());
    }
}