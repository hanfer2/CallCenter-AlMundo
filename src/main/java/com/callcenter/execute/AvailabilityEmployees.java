package com.callcenter.execute;

import com.callcenter.enumerator.EmployeeType;
import com.callcenter.enumerator.EmployeeState;
import org.apache.commons.lang3.Validate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityEmployees {

    public Employee findEmployee(Collection<Employee> employeeList){
        Validate.notNull(employeeList);
        List<Employee> availableEmployees = employeeList.stream().filter(e -> e.getEmployeeState() == EmployeeState.AVAILABLE).collect(Collectors.toList());
       
       Collections.sort(availableEmployees, (p1, p2) -> p1.getEmployeeType().compareTo(p2.getEmployeeType()));
      // System.out.println("sort list ..."+availableEmployees);
        //Hiereancy 1 to MAxValue Int operator , supervisor, Director...and if I need other more for example Presidente its number will be higher
        
        // I go through types of employees
        //I take first element == minvalue I find employee free with this type
        Employee myEmployee = null;
        for (EmployeeType dir : EmployeeType.values()) {
            System.out.println(dir);
            for(Employee employee :   availableEmployees)
            {
               if(employee.getEmployeeType().equals(dir) && employee.getEmployeeState().equals(EmployeeState.AVAILABLE))
               {
                   myEmployee = employee;
                   return employee;
               }
            }
        }
        return myEmployee;
    }

}
