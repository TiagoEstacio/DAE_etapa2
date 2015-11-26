
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "CATEGORIES",
uniqueConstraints =
@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(
        name="getAllCategories",
        query="SELECT ca FROM Category ca ORDER BY ca.id"
    )
})
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String name;
    
    @ManyToMany
    @JoinTable(name = "CATEGORIES_EVENTS",
            joinColumns
            = @JoinColumn(name = "CATEGORIES_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "EVENTS_ID", referencedColumnName = "ID"))
    private List<Event> events;
    
    @ManyToMany
    @JoinTable(name = "CATEGORIES_ATTENDANTS",
            joinColumns
            = @JoinColumn(name = "CATEGORIES_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "ATTENDANTS_ID", referencedColumnName = "ID"))
    private List<Attendant> attendants;
    
    public Category() {
        this.events = new LinkedList<>();
        this.attendants = new LinkedList<>();
    }

    public Category(String name) {
        this.name = name;
        this.events = new LinkedList<>();
        this.attendants = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Attendant> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<Attendant> attendants) {
        this.attendants = attendants;
    }

    public void addEvent(Event event){
        try {
            if (!events.contains(event)){
                this.events.add(event);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void removeEvent(Event event){
        try {
            if (events.contains(event)){
                this.events.remove(event);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public int getNumberOfEvents(){
        return this.events.size();
    }    
    
    public void addAttendant(Attendant attendant){
        try {
            if (!attendants.contains(attendant)){
                attendants.add(attendant);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void removeAttendant(Attendant attendant){
        try {
            if (attendants.contains(attendant)){
                attendants.remove(attendant);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public int getNumberOfAttendants(){
        return this.attendants.size();
    }
    
    @Override
    public String toString() {
        return "entities.Category[id=" + id + "]: "+ name;
    }
}
