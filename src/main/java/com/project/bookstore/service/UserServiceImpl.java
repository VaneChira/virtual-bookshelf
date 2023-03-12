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
        final var result = userRepository.findById(id);
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
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Did not find user id - " + id, User.class.getSimpleName());
        }
    }

    @Override
    public Map<String, Integer> getMostReadGenres(Long userId) { // native query = query tip SQL, query simplu in context hibernate = query tip hibernate
        List<Object[]> results = entityManager.createNativeQuery(""" 
                        SELECT COUNT(*) as count_books, g.type from genres g
                        inner join genres_in_books gib on
                        g.id = gib.genre_id
                        inner join user_book ub on
                        ub.book_id = gib.book_id
                        where ub.user_id = :userId and ub.book_state = 3
                        group by g.type
                        ORDER BY count_books DESC
                        LIMIT 5;""")
                .setParameter("userId", userId).getResultList();
        final var countByGenrePopularity = new HashMap<String, Integer>();

        for (Object[] record : results) { // iterez manual rezultatul query-ului (count books si genre type) pentru a creea map-ul
            countByGenrePopularity.put(String.valueOf(record[1]), Integer.valueOf(String.valueOf(record[0])));
            //.put(key, value)
            //record[1] = a 2-a coloana
            // record[0] = prima coloana
        }
        return countByGenrePopularity;
    }

    @Override
    public Map<User, Integer> getTopUsers() {
        List<Object[]> results = entityManager.createNativeQuery("""
                SELECT   u.id, u.name, COUNT(*) as count_books from user u
                                        inner join user_book ub on
                                        ub.user_id = u.id
                                        where ub.book_state = 3
                                        group by ub.user_id
                                        ORDER BY count_books DESC;""").getResultList();
        final var countByBooksReadPopularity = new HashMap<User, Integer>();

        for (Object[] record : results) {
            final var user = new User();
            user.setId(Long.valueOf(String.valueOf(record[0])));
            user.setName(String.valueOf(record[1]));
            countByBooksReadPopularity.put(user, Integer.valueOf(String.valueOf(record[2])));
        }
        return countByBooksReadPopularity;
    }

    @Override
    public User updateUser(User user, Long id) {
        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("Did not find user id - " + id, User.class.getSimpleName());
        }
    }
}
