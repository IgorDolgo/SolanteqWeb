/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.solanteq.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ru.solanteq.core.HumanResourcesDepartmentLocal;
import ru.solanteq.entities.Employee;

/**
 *
 * @author artem
 * @param <T>
 */
public class LazyEmployeeModel extends LazyDataModel<Employee> {
	private static final long serialVersionUID = 1L;
	private List<Employee> data = new LinkedList<>();
	private final HumanResourcesDepartmentLocal hr;

	public LazyEmployeeModel(HumanResourcesDepartmentLocal hr) {
		this.hr = hr;
	}

	@Override
	public Employee getRowData(String rowKey) {
		try {
			for (Employee emp : data) {
				if (emp.getId().equals(Integer.parseInt(rowKey))) {
					return emp;
				}
			}
			return null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public Object getRowKey(Employee object
	) {
		return object.getId();
	}

	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String order = (sortOrder == SortOrder.UNSORTED) ? null : ((sortOrder == SortOrder.ASCENDING) ? "ASC" : "DESC");
		data = hr.findEmployees(first, pageSize, sortField, order, filters);
		setRowCount(hr.countEmployees(filters));
		return Collections.unmodifiableList(data);
	}
}
