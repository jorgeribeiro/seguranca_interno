package br.gov.ma.tce.seguranca.interno.server.beans.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;

@Stateless
public class UsuarioFacadeBean {
	public static final String URI = "java:global/seguranca_interno_ear/seguranca_interno_server/UsuarioFacadeBean!br.gov.ma.tce.seguranca.interno.server.beans.usuario.UsuarioFacadeBean";
	public static final String JNDI_OBRALEGAL = "java:global/obra_legal_ear/seguranca_interno_server/UsuarioFacadeBean!br.gov.ma.tce.seguranca.interno.server.beans.usuario.UsuarioFacadeBean";

	@PersistenceContext(unitName = "seguranca_interno_server")
	private EntityManager em;

	public Usuario insert(Usuario usuario) {

		em.persist(usuario);
		return usuario;

	}

	public Usuario update(Usuario usuario) {
		return em.merge(usuario);
	}

	public void atualizaUsuario(Integer servidorId, Integer usuarioId) throws NamingException, SQLException {

		InitialContext ctx = null;
		DataSource ds = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		ctx = new InitialContext();
		ds = (javax.sql.DataSource) ctx.lookup("java:/XAPostgresDS");
		conn = ds.getConnection();

		try {

			StringBuffer sql = new StringBuffer(
					"update seguranca_interno.usuario set servidor_id  = ? where usuario_id  = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, servidorId);
			pstmt.setInt(2, usuarioId);
			pstmt.executeUpdate();

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	public Usuario findByPrimaryKey(int id) {
		return em.find(Usuario.class, id);

	}

	public void delete(int id) {
		Usuario usuario = findByPrimaryKey(id);
		em.remove(usuario);

	}

	@SuppressWarnings("unchecked")
	public Collection<Usuario> findAll() {
		Query q = em.createQuery("select u from " + Usuario.NAME + " u ");
		return q.getResultList();

	}

	public Usuario findByMatricula(Integer matricula) {

		Usuario usuario = null;

		try {
			Query q = em.createQuery("select u from " + Usuario.NAME + " u where u.matricula=:matricula");
			q.setParameter("matricula", matricula);
			usuario = (Usuario) q.getSingleResult();

		} catch (Exception e) {
			usuario = null;
		}
		return usuario;
	}

	public Usuario findByLoginComGrupos(Integer matricula) {

		try {
			Query q = em.createQuery("select u from " + Usuario.NAME
					+ " u left join fetch u.usuarioGrupos where u.matricula=:matricula ");
			q.setParameter("matricula", matricula);

			Usuario usuario = (Usuario) q.getSingleResult();
			return usuario;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Usuario> findByFiltro(String matricula, String nome) {
		StringBuilder query = new StringBuilder("select u from " + Usuario.NAME + " u where 1=1 ");
		if (matricula != null && !matricula.trim().isEmpty()) {
			query.append(" and u.matricula = " + matricula.replaceAll("\'", ""));
		}
		if (nome != null && !nome.trim().isEmpty()) {
			query.append(" and LOWER(u.servidor.nome) like LOWER('%" + nome.replaceAll("\'", "") + "%') ");
		}
		query.append(" order by u.servidor.nome");

		Query q = em.createQuery(query.toString());
		return q.getResultList();
	}

	public Collection<Usuario> findUsuariosByGrupoUsuario(int grupoUsuarioId) {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		TypedQuery<Usuario> q = em.createQuery("SELECT u FROM " + UsuarioGrupo.NAME + " ug JOIN ug.usuario u "
				+ " WHERE ug.grupoUsuario.grupoUsuarioId = :grupoUsuarioId", Usuario.class);
		usuarios = q.setParameter("grupoUsuarioId", grupoUsuarioId).getResultList();

		return usuarios;
	}

	public Integer findMaxId() {
		Query q = em.createQuery("select max(cast(u.usuarioId as integer)) from " + Usuario.NAME + " u ");
		return (Integer) q.getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	public Collection<Usuario> findPorIds(List<Integer> ids) {
		Query q = em.createQuery("select u from " + Usuario.NAME + " u where u.usuarioId in (:ids) ");
		q.setParameter("ids", ids);
		return q.getResultList();
	}

}
