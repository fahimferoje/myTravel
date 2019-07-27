/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.util;

import com.mytravel.model.Status;
import com.mytravel.model.StatusForm;
import com.mytravel.model.User;
import com.mytravel.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        statusForm.setUserName(status.getUser().getName() + " " + status.getUser().getLastName());
        statusForm.setId(status.getId());
        
        statusForm.setLocationId(status.getLocation().getId());
        
        statusForm.setStatusVisibility(status.getStatusVisibility().ordinal());
        
        return statusForm;
    }
    
    public static User getLoggedInuser(UserService userService) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        return user;
    }

    public static List<StatusForm> getPublicStatuses(UserService userService) {
        
        List<Status> publicStatuses = userService.getAllPublicStatuses();
                
        List<StatusForm> statusForms = publicStatuses.stream()
                .map(st -> toStatusForm(st))
                .collect(Collectors.toList());
        
        return statusForms;
        
    }

    public static StatusForm findPinnedStatus(List<StatusForm> statusForms) {
        
        Optional<StatusForm> pinnedStatusFormOp = statusForms
                .stream()
                .filter(st -> st.isPinnedStatus())
                .findFirst();
        
        return pinnedStatusFormOp.isPresent() ? pinnedStatusFormOp.get() : null;
                
    }
    
    public static void removePinnedStatus(StatusForm pinnedStatusToRemove, List<StatusForm> statusForms) {
        
        if(pinnedStatusToRemove == null){
            return;
        }
        
        statusForms.removeIf(sf -> sf.getId() == pinnedStatusToRemove.getId());
    }
    
    
    
    
}
