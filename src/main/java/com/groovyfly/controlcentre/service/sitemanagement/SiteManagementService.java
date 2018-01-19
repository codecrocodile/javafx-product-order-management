/*
 * Copyright GroovyFly.com SiteManagementService.java
 */
package com.groovyfly.controlcentre.service.sitemanagement;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groovyfly.controlcentre.client.sitemanagement.newsfeed.NewsFeedEntrySearchParam;
import com.groovyfly.controlcentre.client.sitemanagement.pages.WebPageSearchParam;
import com.groovyfly.controlcentre.structure.sitemanagement.NewsFeedEntry;
import com.groovyfly.controlcentre.structure.sitemanagement.NewsFeedEntry_;
import com.groovyfly.controlcentre.structure.sitemanagement.WebPage;

/**
 * @author Chris Hatton
 */
@Service("SiteManagementService") // @Service annotation is to identify that it’s a Spring component that provides business services to another layer 
@Repository // @Repository annotation indicates that the class contains data access logic and instructs Spring to translate the vendor-specific exceptions to Spring’s DataAccessException hierarchy
@Transactional
public class SiteManagementService implements SiteManagementServiceIF { 
		
	/* note: it seems that is you implement and interface the class you inject into must be of the type of the interface
	   and also that the annotations must be in the implementing class */
	
    @PersistenceContext // you can also add the unit name if there is more than one persistence context
    private EntityManager em;

	/* 
	 * @see com.groovyfly.controlcentre.service.SiteManagementIF#getNewsFeedEntries(com.groovyfly.controlcentre.client.sitemanagement.NewsFeedEntrySearchParam)
	 */
    @Transactional(readOnly=true)
	public List<NewsFeedEntry> getNewsFeedEntries(NewsFeedEntrySearchParam newsFeedEntrySearchParam) throws Exception {
    	/* 
    	 * Criteria Query Using the JPA 2 Criteria API
    	 * 
    	 * Note that we needed meta model classes that correspond to the entity classes. You can write them yourself
    	 * or in this case we have them auto generated (See Pro Spring 3 book, Chapter 10, Database operations with JPA).
    	 * 
    	 * We are using Hibernate Metamodel Generator to generate these classes with the help of Eclipse to generate the 
    	 * classes each time the project is built. 
    	 * 
    	 */
    	
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<NewsFeedEntry> criteriaQuery = cb.createQuery(NewsFeedEntry.class);
        Root<NewsFeedEntry> newsFeedEntryRoot = criteriaQuery.from(NewsFeedEntry.class);
        
        criteriaQuery.select(newsFeedEntryRoot).distinct(true);
        
        Predicate criteria = cb.conjunction();
        
        if (newsFeedEntrySearchParam.getAfterDate() != null) {
            Predicate p = cb.greaterThanOrEqualTo(newsFeedEntryRoot.get(NewsFeedEntry_.publicationDate), newsFeedEntrySearchParam.getAfterDate());
            criteria = cb.and(criteria, p);
        }

        if (newsFeedEntrySearchParam.getBeforeDate() != null) {
            Predicate p = cb.lessThanOrEqualTo(newsFeedEntryRoot.get(NewsFeedEntry_.publicationDate), newsFeedEntrySearchParam.getBeforeDate());
            criteria = cb.and(criteria, p);
        }
        
        if (newsFeedEntrySearchParam.isShowRetired() == false) {
        	Predicate p = cb.equal(newsFeedEntryRoot.get(NewsFeedEntry_.retired), newsFeedEntrySearchParam.isShowRetired());
            criteria = cb.and(criteria, p);	
        }
        

        criteriaQuery.where(criteria);
        
        List<NewsFeedEntry> result = em.createQuery(criteriaQuery).getResultList();
        
        Collections.sort(result, new Comparator<NewsFeedEntry>() {

			@Override
            public int compare(NewsFeedEntry o1, NewsFeedEntry o2) {
	            if (o1.getPublicationDate().compareTo(o2.getPublicationDate()) < 0 ) {
	            	return 1;
	            } else if (o1.getPublicationDate().compareTo(o2.getPublicationDate()) > 0 ) {
	            	return -1;
	            } else {
	            	return 0;
	            }
            }
		});
        
		return result;
	}

	/* 
	 * @see com.groovyfly.controlcentre.service.SiteManagementServiceIF#saveNewsFeedEntries(java.util.List)
	 */
    @Override
    public void saveNewsFeedEntries(List<NewsFeedEntry> newsFeedEntries) throws Exception {
    	for (NewsFeedEntry e : newsFeedEntries) {
    		if (e.getNewsFeedEntryId() == 0) {
    			em.persist(e);
    		} else {
    			em.merge(e);
    		}
    	}
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SiteManagementServiceIF
	 * 		#getPages(com.groovyfly.controlcentre.client.sitemanagement.pages.PageSearchParam)
	 */
    @Override
    public List<WebPage> getWebPages(WebPageSearchParam webPageSearchParam) throws Exception {
    	String qry = "SELECT p FROM WebPage p";
    	TypedQuery<WebPage> allPagesQuery = em.createQuery(qry, WebPage.class);
    	List<WebPage> resultList = allPagesQuery.getResultList();
    	
	    return resultList;
    }

	/* 
	 * @see com.groovyfly.controlcentre.service.sitemanagement.SiteManagementServiceIF
	 * 		#savePage(com.groovyfly.controlcentre.structure.common.sitemanagement.Page)
	 */
    @Override
    public void saveWebPage(WebPage webPage) throws Exception {
    	if (webPage.getPageId() < 1) {
    		em.persist(webPage);
    	} else {
    		em.merge(webPage);
    	}
    }

}
