/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AttendantDTO;
import dtos.CategoryDTO;
import dtos.EventDTO;
import ejbs.AttendantBean;
import ejbs.CategoryBean;
import ejbs.EventBean;
import entities.Administrator;
import entities.Attendant;
import entities.Category;
import entities.Event;
import exceptions.AttendantEnrolledException;
import exceptions.AttendantNotEnrolledException;
import exceptions.EntityDoesNotExistsException;
import exceptions.EventEnrolledException;
import exceptions.EventNotEnrolledException;
import exceptions.MyConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class EventManager {

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

    //Attendant
    private Long atId;
    private String atName;
    private String atEmail;
    private String atUserName;
    private String atPassword;

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
    private List<Attendant> attendantsM;
    private List<Event> eventsM;
    
    private Attendant currentAttendantM;
    private EventDTO currentEvent;
    

    private Administrator currentAdministratorM;
    private List<String> attendantsSelected;
    private List<String> categoriesSelected;
    private List<Category> categoriesM;

    private List<AttendantDTO> attendantsDisponiveisSelected = new ArrayList<>();

    public EventManager() {
      
    }

    public EventDTO getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(EventDTO currentEvent) {
        this.currentEvent = currentEvent;
    }

    
    public List<String> getCategoriesSelected() {
        return categoriesSelected;
    }

    public void setCategoriesSelected(List<String> categoriesSelected) {
        this.categoriesSelected = categoriesSelected;
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

    public Attendant getCurrentAttendantM() {
        return currentAttendantM;
    }

    public void setCurrentAttendantM(Attendant currentAttendantM) {
        this.currentAttendantM = currentAttendantM;
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

    public String createEvent() {
        try {
            if (evStartDate.matches("^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29"
                    + "(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]"
                    + "|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1"
                    + "(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))(|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$")
                    && evFinishDate.matches("^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29"
                            + "(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]"
                            + "|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1"
                            + "(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))(|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$")) {

                eventBean.createEvent(evName, evDescription, evStartDate, evFinishDate);
                currentEvent = eventBean.getAllEvents().get(eventBean.getAllEvents().size() - 1);
                //addCategoriesList();
                clearNewEvent();
                categoriesM.clear();
                //escolher acção
                //return (String) "index?faces-redirect=true";
                return "administrator_panel?faces-redirect=true";
            }
            return "event_create?faces-redirect=true";
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public List<EventDTO> getAllEvents() {
        try {
            return eventBean.getAllEvents();
            
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public String updateEvent() throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            eventBean.updateEvent(currentEvent.getId(), currentEvent.getName(), currentEvent.getDescription(), currentEvent.getStartDate(),currentEvent.getFinishDate());
           // eventBean.updateEvent(evId, evName, evStartDate, evFinishDate);
            //escolher acção
            //return (String) "index?faces-redirect=true";
            return "event_lists?faces-redirect=true";
        } catch (NumberFormatException ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void removeEvent(ActionEvent event) throws EntityDoesNotExistsException {
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
  
    public List<EventDTO> getAllEventsOfCurrentAttendant(Attendant currentAttendant) {
        try {
            return attendantBean.getAllEventsOfAttendant(currentAttendant.getId());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
/*
    public List<CategoryDTO> getAllCategoriesOfCurrentAttendant(Attendant currentAttendant) {
        try {
            return attendantBean.getAllCategoriesOfAttendant(currentAttendant);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
*/
    public List<CategoryDTO> getAllCategoriesOfCurrentEvent() {
        try {
            return eventBean.getAllCategoriesOfEvent(currentEvent.getId());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    //TESTE 2
    public List<String> getAttendantsSelected() {
        return attendantsSelected;
    }

    public void setAttendantsSelected(List<String> attendantsSelected) {
        this.attendantsSelected = attendantsSelected;
    }

    //retorna os attendants actuais do evento
    public List<AttendantDTO> getAttendantsDisponiveisSelected() {
        actualizarAttendantsSelected();
        return attendantsDisponiveisSelected;
    }

    public String addAttendantsList() throws EntityDoesNotExistsException, AttendantNotEnrolledException, AttendantEnrolledException {
        System.out.println("Eeeeeeeeevent ID: ");
        for (AttendantDTO att1 : eventBean.getEventAttendants(currentEvent.getName())) {
            attendantBean.unrollAttendantInEvent(att1.getId(), currentEvent.getId());
        }
        eventBean.clearAllAttendantsInEvent(currentEvent.getName());
        
        for (String str : attendantsSelected) {
            System.err.println("STRINGG: " + str);
            System.out.println("LALALAL: " + attendantBean.getAttendantByName(str));
            //System.out.println(currentEvent.getId());
            
            attendantBean.enrollAttendantInEvent((attendantBean.getAttendantByName(str)).getId(), currentEvent.getId());
        }
        actualizarAttendantsSelected();
        return "event_add_attendants?faces-redirect=true";
    }

    public void actualizarAttendantsSelected() {

        attendantsDisponiveisSelected.clear();
        if (eventBean.getEventAttendants(currentEvent.getName()).isEmpty()) {
            for (AttendantDTO att : attendantBean.getAllAttendants()) {
                attendantsDisponiveisSelected.add(att);
            }
            //  System.out.println("Disponiveis PAra selecao Se estava vazia:" + attendantsDisponiveisSelected);
        } else {
            for (AttendantDTO att : attendantBean.getAllAttendants()) {
                attendantsDisponiveisSelected.add(att);
            }

        }
    }
    
    public List<AttendantDTO> getAllEventAttendants(){
        
        return eventBean.getEventAttendants(currentEvent.getName());
    }
    
    public String updateEventCategories() throws EntityDoesNotExistsException, EventNotEnrolledException, EventEnrolledException {
        for (CategoryDTO cat : eventBean.getAllCategoriesOfEvent(currentEvent.getId())) {
            
            
            eventBean.unrollEventInCategory(currentEvent.getId(), cat.getId());
        }
        //currentEvent.
        eventBean.clearAllCategoriesInEvent(currentEvent.getId());

        for (String str : categoriesSelected) {
            eventBean.enrollEventInCategory(currentEvent.getId(), (categoryBean.getCategoryByName(str)).getId());
        }
        //categoriesM.clear();
        return "event_lists?faces-redirect=true";
    }

    //para lista das categorias
    public String addCategoriesList() throws EntityDoesNotExistsException, EventEnrolledException {

        for (String str : categoriesSelected) {
            eventBean.enrollEventInCategory(currentEvent.getId(), (categoryBean.getCategoryByName(str)).getId());
        }
        return "administrator_panel?faces-redirect=true";
    }
}
