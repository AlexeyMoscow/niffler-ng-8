package guru.qa.niffler.service.impl;

import guru.qa.niffler.api.UsersApi;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.UsersClient;
import io.qameta.allure.Step;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static guru.qa.niffler.test.web.utils.RandomDataUtils.randomUsername;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersApiClient implements UsersClient {
    private static final Config CFG = Config.getInstance();

    private final OkHttpClient client = new OkHttpClient.Builder().
            addNetworkInterceptor(new AllureOkHttp3()
                    .setRequestTemplate("my-http-request.ftl")
                    .setResponseTemplate("my-http-response.ftl"))
            .build();
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CFG.userdataUrl())
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final UsersApi usersApi = retrofit.create(UsersApi.class);
    @Override
    @Step("Create user using API")
    public UserJson createUser(UserJson user) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    @Step("Create user using API")
    public UserJson createUser(String username, String password) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public void addFriend(UserJson user, int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                final Response<UserJson> response;
                final UserJson newUser;
                try {
                    newUser = createUser(randomUsername(), "12345");
                    usersApi.sendInvitation(
                            user.username(),
                            newUser.username()
                    ).execute();
                    response = usersApi.acceptInvitation(user.username(), newUser.username())
                            .execute();
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
                assertEquals(200, response.code());
            }}

    }

    @Override
    public void addIncomeInvitation(UserJson targetUser, int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                final Response<UserJson> response;
                final UserJson newUser;
                try {
                    newUser = createUser(randomUsername(), "12345");

                    response = usersApi.sendInvitation(
                            newUser.username(),
                            targetUser.username()
                    ).execute();
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
                assertEquals(200, response.code());
            }
        }
    }

    @Override
    public void addOutcomeInvitation(UserJson targetUser, int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                final Response<UserJson> response;
                final UserJson newUser;
                try {
                    newUser = createUser(randomUsername(), "12345");

                    response = usersApi.sendInvitation(
                            targetUser.username(),
                            newUser.username()
                    ).execute();
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
                assertEquals(200, response.code());
            }
        }
    }
}
