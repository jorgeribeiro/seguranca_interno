package br.gov.ma.tce.seguranca.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class Report {

	public static File getReportUsuariosSistema(String pathRelatorios, List<?> dados, String relatorio, Map<String, Object> params) {
		try {
			InputStream input = new FileInputStream(pathRelatorios + "/" + relatorio);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
			JasperPrint jasperPrint = JasperFillManager.fillReport(input, params, dataSource);

			String diretorioFinalRelatorio = System.getProperty("java.io.tmpdir");
			File file = File.createTempFile("usuarios_sistema", ".pdf", new File(diretorioFinalRelatorio));
			JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
			return file;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File getReportUsuariosGrupo(String pathRelatorios, List<?> dados, String relatorio, Map<String, Object> params) {
		try {
			InputStream input = new FileInputStream(pathRelatorios + "/" + relatorio);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
			JasperPrint jasperPrint = JasperFillManager.fillReport(input, params, dataSource);

			String diretorioFinalRelatorio = System.getProperty("java.io.tmpdir");
			File file = File.createTempFile("usuarios_grupo", ".pdf", new File(diretorioFinalRelatorio));
			JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
			return file;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
