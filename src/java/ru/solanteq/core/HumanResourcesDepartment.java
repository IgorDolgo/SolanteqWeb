package ru.solanteq.core;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.solanteq.entities.Employee;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HumanResourcesDepartment implements HumanResourcesDepartmentLocal {
	private static final Logger LOG = Logger.getLogger(HumanResourcesDepartment.class.getName());
	@PersistenceContext(unitName = "SolanteqWebPU")
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> findEmployees(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {
		StringBuilder where = new StringBuilder(" WHERE");
		StringBuilder order = new StringBuilder(20);
		String select = "SELECT emp FROM Employee emp";
		for (String field : filters.keySet()) {
			where.append(" UPPER(emp.").append(field).append(") LIKE UPPER(:").append(field.replace('.', '_')).append(") AND ");
		}
		if (filters.containsKey("dateBefore")) {
			where.append("emp.")
		}
		int size = where.length();
		where.delete(size - 5, size);
		if (sortField != null) {
			order.append(" ORDER BY emp.").append(sortField).append(" ").append(sortOrder);
		}
		TypedQuery<Employee> q = em.createQuery(select + where + order, Employee.class).setFirstResult(first).setMaxResults(pageSize);
		for (String field : filters.keySet()) {
			q.setParameter(field.replace('.', '_'), '%' + filters.get(field).toString() + '%');
		}
		return q.getResultList();
	}

	@Override
	public Integer countEmployees(Map<String, Object> filters) {
		StringBuilder where = new StringBuilder(" WHERE");
		String select = "SELECT COUNT(emp.id) FROM Employee emp";
		for (String field : filters.keySet()) {
			where.append(" UPPER(emp.").append(field).append(") LIKE UPPER(:").append(field.replace('.', '_')).append(") AND ");
		}
		int size = where.length();
		where.delete(size - 5, size);
		TypedQuery<Long> q = em.createQuery(select + where, Long.class);
		for (String field : filters.keySet()) {
			q.setParameter(field.replace('.', '_'), '%' + filters.get(field).toString() + '%');
		}
		List<Long> res = q.getResultList();
		if (res != null && res.size() > 0) {
			return res.get(0).intValue();
		} else {
			return null;
		}
	}
}
