package br.gov.ma.tce.seguranca.interno.server.beans.grupousuario;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.gov.ma.tce.seguranca.interno.server.beans.sistema.Sistema;

@Stateless
public class GrupoUsuarioFacadeBean {
	public static final String URI = "java:global/seguranca_interno_ear/seguranca_interno_server/GrupoUsuarioFacadeBean!br.gov.ma.tce.seguranca.interno.server.beans.grupousuario.GrupoUsuarioFacadeBean";

	@PersistenceContext(unitName = "seguranca_interno_server")
	private EntityManager em;

	public GrupoUsuario insert(GrupoUsuario grupoUsuario) {

		em.persist(grupoUsuario);
		return grupoUsuario;

	}

	public GrupoUsuario update(GrupoUsuario grupoUsuario) {

		return em.merge(grupoUsuario);

	}

	public GrupoUsuario findByPrimaryKey(int id) {

		return em.find(GrupoUsuario.class, id);

	}

	public void delete(int id) {

		GrupoUsuario grupoUsuario = findByPrimaryKey(id);
		em.remove(grupoUsuario);

	}

	@SuppressWarnings("unchecked")
	public Collection<GrupoUsuario> findAll() {

		Query q = em.createQuery("select gu from " + GrupoUsuario.NAME + " gu ");
		return q.getResultList();

	}

	public Collection<GrupoUsuario> findGruposBySistema(Sistema sistema) {
		Query q = em.createQuery("select g from " + GrupoUsuario.NAME + " g where g.sistema=:arg0 order by g.nome")
				.setParameter("arg0", sistema);
		Collection<GrupoUsuario> grupos = new ArrayList<GrupoUsuario>();
		for (Object o : q.getResultList()) {
			grupos.add((GrupoUsuario) o);
		}
		return grupos;
	}

}
