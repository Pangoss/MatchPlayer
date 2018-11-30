package fr.fengdavid.matchplayer.repositories;

import fr.fengdavid.matchplayer.entities.User;


public interface UserRepository {
    void save(User user) throws UserAlreadyExistsException;

    User fetchByEmail(String email);

    void update(User user);
}
