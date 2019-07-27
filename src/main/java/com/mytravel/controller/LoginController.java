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
import com.mytravel.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
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
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView showHome(){
        ModelAndView modelAndView = new ModelAndView();
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("locations", userService.findAllLocations());
        modelAndView.addObject("statusForm",new StatusForm());
        modelAndView.setViewName("profile");
        return modelAndView;
    }
    
    @RequestMapping(value="/profile", method = RequestMethod.POST)
    public ModelAndView createStatus(@ModelAttribute StatusForm statusForm) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        List<Location> locations = userService.findAllLocations();
                
        userService.saveStatus(statusForm, user, locations);
        
        System.out.println("Fin "+userService.getAllStatusByUserId(user.getId()).size());
        
        List<Status> statuses = userService.getAllStatusByUserId(user.getId());
        
        List<StatusForm> statusForms = statuses.stream()
                .map(st -> toStatusForm(st))
                .collect(Collectors.toList());
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("locations", locations);
        modelAndView.addObject("allStatuses", statusForms);
        modelAndView.addObject("statusForm",new StatusForm());
        modelAndView.setViewName("profile");
        return modelAndView;
    }
    
    private StatusForm toStatusForm(Status status) {
        
        StatusForm statusForm = new StatusForm();
        statusForm.setLocation(status.getLocation().getName());
        statusForm.setStatusVisibilityString(
                status.getStatusVisibility().equals(Status.StatusVisibility.PRIVATE) ? "Private" : "Public");
        statusForm.setStatusDesc(status.getStatusDescription());
        
        return statusForm;
    }
    


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        System.out.println("Registration get method");
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        System.out.println("Registration");
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
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
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


}
