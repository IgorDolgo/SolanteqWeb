/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.solanteq.gui;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.solanteq.core.HumanResourcesDepartmentLocal;
import ru.solanteq.util.LazyEmployeeModel;

/**
 *
 * @author igordolgo
 */
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
