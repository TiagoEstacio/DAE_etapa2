/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;
import dtos.ManagerDTO;
import ejbs.AdministratorBean;
import ejbs.AttendantBean;
import ejbs.CategoryBean;
import ejbs.EventBean;
import ejbs.ManagerBean;
import entities.Administrator;
import entities.Attendant;
import entities.Category;
import entities.Event;
import entities.Manager;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class AttendantManager {
   
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private ManagerBean managerBean;
    @EJB
    private AttendantBean attendantBean;
    @EJB
    private EventBean eventBean;
    @EJB
    private CategoryBean categoryBean;
    
    
    //Administrator
    private Long adminId;
    private String adminName;
    private String adminEmail;   
    private String adminUserName;
    private String adminPassword;
    
    //Manager
    private Long manId;
    private String manName;
    private String manEmail;   
    private String manUserName;
    private String manPassword;
    
    //Attendant
    private Long atId;
    private String atName;
    private String atEmail;   
    private String atUserName;
    private String atPassword;
    private String atPasswordConfirm;

   
    
    //Event
    private Long evId;
    private String evName;
    private String evDescription;
    private String evStartDate;
    private String evFinishDate;
    
    //Category
    private Long catId;
    private String catName;
    
    //Outras
    private List<Administrator> administratorsM;
    private List<Manager> managersM;
    private List<Attendant> attendantsM;
    private List<Event> eventsM;
    private List<Category> categoriesM;
    private Manager currentManagerM;
    private Attendant currentAttendantM;
    private Event currentEventM;
    private Administrator currentAdministratorM;
    
    private ManagerDTO currentManager;

    public AttendantManager() {
    }
   
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
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

    public Long getAtId() {
        return atId;
    }

    public void setAtId(Long atId) {
        this.atId = atId;
    }

    public String getAtName() {
        return atName;
    }

    public void setAtName(String atName) {
        this.atName = atName;
    }

    public String getAtEmail() {
        return atEmail;
    }

    public void setAtEmail(String atEmail) {
        this.atEmail = atEmail;
    }

    public String getAtUserName() {
        return atUserName;
    }

    public void setAtUserName(String atUserName) {
        this.atUserName = atUserName;
    }

    public String getAtPassword() {
        return atPassword;
    }

    public void setAtPassword(String atPassword) {
        this.atPassword = atPassword;
    }

    public Long getEvId() {
        return evId;
    }

    public void setEvId(Long evId) {
        this.evId = evId;
    }

    public String getEvName() {
        return evName;
    }

    public void setEvName(String evName) {
        this.evName = evName;
    }

    public String getEvStartDate() {
        return evStartDate;
    }

    public void setEvStartDate(String evStartDate) {
        this.evStartDate = evStartDate;
    }

    public String getEvFinishDate() {
        return evFinishDate;
    }

    public void setEvFinishDate(String evFinishDate) {
        this.evFinishDate = evFinishDate;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<Administrator> getAdministratorsM() {
        return administratorsM;
    }

    public void setAdministratorsM(List<Administrator> administratorsM) {
        this.administratorsM = administratorsM;
    }

    public List<Manager> getManagersM() {
        return managersM;
    }

    public void setManagersM(List<Manager> managersM) {
        this.managersM = managersM;
    }

    public List<Attendant> getAttendantsM() {
        return attendantsM;
    }

    public void setAttendantsM(List<Attendant> attendantsM) {
        this.attendantsM = attendantsM;
    }

    public List<Event> getEventsM() {
        return eventsM;
    }

    public void setEventsM(List<Event> eventsM) {
        this.eventsM = eventsM;
    }

    public List<Category> getCategoriesM() {
        return categoriesM;
    }

    public void setCategoriesM(List<Category> categoriesM) {
        this.categoriesM = categoriesM;
    }

    public Manager getCurrentManagerM() {
        return currentManagerM;
    }

    public void setCurrentManagerM(Manager currentManagerM) {
        this.currentManagerM = currentManagerM;
    }

    public Attendant getCurrentAttendantM() {
        return currentAttendantM;
    }

    public void setCurrentAttendantM(Attendant currentAttendantM) {
        this.currentAttendantM = currentAttendantM;
    }

    public Event getCurrentEventM() {
        return currentEventM;
    }

    public void setCurrentEventM(Event currentEventM) {
        this.currentEventM = currentEventM;
    }
    public Administrator getCurrentAdministratorM() {
        return currentAdministratorM;
    }

    public void setCurrentAdministratorM(Administrator currentAdministratorM) {
        this.currentAdministratorM = currentAdministratorM;
    }
    
    public String getEvDescription() {
        return evDescription;
    }

    public void setEvDescription(String evDescription) {
        this.evDescription = evDescription;
    }
     public String getAtPasswordConfirm() {
        return atPasswordConfirm;
    }

    public void setAtPasswordConfirm(String atPasswordConfirm) {
        this.atPasswordConfirm = atPasswordConfirm;
    }
    
/*
    public String createAdministrator(){
        try {
            administratorBean.createAdministrator(adminName, adminEmail, adminUserName, adminPassword);
            clearNewAdministrator();
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Vai para criação de Administrador";
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }  
    }
    
    public List<Administrator> getAllAdministrators(){
        try {
            this.administratorsM = administratorBean.getAllAdministrators();
            return administratorsM; 
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    } 
    
    public String updateAdministrator(){
        try {
            administratorBean.updateAdministrator(adminId, adminName, adminEmail, adminUserName, adminPassword);
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Faz update a Administrador";
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }

    public void removeAdministrator(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteAdministratorId");
            Long id = (Long) param.getValue();
            administratorBean.removeAdministrator(id);
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }
   
    private void clearNewAdministrator() {
        adminName = null;
        adminEmail = null;
        adminUserName = null;
        adminPassword = null;
    }
    
    public String createManager(){
        try {
            managerBean.createManager(manName, manEmail, manUserName, manPassword);
            clearNewManager();
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Vai para criação de Manager";
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
    /*
    public String updateManager(){
        try {
            managerBean.updateManager(manId, manName, manEmail, manUserName, manPassword);
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
            managerBean.removeManager(id);
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
    
    public String createAttendant(){
        try {
            if(atPassword.equals(atPasswordConfirm)){
            attendantBean.createAttendant(atName, atEmail, atUserName, atPassword);
            clearNewAttendant();
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return "administrator_panel?faces-redirect=true";
            
            }
            
            return "administrator_create?faces-redirect=true";
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }  
    }
    
    public List<Attendant> getAllAttendants(){
        try {
            this.attendantsM = attendantBean.getAllAttendants();
            return attendantsM; 
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    
    public String updateAttendat(){
        try {
            attendantBean.updateAttendant(atId, atName, atEmail, atUserName, atPassword);
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Faz update a Attendant";
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }

    public void removeAttendant(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteAttendantId");
            Long id = (Long) param.getValue();
            attendantBean.removeAttendant(id);
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }
   
    private void clearNewAttendant() {
        atName = null;
        atEmail = null;
        atUserName = null;
        atPassword = null;
    }
    
    public String createEvent(){
        try {
            eventBean.createEvent(evName,evDescription, evStartDate, evFinishDate);
            clearNewEvent();
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Vai para criação de Event";
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }  
    }
    
    public List<Event> getAllEvents(){
        try {
            this.eventsM = eventBean.getAllEvents();
            return eventsM; 
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    
    public String updateEvent(){
        try {
            eventBean.updateEvent(evId, evName, evStartDate, evFinishDate);
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Faz update a Event";
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }

    public void removeEvent(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteEventId");
            Long id = (Long) param.getValue();
            eventBean.removeEvent(id);
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }
   
    private void clearNewEvent() {
        evName = null;
        evStartDate = null;
        evFinishDate = null;
    }
    
    public String createCategory(){
        try {
            categoryBean.createCategory(catName);
            clearNewCategory();
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Vai para criação de Category";
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }  
    }
    
    public List<Category> getAllCategories(){
        try {
            this.categoriesM = categoryBean.getAllCategories();
            return categoriesM; 
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    
    public String updateCategory(){
        try {
            categoryBean.updateCategory(catId, catName);
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return (String) "Faz update a Category";
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }

    public void removeCategory(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteCategoryId");
            Long id = (Long) param.getValue();
            categoryBean.removeCategory(id);
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage()); 
        }
    }
   
    private void clearNewCategory() {
        catName = null;
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
    
    public List<Event> getAllEventsOfCurrentAttendant(Attendant currentAttendant) {
        try {
            this.eventsM = attendantBean.getAllEventsOfAttendant(currentAttendant);
            return eventsM;  
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public List<Category> getAllCategoriesOfCurrentAttendant(Attendant currentAttendant) {
        try {
            this.categoriesM = attendantBean.getAllCategoriesOfAttendant(currentAttendant);
            return categoriesM;   
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public List<Category> getAllCategoriesOfCurrentEvent(Event currentEvent) {
        try {
            this.categoriesM = eventBean.getAllCategoriesOfEvent(currentEvent);
            return categoriesM;  
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    
    public List<ManagerDTO> getAllManagers(){
        try {
            return managerBean.getAllManagers();
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
    }
    
    
    public List<Event> getAllEventsOfCurrentManager(){
        try {
      //      return managerBean.getAllEventsOfManager(currentManager.getId());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());       
        }
        return null;
    }
    
    */
}
