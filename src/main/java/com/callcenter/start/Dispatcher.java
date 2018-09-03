package com.callcenter.start;

import com.callcenter.constants.Constants;
import com.callcenter.objects.ObjCall;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author hgutierrez
 */
public class Dispatcher implements Runnable {

    private Boolean active;
    private ExecutorService executorService;
    private ConcurrentLinkedDeque<Employee> employees;
    private ConcurrentLinkedDeque<ObjCall> incomingCalls;
    private AvailabilityEmployees availabilityEmployees;

    public Dispatcher(List<Employee> employees) {
        this(employees, new AvailabilityEmployees());
    }

    public Dispatcher(List<Employee> employees, AvailabilityEmployees availabilityEmployees) {
        Validate.notNull(employees);
        Validate.notNull(availabilityEmployees);
        this.employees = new ConcurrentLinkedDeque(employees);
        this.availabilityEmployees = availabilityEmployees;
        this.incomingCalls = new ConcurrentLinkedDeque<>();
        this.executorService = Executors.newFixedThreadPool(Constants.NUMBER_THREAD);
    }

    public synchronized void dispatch(ObjCall call) {
        System.out.println("Duration call " + call.getDurationInSeconds());
        this.incomingCalls.add(call);
    }

   
    public synchronized void start() {
        this.active = true;
        this.employees.forEach((employee) -> {
            this.executorService.execute(employee);
        });
    }

   
    public synchronized void stop() {
        this.active = false;
        this.executorService.shutdown();
    }

    public synchronized Boolean getActive() {
        return active;
    }

    /**
     * This is the method that runs on the thread.
     * If the incoming calls queue is not empty, then it searches for and available employee to take the call.
     * Calls will queue up until some workers becomes available.
     */
    @Override
    public void run() {
        while (getActive()) {
            if (this.incomingCalls.isEmpty()) {
                continue;
            } else {
                Employee employee = this.availabilityEmployees.findEmployee(this.employees);
                if (employee == null) {
                    System.out.println("**********************this is no employee***************************");
                    continue;
                }else
                {
                System.out.println("My employee is" + employee.getEmployeeType() + "");
                }
                ObjCall call = this.incomingCalls.poll();
                try {
                    employee.attend(call);
                } catch (Exception e) {
                    this.incomingCalls.addFirst(call);
                }
            }
        }
    }
}
