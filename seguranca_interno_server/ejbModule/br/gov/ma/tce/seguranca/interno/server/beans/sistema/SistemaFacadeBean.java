package br.gov.ma.tce.seguranca.interno.server.beans.sistema;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SistemaFacadeBean {
	public static final String URI = "java:global/seguranca_interno_ear/seguranca_interno_server/SistemaFacadeBean!br.gov.ma.tce.seguranca.interno.server.beans.sistema.SistemaFacadeBean";
	
	@PersistenceContext(unitName = "seguranca_interno_server")
	private EntityManager em;

	public Sistema insert(Sistema sistema) {
		
		em.persist(sistema);
		return sistema;

	}

	public Sistema update(Sistema sistema) {
		
		return em.merge(sistema);

	}

	public Sistema findByPrimaryKey(int id) {
		
		return em.find(Sistema.class, id);

	}

	public void delete(int id) {
		
		Sistema sistema = findByPrimaryKey(id);
		em.remove(sistema);

	}

	@SuppressWarnings("unchecked")
	public Collection<Sistema> findAll() {
		Query q = em.createQuery("select s from " + Sistema.NAME + " s order by s.nome");
		return q.getResultList();

	}

}
