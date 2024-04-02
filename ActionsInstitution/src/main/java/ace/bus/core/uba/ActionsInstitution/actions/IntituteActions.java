package ace.bus.core.uba.ActionsInstitution.actions;

import java.util.List;

import ace.bus.core.uba.ActionsInstitution.estruc.Estructural;

public interface IntituteActions {
	public List<Estructural> getData();
	
	public void invokeActionStart(List<Estructural> lstInst);
	
	public void invokeActionStop();
}