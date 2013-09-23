package irt.web.editors;

import irt.objects.components.componentType.ComponentType;
import irt.objects.components.componentType.dao.ComponentTypeDAO;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ComponentTypeEditor extends PropertyEditorSupport {

	Logger logger = LogManager.getLogger(ComponentTypeEditor.class.getName());

	private ComponentTypeDAO componentTypeDAO;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		ComponentType componentType = new ComponentType();
		int classId = Integer.parseInt(text);
		componentType.setClassId(classId);

		List<ComponentType> componentTypes = componentTypeDAO.getList(false);

		if(componentTypes!=null){
			int index = componentTypes.indexOf(componentType);
			if(index>=0)
				componentType = componentTypes.get(index);
			else
				componentType = null;
		}

		logger.info("setAsText:"+componentType);
		setValue(componentType);
	}

	public ComponentTypeDAO getComponentTypeDAO() {
		return componentTypeDAO;
	}

	@Autowired
	public void setComponentTypeDAO(ComponentTypeDAO componentTypeDAO) {
		logger.info("setComponentTypeDAO:"+componentTypeDAO);
		this.componentTypeDAO = componentTypeDAO;
	}
}
