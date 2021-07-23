/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.empleados.Services;

import com.example.empleados.model.Empleados;
import com.example.empleados.repository.EmpleadoRepository;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Win 10
 */
@Service
public class empleadosService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    public String exportReport(Long Id) {
        try {
            List<Empleados> empleados = empleadoRepository.findAllById(Id);
        File file = ResourceUtils.getFile("classpath:ReportesQr\\template.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleados);
        Conectar conectar = new Conectar("jdbc:mysql://127.0.0.1:3306/empleados_prueba_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("par", "ibarbia");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conectar.getConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Win 10\\Desktop"+"\\user1.pdf");
        
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Reporte generado en "+ "C:\\Users\\Win 10\\Desktop";
    }
    
    
    //Esta es la clase que estoy usando crearReporte
    public String crearReporte(Long email) throws JRException{
        
        
        JasperReport jasperReport = JasperCompileManager.compileReport("template.jrxml");
        Map<String,Object> map = new HashMap<>();
        Conectar conectar = new Conectar("jdbc:mysql://127.0.0.1:3306/empleados_prueba_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        map.put("parameter1", email);
        JRDataSource data = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conectar.getConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint,email+".pdf");
        
        return "report.pdf";
    }
    
    
}
