package com.project.bookstore.service;

import com.project.bookstore.exception.ResourceNotFoundException;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> result = userRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Did not find user id - " + id, User.class.getSimpleName());
        }
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Did not find user id - " + id, User.class.getSimpleName());
        }
    }

    @Override
    public Map<String, Integer> getMostReadGenres(Long userId) {
        List<Object[]> results = entityManager.createNativeQuery("""
                        SELECT COUNT(*) as count_books, g.type from genres g
                        inner join genres_in_books gib on
                        g.id = gib.genre_id
                        inner join user_book ub on
                        ub.book_id = gib.book_id
                        where ub.user_id = :userId and ub.book_state = 3
                        group by g.description
                        ORDER BY count_books DESC
                        LIMIT 5;""")
                .setParameter("userId", userId).getResultList();
        Map<String, Integer> countByGenrePopularity = new HashMap<>();

        for (Object[] record : results) {
            countByGenrePopularity.put(String.valueOf(record[1]), Integer.valueOf(String.valueOf(record[0])));
        }

        return countByGenrePopularity;
    }

    @Override
    public User updateUser(User user, Long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            User updatedUser = user;
            updatedUser.setId(id);
            return userRepository.save(updatedUser);
        } else {
            throw new ResourceNotFoundException("Did not find user id - " + id, User.class.getSimpleName());
        }
    }
}
