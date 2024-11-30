package org.example.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryTestImpl implements UserRepository {

    private final Map<String, User> userDatabase = new ConcurrentHashMap<>(); //userName - User

    @Override
    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(userDatabase.get(userName));
    }

    @Override
    public User save(User user) {
        UUID id = UUID.randomUUID();

        User savedUser = new User(
                id.toString(),
                user.userName(),
                user.password()
        );


        userDatabase.put(savedUser .userName(), savedUser);
        return savedUser;
    }
}
