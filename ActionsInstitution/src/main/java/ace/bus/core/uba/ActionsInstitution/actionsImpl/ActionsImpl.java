package ace.bus.core.uba.ActionsInstitution.actionsImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ace.bus.core.uba.ActionsInstitution.actions.IntituteActions;
import ace.bus.core.uba.ActionsInstitution.estruc.Estructural;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActionsImpl implements IntituteActions {

	@Value("${path.jsonfile}")
	String jsonPath;

	@Value("${path.jarfile}")
	String jarPath;

	@Value("${appl.app}")
	String application;

	@Override
	public List<Estructural> getData() {
		try {
			List<Estructural> lstEstructural = new ArrayList<Estructural>();
			Reader readInst = new BufferedReader(new FileReader(jsonPath));

			ObjectMapper mapInst = new ObjectMapper().enable(JsonParser.Feature.AUTO_CLOSE_SOURCE)
					.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

			JsonNode rootNode = mapInst.readTree(readInst);

			readInst.close();

			List<JsonNode> instituciones = rootNode.findValues("instituciones");
			for (JsonNode jsonNode : instituciones) {
				// log.debug("JSON Data Entry Node Parent: " + jsonNode);
				Iterator<JsonNode> iterator = jsonNode.iterator();
				while (iterator.hasNext()) {
					JsonNode jsonChild = iterator.next();
					// log.debug("JSON Data Entry Node Child: " + jsonChild);
					Estructural institutions = new Estructural();
					institutions.setName(jsonChild.get("nombre").asText());
					institutions.setPortSocket(jsonChild.get("portServerSocket").asInt());
					institutions.setPortBus(jsonChild.get("portHTTPBus").asInt());
					institutions.setThreads(jsonChild.get("hilosXcliente").asInt());
					institutions.setReadTimeout(jsonChild.get("readTimeout").asInt());
					institutions.setConnectTimeout(jsonChild.get("connectionTimeout").asInt());
					lstEstructural.add(institutions);
					// log.debug("JSON Node Child: " +institutions);
				}
			}

			return lstEstructural;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public void invokeActionStart(List<Estructural> lstInst) {
		try {
			for (Estructural est : lstInst) {
				// log.debug("Action -> " +est.getPortSocket()+ " " +est.getPortBus()+ " "
				// +est.getThreads()+ " " +est.getReadTimeout()+ " " +est.getConnectTimeout()+ "
				// " +est.getName());
				Runtime.getRuntime()
						.exec("java -jar " + jarPath + " " + est.getPortSocket() + " " + est.getPortBus() + " "
								+ est.getThreads() + " " + est.getReadTimeout() + " " + est.getConnectTimeout() + " "
								+ est.getName());
			}
		} catch (IOException e) {
			log.error("Error -> " + e.getMessage(), e);
		}

	}

	@Override
	public void invokeActionStop() {

		try {
			// Runtime.getRuntime().exec("java QIBMHello")
			String command1[] = { "/bin/sh", "-c", "ps -ef | grep " + application };

			InputStream is = Runtime.getRuntime().exec(command1).getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String data = null;
			String pid = "0";
			while ((data = reader.readLine()) != null) {
				// log.debug("Action Step 1X -> " + data);
				if (data.contains("ServerSocketISO")) {
					// log.debug("Action Step 2X ->" + data);

					String[] processes = data.split(" ");
					if (processes[3].toString().equals(null)) {
						// log.debug("Action Step 4 ->" + processes[4].toString());
						pid = processes[4].toString();
					} else {
						// log.debug("Action Step 3 ->" + processes[3].toString());
						pid = processes[3].toString();
					}
					// int
					//log.debug("Action PIDs ->" + pid);
					String command2[] = { "/bin/sh", "-c", "kill -9 " + pid };
					Runtime.getRuntime().exec(command2);
				}
			}
			// log.debug("Action Stop -> " + result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error IO Action -> " + e.getMessage(), e);
		}
		log.debug("End Action ");
	}

}