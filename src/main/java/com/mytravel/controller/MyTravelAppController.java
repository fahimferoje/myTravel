/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.controller;

import com.mytravel.model.Location;
import com.mytravel.model.Status;
import com.mytravel.model.StatusForm;
import com.mytravel.model.User;
import com.mytravel.service.MyTravelAppService;
import com.mytravel.util.StatusUtil;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyTravelAppController {

    @Autowired
    private MyTravelAppService myTravelAppService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView showHome(){
        
        ModelAndView modelAndView = new ModelAndView();         
        
        modelAndView.addObject("publicStatuses", StatusUtil.getPublicStatuses(myTravelAppService));       
        
        modelAndView.setViewName("home");
        return modelAndView;
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    
    @RequestMapping(value="/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(){
        
        User user = StatusUtil.getLoggedInuser(myTravelAppService);
        
        List<StatusForm> statusForms = StatusUtil.getStatusesAsStatusFormList(myTravelAppService, user.getId());
        
        StatusForm pinnedStatusForm = myTravelAppService.findPinnedStatus(user.getId());
                
        StatusUtil.removePinnedStatus(pinnedStatusForm, statusForms);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("locations", myTravelAppService.findAllLocations());
        modelAndView.addObject("allStatuses", statusForms);
        modelAndView.addObject("statusForm",new StatusForm());
        modelAndView.addObject("pinnedStatusForm", pinnedStatusForm);
        modelAndView.setViewName("profile");
        return modelAndView;
    }
    
    @RequestMapping(value="/profile", method = RequestMethod.POST)
    public ModelAndView createStatus(@ModelAttribute StatusForm statusForm) {

        User user = StatusUtil.getLoggedInuser(myTravelAppService);
        
        List<Location> locations = myTravelAppService.findAllLocations();
                
        myTravelAppService.saveStatus(statusForm, user, locations);
        
        List<StatusForm> statusForms = StatusUtil.getStatusesAsStatusFormList(myTravelAppService, user.getId());
        
        StatusForm pinnedStatusForm = myTravelAppService.findPinnedStatus(user.getId());
        
        StatusUtil.removePinnedStatus(pinnedStatusForm, statusForms);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("locations", locations);
        modelAndView.addObject("allStatuses", statusForms);
        modelAndView.addObject("statusForm",new StatusForm());
        modelAndView.addObject("pinnedStatusForm", pinnedStatusForm);
        modelAndView.setViewName("profile");
        return modelAndView;
    }
    
    @RequestMapping(value="/profile", params = {"statusId", "oldPinnedStatusId"}, method = RequestMethod.POST)
    public ModelAndView makePinnedStatus(@RequestParam int statusId
            , @RequestParam int oldPinnedStatusId) {

        User user = StatusUtil.getLoggedInuser(myTravelAppService);
                
        List<Location> locations = myTravelAppService.findAllLocations();
                
        myTravelAppService.makePinnedOrUnpinned(statusId, true);
        
        if(oldPinnedStatusId != 0){
            myTravelAppService.makePinnedOrUnpinned(oldPinnedStatusId, false);
        }
        
        Status pinnedStatus = myTravelAppService.getStatusById(statusId);
        
        List<StatusForm> statusForms = StatusUtil.getStatusesAsStatusFormList(myTravelAppService, user.getId());
        
        StatusForm pinnedStForm = StatusUtil.toStatusForm(pinnedStatus);
        
        StatusUtil.removePinnedStatus(pinnedStForm, statusForms);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("locations", locations);
        modelAndView.addObject("allStatuses", statusForms);
        modelAndView.addObject("statusForm",new StatusForm());
        modelAndView.addObject("pinnedStatusForm", pinnedStForm);
        modelAndView.setViewName("profile");
        return modelAndView;
    }   


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    
    @RequestMapping(value="/editStatus", method = RequestMethod.POST)
    public ModelAndView editStatus(@RequestParam (value="statusId") int statusId, @ModelAttribute StatusForm statusForm){
        
        ModelAndView modelAndView = new ModelAndView();
                
        Status status = myTravelAppService.getStatusById(statusId);
                
        if(status == null){
            modelAndView.setViewName("editerror");
            return modelAndView;
        }
                        
                
        modelAndView.addObject("statusToEdit", StatusUtil.toStatusForm(status));
        modelAndView.addObject("locations", myTravelAppService.findAllLocations());

        modelAndView.setViewName("editstatus");
        return modelAndView;
    }
    
    @RequestMapping(value="/profile", params ={"editedStatusDesc", "visibilitygrp" 
            ,"statusToEditId", "locationId"}
            , method = RequestMethod.POST)
    public ModelAndView editStatusToDb(
            @RequestParam String editedStatusDesc,
            @RequestParam int statusToEditId,
            @RequestParam int visibilitygrp,
            @RequestParam int locationId) {
        
        List<Location> locations = myTravelAppService.findAllLocations();
        
        int loggedInUserId = StatusUtil.getLoggedInuser(myTravelAppService).getId();
        
        ModelAndView modelAndView = new ModelAndView();
        
        StatusForm statusForm = new StatusForm();  
        statusForm.setId(statusToEditId);
        statusForm.setStatusVisibility(visibilitygrp);
        statusForm.setLocationId(locationId);
        statusForm.setStatusDesc(editedStatusDesc);
                
        myTravelAppService.editStatus(statusForm, locations);
        
        List<StatusForm> statusForms = StatusUtil.getStatusesAsStatusFormList(myTravelAppService
                , loggedInUserId);
        
        StatusForm pinnedStatusForm = myTravelAppService.findPinnedStatus(loggedInUserId);
                
        StatusUtil.removePinnedStatus(pinnedStatusForm, statusForms);
        
        modelAndView.addObject("locations", locations);
        modelAndView.addObject("allStatuses", StatusUtil.getStatusesAsStatusFormList(myTravelAppService
                , StatusUtil.getLoggedInuser(myTravelAppService).getId()));
        modelAndView.addObject("statusForm",new StatusForm());
        modelAndView.addObject("pinnedStatusForm", pinnedStatusForm);

        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = myTravelAppService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            myTravelAppService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = myTravelAppService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


}
