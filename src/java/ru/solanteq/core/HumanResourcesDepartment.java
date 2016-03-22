/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.solanteq.core;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.solanteq.entities.Employee;

/**
 *
 * @author igordolgo
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HumanResourcesDepartment implements HumanResourcesDepartmentLocal {
	@PersistenceContext(unitName = "SolanteqWebPU")
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> findEmployees(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {
		StringBuilder where = new StringBuilder(50);
		StringBuilder order = new StringBuilder(20);
		String select = "SELECT emp FROM Employee emp WHERE";
		for (String field : filters.keySet()) {
			where.append(" UPPER(emp.").append(field).append(") LIKE UPPER(:").append(field).append(") AND");
		}
		if (sortField != null) {
			order.append("emp.").append(sortField).append(" ").append(sortOrder);
		}
		TypedQuery<Employee> q = em.createQuery(select + where.substring(0, where.length() - 4) + order, Employee.class).setFirstResult(first).setMaxResults(pageSize);
		for (String field : filters.keySet()) {
			q.setParameter(field, '%' + filters.get(field).toString() + '%');
		}
		return q.getResultList();
	}

	@Override
	public Integer countEmployees(Map<String, Object> filters) {
		StringBuilder where = new StringBuilder(50);
		String select = "SELECT COUNT(emp.id) FROM Employee emp WHERE";
		for (String field : filters.keySet()) {
			where.append(" UPPER(emp.").append(field).append(") LIKE UPPER(:").append(field).append(") AND");
		}
		TypedQuery<Integer> q = em.createQuery(select + where.substring(0, where.length() - 4), Integer.class);
		for (String field : filters.keySet()) {
			q.setParameter(field, '%' + filters.get(field).toString() + '%');
		}
		List<Integer> res = q.getResultList();
		if (res != null && res.size() > 0) {
			return res.get(0);
		} else {
			return null;
		}
	}
}
