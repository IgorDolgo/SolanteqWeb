/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.solanteq.core;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ru.solanteq.entities.Employee;
import ru.solanteq.exceptions.HRDException;

/**
 *
 * @author igordolgo
 */
@Local
public interface HumanResourcesDepartmentLocal {
	List<Employee> findEmployees(int first, int pageSize, String sortField, String sort, Map<String, Object> filters) throws HRDException;

	Integer countEmployees(Map<String, Object> filters) throws HRDException;
}
