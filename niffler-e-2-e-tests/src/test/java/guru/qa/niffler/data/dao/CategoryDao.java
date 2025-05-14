package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.spend.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryDao {
    CategoryEntity create(CategoryEntity category);

    public CategoryEntity update(CategoryEntity category);

    Optional<CategoryEntity> findCategoryById(UUID id);

    public Optional<CategoryEntity> findCategoryByUsernameAndCategoryName(String username, String name);

    List<CategoryEntity> findAllByUsername(String username);

    public List<CategoryEntity> findAll();

    void deleteCategory(CategoryEntity category);
}
