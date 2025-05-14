package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository {
    UserEntity create(UserEntity user);

    Optional<UserEntity> findById(UUID id);

    Optional<UserEntity> findByUsername(String username);

    UserEntity update(UserEntity user);

    void addFriend(UserEntity requester, UserEntity addressee);

    void addOutcomeRequest(UserEntity requester, UserEntity addressee);

    void addIncomeRequest(UserEntity requester, UserEntity addressee);

    void remove(UserEntity user);
}
