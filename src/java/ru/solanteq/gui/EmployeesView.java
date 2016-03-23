package ru.solanteq.gui;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.solanteq.core.HumanResourcesDepartmentLocal;
import ru.solanteq.util.LazyEmployeeModel;

@ManagedBean
@ViewScoped
public class EmployeesView {
	@EJB
	private HumanResourcesDepartmentLocal hrdl;
	private LazyEmployeeModel employeeModel;

	public LazyEmployeeModel getEmployeeModel() {
		return employeeModel;
	}

	@PostConstruct
	private void postConstruct() {
		employeeModel = new LazyEmployeeModel(hrdl);
	}
}
