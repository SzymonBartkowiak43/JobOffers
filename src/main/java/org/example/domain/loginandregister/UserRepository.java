package org.example.domain.loginandregister;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUserName(String userName);

    User save(User user);

}
