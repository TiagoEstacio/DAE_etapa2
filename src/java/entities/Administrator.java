
package entities;

import static auxCategories.UserType.ADMINISTRATOR;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
//@Table(name = "ADMINISTRATORS")
@NamedQueries({
    @NamedQuery(
            name = "getAllAdministrators",
            query = "SELECT adm FROM Administrator adm ORDER BY adm.id"
    )
})
public class Administrator extends User implements Serializable {

    //private List<User> users;
    //private List<Event> events;

    public Administrator() {
        //this.users = new LinkedList<>();
        //this.events = new LinkedList<>();
    }

    public Administrator(String username, String password, String name, String email) {
        super(username, password, name, email, ADMINISTRATOR);
        //this.users = new LinkedList<>();
        //this.events = new LinkedList<>();
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
//
//    public List<Event> getEvents() {
//        return events;
//    }
//
//    public void setEvents(List<Event> events) {
//        this.events = events;
//    }
//
//    public void addUser(User user) {
//        try {
//            if (!users.contains(user)) {
//                users.add(user);
//            }
//        } catch (Exception ex) {
//            throw new EJBException(ex.getMessage());
//        }
//    }
//
//    public void removeUser(User user) {
//        try {
//            if (users.contains(user)) {
//                users.remove(user);
//            }
//        } catch (Exception ex) {
//            throw new EJBException(ex.getMessage());
//        }
//    }
//
//    public int getNumberOfUsers() {
//        return this.users.size();
//    }
//
//    public void addEvent(Event event) {
//        try {
//            if (!events.contains(event)) {
//                events.add(event);
//            }
//        } catch (Exception ex) {
//            throw new EJBException(ex.getMessage());
//        }
//    }
//
//    public void removeEvent(Event event) {
//        try {
//            if (events.contains(event)) {
//                events.remove(event);
//            }
//        } catch (Exception ex) {
//            throw new EJBException(ex.getMessage());
//        }
//    }
//
//    public int getNumberOfEvents() {
//        return this.events.size();
//    }

    @Override
    public String toString() {
        return "entities.Administrator[id=" + id + "]: " + name;
    }

}
