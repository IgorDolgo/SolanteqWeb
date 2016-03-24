package ru.solanteq.unittest;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.solanteq.core.HumanResourcesDepartmentRemote;
import ru.solanteq.entities.Employee;
import ru.solanteq.entities.Position;
import ru.solanteq.exceptions.HRDException;

/**
 *
 * @author igordolgo
 */
public class HRDtest {
	private static HumanResourcesDepartmentRemote hrdr;
	private static final String HOST = "localhost";
	private static final String PORT = "3700";

	@BeforeClass
	public static void setUpClass() throws NamingException {
		Properties props = new Properties();
		props.setProperty("org.omg.CORBA.ORBInitialHost", HOST);
		props.setProperty("org.omg.CORBA.ORBInitialPort", PORT);
		InitialContext ctx = new InitialContext(props);
		hrdr = (HumanResourcesDepartmentRemote) ctx.lookup(HumanResourcesDepartmentRemote.class.getName());
	}

	@Test
	public void testSearchEmployees() {
		List<Employee> expected = new LinkedList<>();
		Position position = new Position((short) 0, "Developer");
		Employee emp = new Employee(2, "Иванов", "Иван", new Date(), position);
		emp.setPatronymic("Иванович");
		expected.add(emp);
		emp = new Employee(4, "Фёдоров", "Фёдр", new Date(), position);
		emp.setPatronymic("Фёдорович");
		expected.add(emp);
		Map<String, Object> filters = new HashMap<>(1);
		filters.put("position.name", position.getName());
		try {
			List<Employee> employees = hrdr.findEmployees(0, 2, "name", "ASC", filters);
			Assert.assertNotNull(employees);
			Assert.assertTrue(employees.size() == 2);
			Assert.assertArrayEquals(expected.toArray(), employees.toArray());
		} catch (HRDException ex) {
			Assert.fail(ex.getMessage());
		}
	}
}
