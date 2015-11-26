
package ejbs;

import dtos.AttendantDTO;
import dtos.EventDTO;
import entities.Category;
import entities.Event;
import entities.Attendant;
import exceptions.AttendantEnrolledException;
import exceptions.AttendantNotEnrolledException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class AttendantBean {

    @PersistenceContext
    private EntityManager em;
    
    public void createAttendant (String username, String password, String name, String email) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllAttendants").getResultList();
            for (Attendant a : attendants){
                if (username.equals(a.getUserName())){
                    throw new EntityAlreadyExistsException("A attendant with that username already exists.");  
                }
            }
            Attendant attendant = new Attendant (username, password, name, email);
            em.persist(attendant);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
           throw new EJBException(e.getMessage());
        }
    }
    
    public List<Attendant> getAll() {
        try {
            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllAttendants").getResultList();
            return attendants;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<AttendantDTO> getAllAttendants() {
        try {
            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllAttendants").getResultList();
            return attendantsToDTOs(attendants);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    /**
     * Devolve um attendant por username(único) 
     * @param username
     * @return attendantDTO
     */
    public AttendantDTO getAttendant(String username) {
        try {
            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllAttendants").getResultList();
            for (Attendant att : attendants){
                
                if (username.equals(att.getUserName())){
                    return attendantToDTO(att); 
                }
            }
            return null;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    /**
     * Devolve um attendant por nome (não único)
     * @param name
     * @return attendantDTO
     */
    public AttendantDTO getAttendantByName(String name) {
        try {
            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllAttendants").getResultList();
            for (Attendant att : attendants){
            
                if (name.equals(att.getName())){
                    return getAttendant(att.getUserName());
                }
            }
            return null;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void updateAttendant (Long id, String username, String password, String name, String email)throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            Attendant attendant = em.find(Attendant.class, id);
            if (attendant == null){
                throw new EntityDoesNotExistsException("There is no attendant with that id.");
            }
//            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllAttendants").getResultList();
//            for (Attendant a : attendants){
//                if (username.equals(a.getUserName())){
//                    throw new EntityAlreadyExistsException("That username already exists.");
//                }
//            }
            attendant.setUsername(username);
            attendant.setPassword(password);
            attendant.setName(name);
            attendant.setEmail(email);
            em.merge(attendant);   
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void removeAttendant(Long id) throws EntityDoesNotExistsException {
        try {
            Attendant attendant = em.find(Attendant.class, id);
            if (attendant == null) {
                throw new EntityDoesNotExistsException("There is no attendant with that id.");
            }

            for (Event event : attendant.getEvents()) {
                event.removeAttendant(attendant);
            }
            
            for (Category category : attendant.getCategories()){
                category.removeAttendant(attendant);
            }
            
            em.remove(attendant);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void enrollAttendantInEvent(Long attendantId, Long eventId) throws EntityDoesNotExistsException, AttendantEnrolledException{
        try {
            System.out.println("ATTENDANTID: " + attendantId);
            //System.out.println("EVENTID: " + eventId);
            
            Attendant attendant = em.find(Attendant.class, attendantId);
            if (attendant == null) {
                throw new EntityDoesNotExistsException("There is no attendant with that id: " + attendantId);
            }

            Event event = em.find(Event.class, eventId);
            if (event == null) {
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }

            if (event.getAttendants().contains(attendant)) {
                throw new AttendantEnrolledException("Attendant is already enrolled in that event.");
            }
            
            event.addAttendant(attendant); 
            attendant.addEvent(event);

        } catch (EntityDoesNotExistsException | AttendantEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollAttendantInEvent(Long attendantId, Long eventId) throws EntityDoesNotExistsException, AttendantNotEnrolledException {
        try {
            Event event = em.find(Event.class, eventId);
            if(event == null){
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }            
            
            Attendant attendant = em.find(Attendant.class, attendantId);
            if(attendant == null){
                throw new AttendantNotEnrolledException("There is no attendant with that id.");
            }
            
            if(!event.getAttendants().contains(attendant)){
                throw new AttendantNotEnrolledException("Attendant is not enrolled in that event.");
            }
          
            event.removeAttendant(attendant);
            attendant.removeEvent(event);

        } catch (EntityDoesNotExistsException | AttendantNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<AttendantDTO> getEnrolledAttendantsInEvents(Long id) throws EntityDoesNotExistsException{
        try {
            Event event = em.find(Event.class, id);
            if( event == null){
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }            
            List<Attendant> attendants = (List<Attendant>) event.getAttendants();
            return attendantsToDTOs(attendants);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<AttendantDTO> getUnrolledAttendantsInEvents(Long id) throws EntityDoesNotExistsException{
        try {
            Event event = em.find(Event.class, id);
            if( event == null){
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }            
            //nao sei se este código está correcto??
            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllEventAttendants")
                    .setParameter("eventId", event.getId())
                    .getResultList();
            //-----------------------------------------------------------------------------------------
            List<Attendant> enrolled = em.find(Event.class, id).getAttendants();
            attendants.removeAll(enrolled);
            return attendantsToDTOs(attendants);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void enrollAttendantInCategory(Long attendantId, Long categoryId) throws EntityDoesNotExistsException, AttendantEnrolledException{
        try {
            Attendant attendant = em.find(Attendant.class, attendantId);
            if (attendant == null) {
                throw new EntityDoesNotExistsException("There is no attendant with that id.");
            }

            Category category = em.find(Category.class, categoryId);
            if (category == null) {
                throw new EntityDoesNotExistsException("There is no categoty with that id.");
            }

            if (category.getAttendants().contains(attendant)) {
                throw new AttendantEnrolledException("Attendant is already enrolled in that category.");
            }

            category.addAttendant(attendant); 
            attendant.addCategory(category);

        } catch (EntityDoesNotExistsException | AttendantEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollAttendantInCategory(Long attendantId, Long categoryId) throws EntityDoesNotExistsException, AttendantNotEnrolledException {
        try {
            Category category = em.find(Category.class, categoryId);
            if(category == null){
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }            
            
            Attendant attendant = em.find(Attendant.class, attendantId);
            if(attendant == null){
                throw new AttendantNotEnrolledException("There is no attendant with that id.");
            }
            
            if(!category.getAttendants().contains(attendant)){
                throw new AttendantNotEnrolledException("Attendant is not enrolled in that category.");
            }
          
            category.removeAttendant(attendant);
            attendant.removeCategory(category);

        } catch (EntityDoesNotExistsException | AttendantNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<AttendantDTO> getEnrolledAttendantsInCategories(Long Id) throws EntityDoesNotExistsException{
        try {
            Category category = em.find(Category.class, Id);
            if( category == null){
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }            
            List<Attendant> attendants = (List<Attendant>) category.getAttendants();
            return attendantsToDTOs(attendants);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<AttendantDTO> getUnrolledAttendantsInCategories(Long id) throws EntityDoesNotExistsException{
        try {
            Category category = em.find(Category.class, id);
            if( category == null){
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }            
            //nao sei se este código está correcto??
            List<Attendant> attendants = (List<Attendant>) em.createNamedQuery("getAllCategoryAttendants")
                    .setParameter("categoryId", category.getId())
                    .getResultList();
            //-----------------------------------------------------------------------------------------
            List<Attendant> enrolled = em.find(Category.class, id).getAttendants();
            attendants.removeAll(enrolled);
            return attendantsToDTOs(attendants);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
   
    AttendantDTO attendantToDTO(Attendant attendant) {
        return new AttendantDTO(
                attendant.getId(),
                attendant.getUserName(),
                null,
                attendant.getName(),
                attendant.getEmail());
    }

    List<AttendantDTO> attendantsToDTOs(List<Attendant> attendants) {
        List<AttendantDTO> dtos = new ArrayList<>();
        for (Attendant a : attendants) {
            dtos.add(attendantToDTO(a));
        }
        return dtos;
    }
    
    //Devolver lista de eventsdto do currentAtendant
//    public List<EventDTO> getAllEventsOfAttendant(Attendant attendant){
//        List<Event> events = attendant.getEvents();
//        return eventsToDTOs(events);
//    }
    
    //Devolver lista de eventsdto do currentAtendant
    public List<EventDTO> getAllEventsOfAttendant(Long id) {
        Attendant att = em.find(Attendant.class, id);
        
        if (att != null) {       
             List<Event> events = new ArrayList<>();
             events = att.getEvents();
             return eventsToDTOs(events);
        }
        return null;
    }
    
    
    EventDTO eventToDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getStartDate(),
                event.getFinishDate());
               
    }
    
    List<EventDTO> eventsToDTOs(List<Event> events) {
        List<EventDTO> dtos = new ArrayList<>();
        for (Event e : events) {
            dtos.add(eventToDTO(e));
        }
        return dtos;
    }
}
