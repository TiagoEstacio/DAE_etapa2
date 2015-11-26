
package ejbs;

import dtos.AttendantDTO;
import dtos.EventDTO;
import dtos.ManagerDTO;
import entities.Category;
import entities.Event;
import entities.Manager;
import exceptions.AttendantNotEnrolledException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.ManagerEnrolledException;
import exceptions.ManagerNotEnrolledException;
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
public class ManagerBean {

    @PersistenceContext
    private EntityManager em;
    
    public void createManager (String username, String password, String name, String email) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            List<Manager> managers = (List<Manager>) em.createNamedQuery("getAllManagers").getResultList();
            for (Manager m : managers){
                if (username.equals(m.getUserName())){
                    throw new EntityAlreadyExistsException("A manager with that username already exists.");  
                }
            }
            Manager manager = new Manager (username, password, name, email);
            em.persist(manager);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
           throw new EJBException(e.getMessage());
        }
    }
    
    public List<Manager> getAll() {
        try {
            List<Manager> managers = (List<Manager>) em.createNamedQuery("getAllManagers").getResultList();
            return managers;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<ManagerDTO> getAllManagers() {
        try {
            List<Manager> managers = (List<Manager>) em.createNamedQuery("getAllManagers").getResultList();
            return managersToDTOs(managers);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Manager getManager(String username) {
        try {
            Manager manager = new Manager();
            List<Manager> managers = (List<Manager>) em.createNamedQuery("getAllManagers").getResultList();
            for (Manager m : managers){
                if (username.equals(m.getName())){
                    manager = m;
                    break;
                }
            }
            return manager;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void updateManager (Long id, String username, String password, String name, String email)throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            Manager manager = em.find(Manager.class, id);
            if (manager == null){
                throw new EntityDoesNotExistsException("There is no manager with that id.");
            }
//            List<Manager> Managers = (List<Manager>) em.createNamedQuery("getAllManagers").getResultList();
//            for (Manager m : Managers){
//                if (username.equals(m.getUserName())){
//                    throw new EntityAlreadyExistsException("That manager already exists.");
//                }
//            }
            manager.setUsername(username);
            manager.setPassword(password);
            manager.setName(name);
            manager.setEmail(email);
            em.merge(manager);   
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void removeManager(Long id) throws EntityDoesNotExistsException {
        try {
            Manager manager = em.find(Manager.class, id);
            if (manager == null) {
                throw new EntityDoesNotExistsException("There is no manager with that id.");
            }

            for (Event event : manager.getEvents()) {
                event.removeManager(manager);
            }
            
            /* caso o manager tenha categories
            for (Category category : manager.getCategories()){
                category.removeManager(manager);
            }
            */
            
            em.remove(manager);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void enrollManagerInEvent(Long managerId, Long eventId) throws EntityDoesNotExistsException, ManagerEnrolledException{
        try {
            Manager manager = em.find(Manager.class, managerId);
            if (manager == null) {
                throw new EntityDoesNotExistsException("There is no manager with that id.");
            }

            Event event = em.find(Event.class, eventId);
            if (event == null) {
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }

            if (event.getManagers().contains(manager)) {
                throw new ManagerEnrolledException("Manager is already enrolled in that event.");
            }

            event.addManager(manager); 
            manager.addEvent(event);

        } catch (EntityDoesNotExistsException | ManagerEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollManagerInEvent(Long managerId, Long eventId) throws EntityDoesNotExistsException, ManagerNotEnrolledException {
        try {
            Event event = em.find(Event.class, eventId);
            if(event == null){
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }            
            
            Manager manager = em.find(Manager.class, managerId);
            if(manager == null){
                throw new AttendantNotEnrolledException("There is no manager with that id.");
            }
            
            if(!event.getManagers().contains(manager)){
                throw new ManagerNotEnrolledException("Manager is not enrolled in that event.");
            }
          
            event.removeManager(manager);
            manager.removeEvent(event);

        } catch (EntityDoesNotExistsException | ManagerNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<ManagerDTO> getEnrolledManagersInEvents(Long id) throws EntityDoesNotExistsException{
        try {
            Event event = em.find(Event.class, id);
            if( event == null){
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }            
            List<Manager> managers = (List<Manager>) event.getManagers();
            return managersToDTOs(managers);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<ManagerDTO> getUnrolledManagersInEvents(Long id) throws EntityDoesNotExistsException{
        try {
            Event event = em.find(Event.class, id);
            if( event == null){
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }            
            //nao sei se este código está correcto??
            List<Manager> managers = (List<Manager>) em.createNamedQuery("getAllEventManagers")
                    .setParameter("eventId", event.getId())
                    .getResultList();
            //-----------------------------------------------------------------------------------------
            List<Manager> enrolled = em.find(Event.class, id).getManagers();
            managers.removeAll(enrolled);
            return managersToDTOs(managers);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /* Caso o manager use categories
    public void enrollManagerInCategory(Long managerId, Long categoryId) throws EntityDoesNotExistsException, ManagerEnrolledException{
        try {
            Manager manager = em.find(Manager.class, managerId);
            if (manager == null) {
                throw new EntityDoesNotExistsException("There is no manager with that id.");
            }

            Category category = em.find(Category.class, categoryId);
            if (category == null) {
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }

            if (category.getManagers().contains(manager)) {
                throw new AttendantEnrolledException("Manager is already enrolled in that category.");
            }

            category.addManager(manager); 
            manager.addCategory(category);

        } catch (EntityDoesNotExistsException | ManagerEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollManagerInCategory(Long managerId, Long categoryId) throws EntityDoesNotExistsException, ManagerNotEnrolledException {
        try {
            Category category = em.find(Category.class, categoryId);
            if(category == null){
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }            
            
            Manager manager = em.find(Manager.class, managerId);
            if(manager == null){
                throw new ManagerNotEnrolledException("There is no manager with that id.");
            }
            
            if(!category.getManagers().contains(manager)){
                throw new ManagerNotEnrolledException("Manager is not enrolled in that category.");
            }
          
            category.removeManager(manager);
            manager.removeCategory(category);

        } catch (EntityDoesNotExistsException | ManagerNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<ManagerDTO> getEnrolledManagersInCategories(Long Id) throws EntityDoesNotExistsException{
        try {
            Category category = em.find(Category.class, Id);
            if( category == null){
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }            
            List<Manager> managers = (List<Manager>) category.getManagers();
            return managersToDTOs(managers);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<ManagerDTO> getUnrolledManagersInCategories(Long id) throws EntityDoesNotExistsException{
        try {
            Category category = em.find(Category.class, id);
            if( category == null){
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }            
            //nao sei se este código está correcto??
            List<Manager> managers = (List<Manager>) em.createNamedQuery("getAllCategoryManagers")
                    .setParameter("categoryId", category.getId())
                    .getResultList();
            //-----------------------------------------------------------------------------------------
            List<Manager> enrolled = em.find(Category.class, id).getManagers();
            managers.removeAll(enrolled);
            return managersToDTOs(managers);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    */
   
    ManagerDTO managerToDTO(Manager manager) {
        return new ManagerDTO(
                manager.getId(),
                manager.getUserName(),
                null,
                manager.getName(),
                manager.getEmail());
    }

    List<ManagerDTO> managersToDTOs(List<Manager> managers) {
        List<ManagerDTO> dtos = new ArrayList<>();
        for (Manager m : managers) {
            dtos.add(managerToDTO(m));
        }
        return dtos;
    }

    public List<EventDTO> getAllEventsOfManager(Long id) {
        Manager man = em.find(Manager.class, id);
        
        if (man != null) {       
             List<Event> events = new ArrayList<>();
             events = man.getEvents();
             return eventsToDTOs(events);
        }
        return null;
    }
    
    List<EventDTO> eventsToDTOs(List<Event> events) {
        List<EventDTO> dtos = new ArrayList<>();
        for (Event e : events) {
            dtos.add(eventToDTO(e));
        }
        return dtos;
    }
    
    EventDTO eventToDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getStartDate(),
                event.getFinishDate());
               
    }

    public Object enrollManagerInEvent(AttendantDTO attendantByName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
