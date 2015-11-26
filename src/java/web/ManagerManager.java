/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.ManagerDTO;
import ejbs.ManagerBean;
import entities.Event;
import entities.Manager;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

/**
 *
 * @author ITWannaBe
 */
@ManagedBean
@SessionScoped
public class ManagerManager {
    @EJB
    private ManagerBean managerBean;
   
    //Manager
    private Long manId;
    private String manName;
    private String manEmail;   
    private String manUserName;
    private String manPassword;
    private String manPasswordConfirm;
    
    private List<Manager> managersM;
    private Manager currentManagerM;
    private List<Event> eventsM;
    
    private ManagerDTO currentManager;
    private ManagerDTO newManager;
    
    private Event currentEvent;

    
    public ManagerManager() {
        currentManager = new ManagerDTO();
        currentEvent = new Event();
    }
    
    public List<ManagerDTO> getAllManagers(){
        try {
            return managerBean.getAllManagers();
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    /*
    private ManagerDTO newManager;
    private ManagerDTO currentManager;

    public ManagerManager() {
        newManager = new ManagerDTO();
    }
    
    public List<ManagerDTO> getAllManagers(){
        try {
            return managerBean.getAllManagers();
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    
    public String createManager(){
        try {
            managerBean.createManager(
                    newManager.getName(), 
                    newManager.getEmail(), 
                    newManager.getUsername(), 
                    newManager.getPassword());
            newManager.reset();
            //escolher acção
            return "index?faces-redirect=true";
            //return (String) "Vai para criação de Manager";
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }  
    }

    public String updateManager(){
        try {
            managerBean.updateManager(
                    currentManager.getId(),
                    currentManager.getName(),
                    currentManager.getUsername(), 
                    currentManager.getPassword(),
                    currentManager.getEmail());
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Faz update a Manager";
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }
    
    public void removeManager(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteUserId");
            Long id = (Long) param.getValue();
            managerBean.removeManager(id);
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }
    
    public List<Event> getAllEventsOfCurrentManager(){
        try {
            return managerBean.getAllEventsOfManager(currentManager.getId());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    
    // - G&S - /////////////////////////////////////////////////////////////////////    

    public ManagerDTO getNewManager() {
        return newManager;
    }
    
    public ManagerDTO getCurrentManager() {
        return currentManager;
    }
    
    public void setNewManager(ManagerDTO newManager) {
        this.newManager = newManager;
    }

    public void setCurrentManager(ManagerDTO currentManager) {
        this.currentManager = currentManager;
    }
    
    /*
    public List<Event> getAllEventsOfCurrentManager(){
        try {
            return managerBean.getAllEventsOfManager(currentManager.getId());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    /*
    public List<Manager> getAllManagers(){
        try {
            this.managersM = managerBean.getAllManagers();
            return managersM; 
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    */
    
    public String createManager(){
        try {if(manPassword.equals(manPasswordConfirm)){
            managerBean.createManager(manName, manEmail, manUserName, manPassword);
            clearNewManager();
            //escolher acção
            //return (String) "index?faces-redirect=true";
           return "administrator_panel?faces-redirect=true";
            
            }
            
            return "administrator_create?faces-redirect=true";
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }  
    }
    
    public String updateManager(){
        try {
            //managerBean.updateManager(manId, manName, manEmail, manUserName, manPassword);
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Faz update a Manager";
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }

    public void removeManager(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteManagerId");
            Long id = (Long) param.getValue();
            //managerBean.removeManager(id);
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }
   
    private void clearNewManager() {
        manName = null;
        manEmail = null;
        manUserName = null;
        manPassword = null;
    }
    
   
    /*
    public List<Event> getAllEventsOfCurrentManager(Manager currentManager){
        try {
            this.eventsM = managerBean.getAllEventsOfManager(currentManager);
            return eventsM; 
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    */

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }
    
    public ManagerDTO getNewManager() {
        return newManager;
    }
    
    public ManagerDTO getCurrentManager() {
        return currentManager;
    }
    
    public void setNewManager(ManagerDTO newManager) {
        this.newManager = newManager;
    }

    public void setCurrentManager(ManagerDTO currentManager) {
        this.currentManager = currentManager;
    }
    
    
     public Long getManId() {
        return manId;
    }

    public void setManId(Long manId) {
        this.manId = manId;
    }

    public String getManName() {
        return manName;
    }

    public void setManName(String manName) {
        this.manName = manName;
    }

    public String getManEmail() {
        return manEmail;
    }

    public void setManEmail(String manEmail) {
        this.manEmail = manEmail;
    }

    public String getManUserName() {
        return manUserName;
    }

    public void setManUserName(String manUserName) {
        this.manUserName = manUserName;
    }

    public String getManPassword() {
        return manPassword;
    }

    public void setManPassword(String manPassword) {
        this.manPassword = manPassword;
    }
    
    public List<Manager> getManagersM() {
        return managersM;
    }

    public void setManagersM(List<Manager> managersM) {
        this.managersM = managersM;
    }
    
     public Manager getCurrentManagerM() {
        return currentManagerM;
    }

    public void setCurrentManagerM(Manager currentManagerM) {
        this.currentManagerM = currentManagerM;
    }

    public String getManPasswordConfirm() {
        return manPasswordConfirm;
    }

    public void setManPasswordConfirm(String manPasswordConfirm) {
        this.manPasswordConfirm = manPasswordConfirm;
    }
}
