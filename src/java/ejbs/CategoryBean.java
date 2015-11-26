package ejbs;

import dtos.CategoryDTO;
import dtos.EventDTO;
import entities.Attendant;
import entities.Category;
import entities.Event;
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
public class CategoryBean {

    @PersistenceContext
    private EntityManager em;

  

    public void createCategory(String name) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            List<Category> categories = (List<Category>) em.createNamedQuery("getAllCategories").getResultList();
            for (Category c : categories) {
                if (name.equals(c.getName())) {
                    throw new EntityAlreadyExistsException("A category with that name already exists.");
                }
            }
            Category category = new Category(name);
            em.persist(category);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<CategoryDTO> getAllCategories() {
        try {
            List<Category> categories = (List<Category>) em.createNamedQuery("getAllCategories").getResultList();
            return categoriesToDTOs(categories);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<CategoryDTO> getAllCategoriesOfCurrentEvent(Long id) throws EntityDoesNotExistsException{
        
        Event event = em.find(Event.class, id);
            if (event == null){
                throw new EntityDoesNotExistsException("There is no event with that id.");
            }
        List<Category> eventCategories = event.getCategories();
        return categoriesToDTOs(eventCategories);
    }

    public List<EventDTO> getAllCategoryEvents(Long id) throws EntityDoesNotExistsException {
        Category category = em.find(Category.class, id);
        if (category == null) {
            throw new EntityDoesNotExistsException("There is no category with that id.");
        }
        return eventsToDTOs(category.getEvents());
        
    }

    public int getNumberofAttendants(Long id) throws EntityDoesNotExistsException {
        Category category = em.find(Category.class, id);
        if (category == null) {
            throw new EntityDoesNotExistsException("There is no category with that id.");
        }
        return category.getAttendants().size();
    }
    
    public int getNumberofEvents(Long id) throws EntityDoesNotExistsException {
        Category category = em.find(Category.class, id);
        if (category == null) {
            throw new EntityDoesNotExistsException("There is no category with that id.");
        }
        return category.getNumberOfEvents();
    }

    public CategoryDTO getCategoryByName(String name) {
        try {
            List<Category> categories = (List<Category>) em.createNamedQuery("getAllCategories").getResultList();
            for (Category cat : categories) {
                if (name.equals(cat.getName())) {
                    return categoryToDTO(cat);
                }
            }
            return null;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void updateCategory(Long id, String name) throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            
            
            Category category = em.find(Category.class, id);
            if (category == null) {
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }
            System.out.println("ID: " + id);
            System.out.println("NAME: " + name);

            System.out.println("CategoryID: " + category.getId());
            /*
            List<Category> categories = (List<Category>) em.createNamedQuery("getAllCategories").getResultList();
            for (Category c : categories) {
                if (name.equals(c.getName())) {
                    throw new EntityAlreadyExistsException("That category already exists.");
                }
            }
            */
            category.setName(name);
            em.merge(category);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void removeCategory(Long id) throws EntityDoesNotExistsException {
        try {
            Category category = em.find(Category.class, id);
            if (category == null) {
                throw new EntityDoesNotExistsException("There is no category with that id.");
            }
           
            for (Attendant attendant : category.getAttendants()) {
                attendant.removeCategory(category);
                      
            }
            
            for (Event event : category.getEvents()) {
              event.removeCategory(category);
           }
            
            em.remove(category);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /* Caso o manager tenha categories
     public List<CategoryDTO> getManagerCategories(Long managerId) throws EntityDoesNotExistsException {
     try {
     Manager manager = em.find(Manager.class, managerId);
     if (manager == null) {
     throw new EntityDoesNotExistsException("Manager does not exists.");
     }
     return categoriesToDTOs(manager.getCategories()); 
     } catch (EntityDoesNotExistsException e) {
     throw e;
     } catch (Exception e) {
     throw new EJBException(e.getMessage());
     }
     }
     */
    public List<CategoryDTO> getAttendantCategories(Long attendantId) throws EntityDoesNotExistsException {
        try {
            Attendant attendant = em.find(Attendant.class, attendantId);
            if (attendant == null) {
                throw new EntityDoesNotExistsException("Attendant does not exists.");
            }
            return categoriesToDTOs(attendant.getCategories());
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<CategoryDTO> getEventCategories(Long eventId) throws EntityDoesNotExistsException {
        try {
            Event event = em.find(Event.class, eventId);
            if (event == null) {
                throw new EntityDoesNotExistsException("Event does not exists.");
            }
            return categoriesToDTOs(event.getCategories());
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    CategoryDTO categoryToDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName());
    }

    List<CategoryDTO> categoriesToDTOs(List<Category> categories) {
        List<CategoryDTO> dtos = new ArrayList<>();
        for (Category c : categories) {
            dtos.add(categoryToDTO(c));
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
    
    List<EventDTO> eventsToDTOs(List<Event> events) {
        List<EventDTO> dtos = new ArrayList<>();
        for (Event e : events) {
            dtos.add(eventToDTO(e));
        }
        return dtos;
    }
}
