package com.example.empleados.controller;

import com.example.empleados.Services.Conectar;
import com.example.empleados.Services.empleadosService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.empleados.exception.ResourceNotFoundException;
import com.example.empleados.model.Empleados;
import com.example.empleados.repository.EmpleadoRepository;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Id;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.util.ResourceUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
        @Autowired
        private empleadosService empleados;
	//Get todo los empleados
	
	@GetMapping("/obtenerEmpleados")
	public List<Empleados> getEmpleados(){
		return empleadoRepository.findAll();
	}
	
	//Creacion de empleados Rest Api
	@PostMapping("/crearEmpleado")
	public Empleados crearEmpleado(@RequestBody Empleados empleados) {
		return empleadoRepository.save(empleados);
	}
	@GetMapping("/obtenerEmpleados/{id}")
	public ResponseEntity<Empleados> getEmpleadoId(@PathVariable Long id) {
		Empleados empleado = empleadoRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No se encontro el empleado con el id:"+id));
		return ResponseEntity.ok(empleado);
	}
	@PutMapping("/actualizarEmpleado/{id}")
	public ResponseEntity<Empleados> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleados empleadoDetails) {
		Empleados empleado = empleadoRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No se encontro el empleado con el id:"+id));
		empleado.setFirstName(empleadoDetails.getFirstName());
		empleado.setLastName(empleadoDetails.getLastName());
		empleado.setEmailId(empleadoDetails.getEmailId());
		Empleados updateEmpleado = empleadoRepository.save(empleado);
		return ResponseEntity.ok(updateEmpleado);
	}
	
	//Borrar Empleado
	@DeleteMapping("/borrarEmpleado/{id}")
	public ResponseEntity<Map<String, Boolean>> borrarEmpleado(@PathVariable Long id){
		Empleados empleado = empleadoRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No se encontro el empleado con el id:"+id));
		empleadoRepository.delete(empleado);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
//       @GetMapping("/obtenerPdf/{id}")
//       public String generarPdf(@PathVariable Long id) throws FileNotFoundException, JRException{
//           return empleados.exportReport("pdf", id);
//       }
//	   public ResponseEntity<Map<String, Boolean>> verReporte(@PathVariable Long id) {
//        try {
//            Optional<Empleados> empleado = empleadoRepository.findById(id);
//            String rutaInforma = ".\\resources\\ReportesQr\\report1.jasper";
//            Map parametros = new HashMap();
//            parametros.put("idNum", id);
//            File file = ResourceUtils.getFile(rutaInforma);
//            JasperPrint informe = JasperFillManager.fillReport(rutaInforma, parametros);
//            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//            //JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleado);
//            JasperViewer ventanaVisor = new JasperViewer(informe, false);
//            ventanaVisor.setTitle("Prueba Reporte");
//            ventanaVisor.setVisible(true);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.of(parametros);
//    }
      @GetMapping("/obtenerPdf/{id}")
      public String reporte(@PathVariable Long id) throws JRException {
          
          return empleados.crearReporte(id);
      
      }
      
//      @GetMapping("/pdf")
//      public static void execute()  {
//        try {
////Crear el mapa de parametros
//            Map<String, Object> parameters = new HashMap<String, Object>();
//            parameters.put("par", "ibarbia");
//            InputStream reportStream = new FileInputStream("template.jrxml");
//
////Iniciar reporte
//            JasperReport report = JasperCompileManager.compileReport(reportStream);
//            JasperPrint jasperPrint = new JasperPrint();
//
////Llenar el reporte donde se le pasa en el tercer argumento el mapa ya creado
//            JasperFillManager.fillReportToFile(report, "template.jrxml", (Map<String, Object>) parameters, new JREmptyDataSource());
//            reportStream.close();
//
////Generar PDF
//            List listJasper = new ArrayList();
//            listJasper.add(JRLoader.loadObjectFromFile("template.jrxml"));
//            JRPdfExporter exp = new JRPdfExporter();
//            exp.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listJasper);
//            exp.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
//            exp.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "reporte.pdf");
//            exp.exportReport();
//            System.out.println("Observar aca "+exp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
      
      @GetMapping("/visibleNavegador")
      public static void visible(){
      // descarga dentro del mismo proyecto
                Conectar conectar = new Conectar("jdbc:mysql://127.0.0.1:3306/empleados_prueba_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		JasperPrint jasperPrint;
            try {
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                configuration.setEncrypted(true);
                configuration.set128BitKey(true);
                configuration.setOwnerPassword("1234"); 
                configuration.setPermissions(PdfWriter.ALLOW_SCREENREADERS);
                jasperPrint = JasperFillManager.fillReport(
                        "template.jasper", null,
                        conectar.getConnection());
            
		JRPdfExporter exp = new JRPdfExporter();
		exp.setExporterInput(new SimpleExporterInput(jasperPrint));
		exp.setExporterOutput(new SimpleOutputStreamExporterOutput("ReporteEmpleados.pdf"));
		exp.setConfiguration(configuration);
		exp.exportReport();
 
		// se muestra en una ventana aparte para su descarga
		JasperPrint jasperPrintWindow = JasperFillManager.fillReport(
				"template.jasper", null,
				conectar.getConnection());
		JasperViewer jasperViewer = new JasperViewer(jasperPrintWindow);
		jasperViewer.setVisible(true);
            } catch (JRException ex) {
                ex.printStackTrace();
            }
      }
}
