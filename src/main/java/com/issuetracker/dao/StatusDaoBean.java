/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.issuetracker.dao;

import com.issuetracker.dao.api.StatusDao;
import com.issuetracker.model.Project;
import com.issuetracker.model.Status;
import com.issuetracker.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author mgottval
 */
@Stateless
public class StatusDaoBean implements StatusDao{

    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder qb;
    
    @Override
    public void insert(Status status) {
        em.persist(status);
    }

    @Override
    public List<Status> getStatuses() {
        qb = em.getCriteriaBuilder();
        CriteriaQuery<Status> c = qb.createQuery(Status.class);
        Root<Status> u = c.from(Status.class);
        TypedQuery<Status> pQuery = em.createQuery(c);
        List<Status> results = pQuery.getResultList();
        if (results != null && !results.isEmpty()) {
            return results;
        } else {
            return new ArrayList<Status>();
        }
    }

    @Override
    public void remove(Status status) {
        em.remove(em.contains(status)? status : em.merge(status));
    }

    @Override
    public Status getStatusById(Long id) {
        qb = em.getCriteriaBuilder();
        CriteriaQuery<Status> statusQuery = qb.createQuery(Status.class);
        Root<Status> p = statusQuery.from(Status.class);
        Predicate pCondition = qb.equal(p.get("id"), id);
        statusQuery.where(pCondition);
        TypedQuery<Status> pQuery = em.createQuery(statusQuery);
        List<Status> statusResults = pQuery.getResultList();
        if (statusResults != null && !statusResults.isEmpty()) {
            return statusResults.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Status getStatusByName(String name) {
        qb = em.getCriteriaBuilder();
        CriteriaQuery<Status> statusQuery = qb.createQuery(Status.class);
        Root<Status> p = statusQuery.from(Status.class);
        Predicate pCondition = qb.equal(p.get("name"), name);
        statusQuery.where(pCondition);
        TypedQuery<Status> pQuery = em.createQuery(statusQuery);
        List<Status> statusResults = pQuery.getResultList();
        if (statusResults != null && !statusResults.isEmpty()) {
            return statusResults.get(0);
        } else {
            return null;
        }
    }
    
}
