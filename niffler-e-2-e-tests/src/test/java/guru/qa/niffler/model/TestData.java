package guru.qa.niffler.model;

import lombok.Builder;

import java.util.List;

@Builder
public record TestData(
        String password,
        List<CategoryJson> categories,
        List<SpendJson> spendings,
        List<UserJson> friendshipRequests,
        List<UserJson> friendshipAddressees,
        List<UserJson> friends) {
}
