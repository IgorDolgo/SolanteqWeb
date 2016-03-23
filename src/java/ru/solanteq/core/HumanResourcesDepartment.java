package ru.solanteq.core;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import ru.solanteq.entities.Employee;
import ru.solanteq.exceptions.HRDException;
import static ru.solanteq.exceptions.HRDException.Exceptions.ERROR_IN_SEARCH;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@DeclareRoles("user")
public class HumanResourcesDepartment implements HumanResourcesDepartmentLocal, HumanResourcesDepartmentRemote {
	private static final Logger LOG = Logger.getLogger(HumanResourcesDepartment.class.getName());
	@PersistenceContext(unitName = "SolanteqWebPU")
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> findEmployees(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) throws HRDException {
		String query = null;
		try {
			StringBuilder where = new StringBuilder(" WHERE");
			StringBuilder order = new StringBuilder(20);
			String select = "SELECT emp FROM Employee emp";
			for (String field : filters.keySet()) {
				switch (field) {
					case "dateBefore":
						where.append(" emp.dateOfBirth <= :dateBefore AND ");
						break;
					case "dateAfter":
						where.append(" emp.dateOfBirth >= :dateAfter AND ");
						break;
					default:
						where.append(" UPPER(emp.").append(field).append(") LIKE UPPER(:").append(field.replace('.', '_')).append(") AND ");
				}
			}
			int size = where.length();
			where.delete(size - 5, size);
			if (sortField != null) {
				order.append(" ORDER BY emp.").append(sortField).append(" ").append(sortOrder);
			}
			query = select + where + order;
			TypedQuery<Employee> q = em.createQuery(query, Employee.class).setFirstResult(first).setMaxResults(pageSize);
			for (String field : filters.keySet()) {
				switch (field) {
					case "dateBefore":
					case "dateAfter":
						q.setParameter(field, (Date) filters.get(field), TemporalType.DATE);
						break;
					default:
						q.setParameter(field.replace('.', '_'), '%' + filters.get(field).toString() + '%');
				}
			}
			return q.getResultList();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "HRD exception thrown:", e);
			throw ERROR_IN_SEARCH.create(e, query);
		}
	}

	@Override
	public Integer countEmployees(Map<String, Object> filters) throws HRDException {
		String query = null;
		try {
			StringBuilder where = new StringBuilder(" WHERE");
			String select = "SELECT COUNT(emp.id) FROM Employee emp";
			for (String field : filters.keySet()) {
				switch (field) {
					case "dateBefore":
						where.append(" emp.dateOfBirth <= :dateBefore AND ");
						break;
					case "dateAfter":
						where.append(" emp.dateOfBirth >= :dateAfter AND ");
						break;
					default:
						where.append(" UPPER(emp.").append(field).append(") LIKE UPPER(:").append(field.replace('.', '_')).append(") AND ");
				}
			}
			int size = where.length();
			where.delete(size - 5, size);
			query = select + where;
			TypedQuery<Long> q = em.createQuery(query, Long.class);
			for (String field : filters.keySet()) {
				switch (field) {
					case "dateBefore":
					case "dateAfter":
						q.setParameter(field, (Date) filters.get(field), TemporalType.DATE);
						break;
					default:
						q.setParameter(field.replace('.', '_'), '%' + filters.get(field).toString() + '%');
				}
			}
			List<Long> res = q.getResultList();
			if (res != null && res.size() > 0) {
				return res.get(0).intValue();
			} else {
				return null;
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "HRD exception thrown:", e);
			throw ERROR_IN_SEARCH.create(e, query);
		}
	}
}
