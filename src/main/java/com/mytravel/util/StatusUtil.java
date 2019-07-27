/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.util;

import com.mytravel.model.Status;
import com.mytravel.model.StatusForm;
import com.mytravel.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Fahim Feroje Al Jami
 */
public class StatusUtil {
    
    public static List<StatusForm> getStatusesAsStatusFormList(UserService userService, int usrId) {
        
        List<Status> statuses = userService.getAllStatusByUserId(usrId);
        
        List<StatusForm> statusForms = statuses.stream()
                .map(st -> toStatusForm(st))
                .collect(Collectors.toList());
        
        return statusForms;
    }
    
    public static StatusForm toStatusForm(Status status) {
        
        StatusForm statusForm = new StatusForm();
        statusForm.setLocation(status.getLocation().getName());
        statusForm.setStatusVisibilityString(
                status.getStatusVisibility().equals(Status.StatusVisibility.PRIVATE) ? "Private" : "Public");
        statusForm.setStatusDesc(status.getStatusDescription());
        
        return statusForm;
    }
    
    
}
