/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.dkopic2.entity.Log;
import org.foi.nwtis.dkopic2.entity.Log_;

/**
 *
 * @author domagoj
 */
@Stateless
public class LogFacade extends AbstractFacade<Log> {

    @PersistenceContext(unitName = "dkopic2_aplikacija_2_1PU")
    private EntityManager entityManager;
    private CriteriaQuery criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Log> root;
    private List<Predicate> predicates;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public LogFacade() {
        super(Log.class);
    }
    
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Log.class);
        root = criteriaQuery.from(Log.class);
        predicates = new ArrayList<>();
    }
    
    public void filterByTime(String startTime, String endTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date start = format.parse(startTime);
        Date end = format.parse(endTime);
        
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Log_.vrijeme), start));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Log_.vrijeme), end));
    }
    
    public void filterByStart(String startTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date start = format.parse(startTime);
        
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Log_.vrijeme), start));
    }
    
    public void filterByEnd(String endTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date end = format.parse(endTime);
        
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Log_.vrijeme), end));
    }
    
    public void filterByUser(String user) {
        predicates.add(criteriaBuilder.equal(root.get(Log_.korisnik), user));
    }
    
    public void filterByIp(String ip) {
        predicates.add(criteriaBuilder.equal(root.get(Log_.ipadresa), ip));
    }
    
    public List<Log> getResult() {
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    
    public List<Log> getAddresses(String username) {
        init();
        predicates.add(criteriaBuilder.equal(root.get(Log_.korisnik), username));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    
}
