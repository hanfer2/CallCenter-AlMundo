/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.objects;

import com.callcenter.enumerator.EmployeeState;
import com.callcenter.enumerator.EmployeeType;

/**
 *
 * @author hgutierrez
 */
public class ObjEmployee {
    
        private EmployeeType employeeType;
        //this atribute  have the priority for employee
	private int id;
	private EmployeeState employeeState;
        
	public ObjEmployee(EmployeeType employeeType, int id) {
		this.employeeType = employeeType;
		this.id = id;
	}

        public ObjEmployee(EmployeeType employeeType, EmployeeState employeeState)
        {
             this.employeeType = employeeType;
              this.employeeState = employeeState;
        }
        
         public ObjEmployee()
        {
            
        }
        
    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeState getEmployeeState() {
        return employeeState;
    }

    public void setEmployeeState(EmployeeState employeeState) {
        this.employeeState = employeeState;
    }
	
    
}
