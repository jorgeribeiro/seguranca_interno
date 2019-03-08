package br.gov.ma.tce.seguranca.interno.server.beans.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {
	private static ServiceLocator me = new ServiceLocator();

	private Context cntx;

	private DataSource ds;

	public ServiceLocator() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File("Zigurate.properties")));
		} catch (IOException ex1) {
			props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
			try {
				props.store(new FileOutputStream(new File("Zigurate.properties")), "Service Locator");
			} catch (IOException ex) {
			}
		}

		try {
			cntx = new InitialContext();
			cntx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			cntx.addToEnvironment(Context.PROVIDER_URL, props.get(Context.PROVIDER_URL));
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}

	public static ServiceLocator getInstance() {
		return me;
	}

	public Connection getConnection(String lookupName) {
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		ds = null;
		try {
			ds = (DataSource) ctx.lookup(lookupName);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}

		try {
			conn = ds.getConnection();
			return conn;
		} catch (SQLException ex1) {
			ex1.printStackTrace();
			return null;
		}
	}
}
