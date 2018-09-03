/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.useful;

import com.callcenter.enumerator.EmployeeState;
import com.callcenter.enumerator.EmployeeType;
import com.callcenter.objects.ObjCall;
import com.callcenter.objects.ObjEmployee;
import com.callcenter.useful.Useful;
import com.callcenter.constants.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author hgutierrez
 */
public class BuildingStructures {
    
  private static List<ObjEmployee> listEmployees = new ArrayList<>();
   

   public static List<ObjEmployee> listEmployees(Integer numberOperators, EmployeeType type)
   {
      int i=0;
        ObjEmployee employee = null;
       while(i < numberOperators )
      {
        employee = new ObjEmployee();
        employee.setEmployeeType(type);
        employee.setEmployeeState(EmployeeState.AVAILABLE);
        listEmployees.add(employee);
        i++;
      }
     
      return listEmployees;
   }
   
   
   public static List<ObjCall> listClientCalls(Integer numberCalls)
   {
     List<ObjCall> listClientCalls = new ArrayList<>();
      int i=0;
        ObjCall clientCall = null;
       while(i< numberCalls )
      {
        clientCall = new ObjCall(Useful.returnDuractionCall(Constants.MIN_DURATION_CALL, Constants.MAX_DURATION_CALL));
        //clientCall.setDateCall(new Date());
        listClientCalls.add(clientCall);
        i++;
      }
     
      return listClientCalls;
   }

    
}
