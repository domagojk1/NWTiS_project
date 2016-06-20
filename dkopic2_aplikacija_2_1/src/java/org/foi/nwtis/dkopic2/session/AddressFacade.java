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
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.dkopic2.entity.Address;
import org.foi.nwtis.dkopic2.entity.Address_;
import org.foi.nwtis.dkopic2.entity.User;

/**
 *
 * @author domagoj
 */
@Stateless
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "dkopic2_aplikacija_2_1PU")
    private EntityManager entityManager;
    private CriteriaQuery<Address> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Address> root;
    private List<Predicate> predicates;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public AddressFacade() {
        super(Address.class);
    }
    
    private void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Address.class);
        root = criteriaQuery.from(Address.class);
        predicates = new ArrayList<>();
    }
    
    public List<Address> getAddresses(User user) {
        init();
        predicates.add(criteriaBuilder.equal(root.get(Address_.korisnik), user));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
