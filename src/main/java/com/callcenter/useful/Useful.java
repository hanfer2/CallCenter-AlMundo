/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.useful;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author hgutierrez
 */
public class Useful {
    
    public static Integer returnDuractionCall(Integer minValue, Integer MaxValue)
    {
    
      Integer duration = ThreadLocalRandom.current().nextInt(minValue, MaxValue + 1);
      return duration;
    }
    
    
    
}
