package employees;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@WebService
public class EmployeesService {

    private EmployeesBean bean = new EmployeesBean();

    public EmployeeDto createEmployee(@WebParam(name = "employee-to-create") CreateEmployeeCommandDto command) {
        var employee = bean.createEmployee(new Employee(command.getName()));
        return new EmployeeDto(employee.getId(), employee.getEmployeeName());
    }

    public List<EmployeeDto> listEmployees() {
        return bean.listEmployees().stream()
                .map(e -> new EmployeeDto(e.getId(), e.getEmployeeName()))
                .collect(Collectors.toList());
    }

    public Employee updateEmployee(Employee employee) {
        return bean.updateEmployee(employee);
    }

    public void deleteEmployee(long id) {
        bean.deleteEmployee(id);
    }

    public Employee findEmployeeById(long id) {
        return bean.findEmployeeById(id);
    }
}
