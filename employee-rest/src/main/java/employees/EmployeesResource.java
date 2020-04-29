package employees;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeesResource {

    private EmployeesBean employeesBean = new EmployeesBean();

    @POST

    public Response createEmployee(Employee employee) {
        var created = employeesBean.createEmployee(employee);
        return Response.status(201).entity(created).build();
    }

    @GET
    public List<Employee> listEmployees() {
        return employeesBean.listEmployees();
    }


    @POST
    @Path("{id}")
    public Response updateEmployee(@PathParam("id") long id, Employee employee) {
        try {
            return Response.ok(employeesBean.updateEmployee(id, employee)).build();
        }
        catch (IllegalStateException e) {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteEmployee(@PathParam("id") long id) {
        employeesBean.deleteEmployee(id);
    }

    @GET
    @Path("{id}")
    public Employee findEmployeeById(@PathParam("id") long id) {
        return employeesBean.findEmployeeById(id);
    }
}
