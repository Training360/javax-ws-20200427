package employees;

import javax.xml.ws.Endpoint;

public class EmployeesMain {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/employees",
                new EmployeesService());
    }
}
