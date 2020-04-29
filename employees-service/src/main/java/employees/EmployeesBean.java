package employees;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeesBean {

    private static List<Employee> EMPLOYEES = new CopyOnWriteArrayList<>();

    private static AtomicLong SEQ = new AtomicLong();

    public Employee createEmployee(Employee employee) {

        employee.setId(SEQ.incrementAndGet());
        EMPLOYEES.add(employee);
        return employee;
    }

    public List<Employee> listEmployees() {
        return EMPLOYEES;
    }

    public Employee updateEmployee(Employee employee) {
        Employee found = EMPLOYEES.stream()
                .filter(e -> e.getId().equals(employee.getId()))
                .findFirst().orElseThrow(() -> new IllegalStateException("Employee not found " + employee.getId()));
        found.setName(employee.getName());
        return found;
    }

    public void deleteEmployee(String id) {
        Employee employee = EMPLOYEES.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Employee not found " + id));
        EMPLOYEES.remove(employee);
    }

    public Employee findEmployeeById(String id) {
        return EMPLOYEES.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Employee not found " + id));
    }
}
