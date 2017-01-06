package sml.manager.api.aspect.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sml.manager.api.aspect.modelo.Requisicao;

@Repository
public class TesteDao {
	
	private SessionFactory sessionFactory;
	
	@Transactional
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void persistir(Requisicao p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
	}
}
