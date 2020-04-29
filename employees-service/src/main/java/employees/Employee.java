package employees;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {

    private Long id;

    private String employeeName;

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }
}
