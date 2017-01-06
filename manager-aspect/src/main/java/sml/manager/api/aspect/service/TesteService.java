package sml.manager.api.aspect.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sml.manager.api.aspect.dao.TesteDao;
import sml.manager.api.aspect.modelo.Requisicao;

@Service
public class TesteService {
	
	 private TesteDao testeDao;
	 
	 
	 public void setTesteDao(TesteDao testeDao) {
		this.testeDao = testeDao;
	}

	 @Transactional
	public void persistirAluno(Requisicao entity) {
           this.testeDao.persistir(entity);
     }
}
