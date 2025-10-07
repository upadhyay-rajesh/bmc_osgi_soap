package com.mynewkraf.impl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.mynewkraf.api.Employee;
import com.mynewkraf.api.HelloService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

@Component(service = HelloService.class)
public class HelloServiceImpl implements HelloService{
	
	

	@Override
	public String say(String name) {
		// TODO Auto-generated method stub
		 return "Hello, " + name + "!";
	}

	@Override
	public String createProfile(Employee ee) {
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("helloPU");
		EntityManager em=emf.createEntityManager();
		
		EntityTransaction et=em.getTransaction();
		et.begin();
			em.persist(ee);
		et.commit();
		return "record created";
	}

	@Override
	public String deleteProfile(String id) {
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("helloPU");
		EntityManager em=emf.createEntityManager();
		
		EntityTransaction et=em.getTransaction();
		et.begin();
			Query q=em.createQuery("delete from com.mynewkraf.api.Employee e where e.email=:em");
			q.setParameter("em", id);
			
			int i=q.executeUpdate();
			if(i>0) {
				return "record deleted";
			}
		et.commit();
		return "could not delete";
	}

	@Override
	public String editProfile(String id, Employee ee) {
		ee.setEmail(id);
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("helloPU");
		EntityManager em=emf.createEntityManager();
		
		EntityTransaction et=em.getTransaction();
		et.begin();
			em.merge(ee);
		et.commit();
		return "record updated";
	}

	@Override
	public String viewProfile(String id) {
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("helloPU");
		EntityManager em=emf.createEntityManager();
		
		Query q=em.createQuery("from com.mynewkraf.api.Employee e where e.email=:em");
		q.setParameter("em", id);
		
		Employee e1=(Employee)q.getSingleResult();
		return "record is "+e1.getName()+" "+e1.getEmail();
	}

	@Override
	public List<Employee> viewAllProfile() {
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("helloPU");
		EntityManager em=emf.createEntityManager();
		Query q=em.createQuery("from com.mynewkraf.api.Employee e");
		return q.getResultList();
	}

}
