/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.dkopic2.entity.User;
import org.foi.nwtis.dkopic2.entity.User_;

/**
 *
 * @author domagoj
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "dkopic2_aplikacija_2_1PU")
    private EntityManager entityManager;
    private CriteriaQuery<User> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<User> root;
    private List<Predicate> predicates;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public UserFacade() {
        super(User.class);
    }
    
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(User.class);
        root = criteriaQuery.from(User.class);
        predicates = new ArrayList<>();
    }
    
    public User fetchUser(String username, String pass) {
        init();
        
        predicates.add(criteriaBuilder.equal(root.get(User_.username), username));
        predicates.add(criteriaBuilder.equal(root.get(User_.password), pass));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        
        try
        {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
    }
    
    public User fetchUser(String username) {
        init();
        
        predicates.add(criteriaBuilder.equal(root.get(User_.username), username));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        
        try
        {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
    }
    
    public User fetchUser(int id) {
        init();
        
        predicates.add(criteriaBuilder.equal(root.get(User_.id), id));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        
        try
        {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
    }
    
    public User fetchUserMail(String mail) {
        init();
        
        predicates.add(criteriaBuilder.equal(root.get(User_.mail), mail));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        
        try
        {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
    }
    
    public List<User> fetchPending() {
        init();
        List<User> pending = null;
        
        predicates.add(criteriaBuilder.equal(root.get(User_.status), 0));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        pending = entityManager.createQuery(criteriaQuery).getResultList();
        
        return pending;
    }
    
    public List<User> fetchUsers() {
        init();
        List<User> users = null;
        
        predicates.add(criteriaBuilder.equal(root.get(User_.uloga), "Korisnik"));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        users = entityManager.createQuery(criteriaQuery).getResultList();
        
        return users;
    }
}
