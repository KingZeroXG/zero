package ace.bus.core.uba.ActionsInstitution.restctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ace.bus.core.uba.ActionsInstitution.actions.IntituteActions;
import ace.bus.core.uba.ActionsInstitution.estruc.Estructural;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zero
 *
 */
@RestController
@Slf4j
@RequestMapping("/instituciones")
@Api(tags = "Api Rest para las acciones de Instituciones")
public class ActionsJava {

	@Autowired
	private IntituteActions actions;
	
	@GetMapping("/listarInstituciones")
	@ApiOperation(notes = "Devuelve la Lista de Instituciones creadas", value = "getDataInstituciones")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Listado de Instituciones"),
			@ApiResponse(code = 404, message = "Vacio") })
	public ResponseEntity<List<Estructural>> getData() {
		try {
			return ResponseEntity.ok(actions.getData());
		} catch (Exception e) {
			log.error("Error listarInstituciones -> " + e.getMessage());
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping("/activarInstituciones")
	public ResponseEntity<String> postActivate() {
		
		try {
			List<Estructural> lsInst = actions.getData();
			actions.invokeActionStart(lsInst);
			return ResponseEntity.ok("Ejecucion Exitosa");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error -> activarInstituciones" + e.getMessage());
			return ResponseEntity.internalServerError().build();
			
		}
		
	}
	@DeleteMapping("/stoppearInstituciones")
	public ResponseEntity<String> deleteActivate(){
		try {
			actions.invokeActionStop();
			return ResponseEntity.ok("Ejecucion Exitosa");
		} catch (Exception e) {
			log.error("Error -> " + e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
}