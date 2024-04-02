package ace.bus.core.uba.ActionsInstitution.estruc;

import lombok.Data;

@Data
public class Estructural {
	String name;
	int portSocket;
	int portBus;
	int threads;
	int readTimeout;
	int connectTimeout;
}
