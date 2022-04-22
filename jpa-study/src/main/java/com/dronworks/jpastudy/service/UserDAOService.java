package com.dronworks.jpastudy.service;

import com.dronworks.jpastudy.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository // Interacts with database
@Transactional // Open and close transaction - on class level each method will be transactional
public class UserDAOService {

    /*
        When we persist an object it becomes tracked by persistant context
    */
    @PersistenceContext
    private EntityManager entityManager;

    public long insert(User user) {
        entityManager.persist(user);
        return user.getId();
    }

}
