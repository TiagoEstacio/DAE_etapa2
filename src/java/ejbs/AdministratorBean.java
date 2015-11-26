package ejbs;

import auxCategories.UserType;
import dtos.AdministratorDTO;
import entities.Administrator;
import entities.Attendant;
import entities.Manager;
import entities.User;
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
public class AdministratorBean {

    @PersistenceContext
    EntityManager em;

    public void createAdministrator(String username, String password, String name, String email) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            List<Administrator> administrators = (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
            for (Administrator a : administrators) {
                if (username.equals(a.getUserName())) {
                    throw new EntityAlreadyExistsException("A administrator with that username already exists.");
                }
            }
            Administrator admin = new Administrator(username, password, name, email);
            em.persist(admin);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void createUser(String username, String password, String name, String email, UserType type) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            List<User> users = (List<User>) em.createNamedQuery("getAllUsers").getResultList();
            for (User u : users) {
                if (username.equals(u.getUserName())) {
                    throw new EntityAlreadyExistsException("A user with that username already exists.");
                }
            }
            User user = null;
            switch (type) {
                case ADMINISTRATOR:
                    user = new Administrator(username, password, name, email);
                    break;

                case MANAGER:
                    user = new Manager(username, password, name, email);
                    break;

                case ATTENDANT:
                    user = new Attendant(username, password, name, email);
                    break;
                default:
                    System.out.println("Not a valid user type!");
            }

            em.persist(user);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<AdministratorDTO> getAllAdministrators() {
        try {

            return administratorsToDTOs((List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Administrator getAdministrator(String username) {
        try {
            for (Administrator admin : (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList()) {
                if (username.equals(admin.getUserName())) {
                    return admin;
                }
            }

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return null;
    }

    public void updateAdministrator(Long id, String username, String password, String name, String email) throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Administrator administrator = em.find(Administrator.class, id);
            if (administrator == null) {
                throw new EntityDoesNotExistsException("There is no administrator with that id.");
            }
//            List<Administrator> administrators = (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
//            for (Administrator a : administrators){
//                if (username.equals(a.getUserName())){
//                    throw new EntityAlreadyExistsException("A administrator with that username already exists.");  
//                }
//            }
            administrator.setUsername(username);
            administrator.setPassword(password);
            administrator.setName(name);
            administrator.setEmail(email);
            em.merge(administrator);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    //updateUser -> qualquer user pode ser atualizado para Manager. Um admin tb pode ser manager e fica com 2 tipos?
    //não é possível fazer o update para Admin e para attendant(pôr esta hipótese ao grupo)
    public void updateUser(Long id, String name, String email, String password) throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
        User user = em.find(User.class, id);
        if (user == null) {
                throw new EntityDoesNotExistsException("There is no user with that id.");
            }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
       
        em.merge(user);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void removeAdministrator(Long id) throws EntityDoesNotExistsException {
        try {
            Administrator administrator = em.find(Administrator.class, id);
            if (administrator == null) {
                throw new EntityDoesNotExistsException("There is no administrator with that id.");
            }
            em.remove(administrator);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void removeUser(Long id) throws EntityDoesNotExistsException {
        try {
            User user = em.find(User.class, id);
            if (user == null) {
                throw new EntityDoesNotExistsException("There is no user with that id.");
            }
            em.remove(user);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public UserType[] getTypesValues() {
        return UserType.values();
    }

    AdministratorDTO administratorToDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getId(),
                administrator.getUserName(),
                null,
                administrator.getName(),
                administrator.getEmail());
    }

    List<AdministratorDTO> administratorsToDTOs(List<Administrator> administrators) {
        List<AdministratorDTO> dtos = new ArrayList<>();
        for (Administrator a : administrators) {
            dtos.add(administratorToDTO(a));
        }
        return dtos;
    }
}
