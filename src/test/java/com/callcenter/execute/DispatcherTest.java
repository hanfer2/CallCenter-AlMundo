package com.callcenter.execute;

import com.callcenter.constants.Constants;
import com.callcenter.enumerator.EmployeeType;
import com.callcenter.execute.Employee;
import com.callcenter.execute.Dispatcher;
import com.callcenter.objects.ObjCall;
import com.callcenter.objects.ObjEmployee;
import com.callcenter.useful.BuildingStructures;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DispatcherTest {

    private static final int CALL_AMOUNT = 10;

    private static final int MIN_CALL_DURATION = Constants.MIN_DURATION_CALL;

    private static final int MAX_CALL_DURATION = Constants.MAX_DURATION_CALL;

    @Test(expected = NullPointerException.class)
    public void testDispatcherCreationWithNullEmployees() {
        new Dispatcher(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDispatcherCreationWithNullStrategy() {
        new Dispatcher(new ArrayList<>(), null);
    }
    
    @Test
    public void testDispatchCallsToEmployees() throws InterruptedException {
        
        List<Employee> employeeList = BuildingStructures.listEmployees(5, EmployeeType.OPERATOR);
        employeeList.addAll( BuildingStructures.listEmployees(3, EmployeeType.SUPERVISOR));
        employeeList.addAll(BuildingStructures.listEmployees(2, EmployeeType.DIRECTOR));
        
        System.out.println("Number of empleados::"+employeeList.size());
        Dispatcher dispatcher = new Dispatcher(employeeList);
        dispatcher.start();
        TimeUnit.SECONDS.sleep(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        TimeUnit.SECONDS.sleep(1);

        buildCallList().stream().forEach(call -> {
            dispatcher.dispatch(call);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });

        executorService.awaitTermination(MAX_CALL_DURATION * 2, TimeUnit.SECONDS);
        assertEquals(CALL_AMOUNT, employeeList.stream().mapToInt(employee -> employee.getAttendedCalls().size()).sum());
    }

    private static List<ObjCall> buildCallList() {
        return BuildingStructures.listClientCalls(CALL_AMOUNT);
    }

}
