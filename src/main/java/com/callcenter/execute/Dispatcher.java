package com.callcenter.execute;

import com.callcenter.constants.Constants;
import com.callcenter.objects.ObjCall;
import com.callcenter.objects.ObjEmployee;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author hgutierrez
 */
public class Dispatcher implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

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
        logger.info("Dispatch new call of " + call.getDurationInSeconds() + " seconds");
        this.incomingCalls.add(call);
    }

    /**
     * Starts the employee threads and allows the dispatcher run method to execute
     */
    public synchronized void start() {
        this.active = true;
        for (Employee employee : this.employees) {
            this.executorService.execute(employee);
        }
    }

    /**
     * Stops the employee threads and the dispatcher run method immediately
     */
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
                    logger.error(e.getMessage());
                    this.incomingCalls.addFirst(call);
                }
            }
        }
    }

}
