package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.userAuth.AuthorityEntity;

public interface AuthAuthorityDao {
    void create(AuthorityEntity... user);
}