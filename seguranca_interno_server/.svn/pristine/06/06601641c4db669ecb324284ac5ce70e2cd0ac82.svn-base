package br.gov.ma.tce.seguranca.interno.server.beans.servidor;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ServidorFacadeBean {
	public static final String URI = "java:global/seguranca_interno_ear/seguranca_interno_server/ServidorFacadeBean!br.gov.ma.tce.seguranca.interno.server.beans.servidor.ServidorFacadeBean";

	@PersistenceContext(unitName = "seguranca_interno_server")
	private EntityManager em;

	public Servidor insert(Servidor servidor) {

		em.persist(servidor);
		return servidor;

	}

	public Servidor update(Servidor servidor) {

		return em.merge(servidor);

	}

	public Servidor findByPrimaryKey(int id) {

		return em.find(Servidor.class, id);

	}

	public void delete(int id) {

		Servidor servidor = findByPrimaryKey(id);
		em.remove(servidor);

	}

	@SuppressWarnings("unchecked")
	public Collection<Servidor> findAll() {

		Query q = em.createQuery("select s from " + Servidor.NAME + " s ");
		return q.getResultList();

	}

	@SuppressWarnings("rawtypes")
	public Servidor findByMatricula(Integer matricula) {

		Query q = em.createQuery("select s from " + Servidor.NAME + " s where s.matricula=:arg0").setParameter("arg0",
				matricula);

		List results = q.getResultList();

		Servidor servidor = null;
		if (!results.isEmpty()) {
			servidor = (Servidor) results.get(0);
			return servidor;
		} else {
			return null;
		}

	}

	public Integer findMaxId() {
		Query q = em.createQuery("select max(cast(s.servidorId as integer)) from " + Servidor.NAME + " s ");
		return (Integer) q.getResultList().get(0);
	}
	
	@SuppressWarnings("rawtypes")
	public Servidor findByCargoFuncao(int cargoFuncaoId) {
		Query q = em.createQuery("select s from " + Servidor.NAME + " s where s.cargoFuncaoId = :cargoFuncaoId");
		q.setParameter("cargoFuncaoId", cargoFuncaoId);
		List results = q.getResultList();

		Servidor servidor = null;
		if (!results.isEmpty()) {
			servidor = (Servidor) results.get(0);
			return servidor;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Servidor> findBySetorAndComissionado(Integer setorId) {
		Query q = em.createQuery("select s from " + Servidor.NAME
				+ " s where (s.lotacaoExercicioId = :id or s.lotacaoSubordinacao = :id) and s.cargoFuncao is not null order by s.funcaoHierarquia, s.lotacaoExercicio, s.cargoFuncao, s.nome");
		q.setParameter("id", setorId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Collection<Servidor> findBySetorAndComissionadoSacex(Integer setorId) {
		Query q = em.createQuery("select s from " + Servidor.NAME
				+ " s where s.lotacaoExercicioId = :id and s.cargoFuncao is not null order by s.funcaoHierarquia, s.lotacaoExercicio, s.nome");
		q.setParameter("id", setorId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Collection<Servidor> findBySetorAndComissionado2(Integer setorId1, Integer setorId2) {
		Query q = em.createQuery("select s from " + Servidor.NAME
				+ " s where (s.lotacaoExercicioId = :id1 or s.lotacaoExercicioId = :id2) and s.cargoFuncao is not null order by s.funcaoHierarquia, s.lotacaoExercicio, s.cargoFuncao, s.nome");
		q.setParameter("id1", setorId1).setParameter("id2", setorId2);
		return q.getResultList();
	}
}
