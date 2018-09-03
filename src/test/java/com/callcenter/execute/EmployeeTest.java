package com.callcenter.execute;

import com.callcenter.enumerator.EmployeeState;
import com.callcenter.enumerator.EmployeeType;
import com.callcenter.execute.Employee;
import com.callcenter.objects.ObjCall;
import com.callcenter.useful.BuildingStructures;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeTest {

    @Test(expected = NullPointerException.class)
    public void testEmployeeInvalidCreation() {
        new Employee(null);
    }

    @Test
    public void testEmployeeCreation() {
        Employee employee = Employee.buildOperator();

        assertNotNull(employee);
        assertEquals(EmployeeType.OPERATOR, employee.getEmployeeType());
        assertEquals(EmployeeState.AVAILABLE, employee.getEmployeeState());
    }

    @Test
    public void testEmployeeAttendWhileAvailable() throws InterruptedException {
        Employee employee = Employee.buildOperator();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(employee);
        employee.attend(BuildingStructures.listClientCalls(1).get(0));

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        assertEquals(1, employee.getAttendedCalls().size());
    }

    @Test
    public void testEmployeeStatesWhileAttend() throws InterruptedException {
      /*  Employee employee = Employee.buildOperator();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(employee);
        assertEquals(EmployeeState.AVAILABLE, employee.getEmployeeState());
        TimeUnit.SECONDS.sleep(1);
        employee.attend(BuildingStructures.listClientCalls(2));
        employee.attend(BuildingStructures.listClientCalls(1));
        TimeUnit.SECONDS.sleep(1);
        assertEquals(EmployeeState.BUSY, employee.getEmployeeState());

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        assertEquals(2, employee.getAttendedCalls().size());*/
    }

}
