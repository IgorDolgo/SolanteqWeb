package ru.solanteq.util;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ru.solanteq.core.HumanResourcesDepartmentLocal;
import ru.solanteq.entities.Employee;

public class LazyEmployeeModel extends LazyDataModel<Employee> {
	private static final long serialVersionUID = 1L;
	private List<Employee> data = new LinkedList<>();
	private final HumanResourcesDepartmentLocal hr;
	private Date dateBefore;
	private Date dateAfter;

	public Date getDateAfter() {
		return dateAfter;
	}

	public void setDateAfter(Date dateAfter) {
		this.dateAfter = dateAfter;
	}

	public Date getDateBefore() {
		return dateBefore;
	}

	public void setDateBefore(Date dateBefore) {
		this.dateBefore = dateBefore;
	}

	public LazyEmployeeModel(HumanResourcesDepartmentLocal hr) {
		this.hr = hr;
	}

	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		if (dateBefore != null) {
			filters.put("dateBefore", dateBefore);
		}
		if (dateAfter != null) {
			filters.put("dateAfter", dateAfter);
		}
		String order = (sortOrder == SortOrder.UNSORTED) ? null : ((sortOrder == SortOrder.ASCENDING) ? "ASC" : "DESC");
		data = hr.findEmployees(first, pageSize, sortField, order, filters);
		setRowCount(hr.countEmployees(filters));
		return Collections.unmodifiableList(data);
	}
}
