/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.objects;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author hgutierrez
 */
public class ObjCall {
      private Integer durationInSeconds;

      
    public ObjCall(Integer durationInSeconds) {
        Validate.notNull(durationInSeconds);
        Validate.isTrue(durationInSeconds >= 0);
        this.durationInSeconds = durationInSeconds;
    }
      
    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
      
      
}
