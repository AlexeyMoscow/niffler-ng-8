package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.data.dao.AuthUserDao;
import guru.qa.niffler.data.entity.spend.auth.AuthUserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class AuthUserDaoJdbc implements AuthUserDao {

  private final Connection connection;

  private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  public AuthUserDaoJdbc(Connection connection) {
    this.connection = connection;
  }


  @Override
  public AuthUserEntity create(AuthUserEntity user) {
    try (PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO \"user\" (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
                    "VALUES (?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, user.getUsername());
      ps.setString(2, PASSWORD_ENCODER.encode(user.getPassword()));
      ps.setBoolean(3, user.getEnabled());
      ps.setBoolean(4, user.getAccountNonExpired());
      ps.setBoolean(5, user.getAccountNonLocked());
      ps.setBoolean(6, user.getCredentialsNonExpired());

      ps.executeUpdate();

      final UUID generatedKey;

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          generatedKey = rs.getObject("id", UUID.class);
        } else {
          throw new SQLException("Failed to get the generated key");
        }
      }
      user.setId(generatedKey);
      return user;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<AuthUserEntity> findAuthUserById(UUID id) {
    try (PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM \"user\" WHERE id = ?"
    )) {
      ps.setObject(1, id);
      ps.execute();
      try (ResultSet rs = ps.getResultSet()) {
        if (rs.next()) {
          AuthUserEntity authUserEntity = new AuthUserEntity();
          authUserEntity.setId(rs.getObject("id", UUID.class));
          authUserEntity.setUsername(rs.getString("username"));
          authUserEntity.setPassword(rs.getString("password"));
          authUserEntity.setEnabled(rs.getBoolean("enabled"));
          authUserEntity.setAccountNonExpired(rs.getBoolean("account_non_expired"));
          authUserEntity.setAccountNonLocked(rs.getBoolean("account_non_locked"));
          authUserEntity.setCredentialsNonExpired(rs.getBoolean("credentials_non_expired"));
          return Optional.of(authUserEntity);
        } else {
          return Optional.empty();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
