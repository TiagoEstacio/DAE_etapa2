
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
import javax.validation.constraints.Pattern;



@Entity
@Table(name = "EVENTS",
uniqueConstraints =
@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(
        name="getAllEvents",
        query="SELECT ev FROM Event ev ORDER BY ev.id"
    )
})
public class Event implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    
    private String description;
    
    @ManyToMany(mappedBy = "events")
    private List<Category> categories;
   
    @NotNull
   @Pattern(regexp="^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29"
           + "(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]"
           + "|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1"
           + "(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))(|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$",
            message="{Not Valid date or hour}")
    private String startDate;
    
    @NotNull
    @Pattern(regexp="^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29"
           + "(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]"
           + "|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1"
           + "(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))(|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$",
            message="{Not Valid date or hour}")
        private String finishDate;
    
    @ManyToMany
    @JoinTable(name = "EVENTS_MANAGERS",
            joinColumns
            = @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "MANAGERS_ID", referencedColumnName = "ID"))
    private List<Manager> managers;
    
    @ManyToMany
    @JoinTable(name = "EVENTS_ATTENDANTS",
            joinColumns
            = @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "ATTENDANTS_ID", referencedColumnName = "ID"))
    private List<Attendant> attendants;
    
    private boolean openForEnroll;
      
    public Event() {
        this.categories = new LinkedList<>();
        this.managers = new LinkedList<>();
        this.attendants = new LinkedList<>();
        this.openForEnroll = false;
    }

    public Event(String name,String description ,String startDate, String finishDate) {
        this.name = name;
        this.description=description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.categories = new LinkedList<>();
        this.managers = new LinkedList<>();
        this.attendants = new LinkedList<>();
        this.openForEnroll = false;
    }

    public boolean isOpenForEnroll() {
        return openForEnroll;
    }

    public void setOpenForEnroll(boolean OpenForEnrollment) {
        this.openForEnroll = OpenForEnrollment;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public List<Attendant> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<Attendant> attendants) {
        this.attendants = attendants;
    }
    
    public void addCategory(Category category){
        try {
            if (!categories.contains(category)){
                this.categories.add(category);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void removeCategory(Category category){
        try {
            if (categories.contains(category)){
                this.categories.remove(category);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public int getNumberOfCategories(){
        return this.categories.size();
    }
    
    public void addManager(Manager manager){
        try {
            if (!managers.contains(manager)){
                managers.add(manager);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void removeManager(Manager manager){
        try {
            if (managers.contains(manager)){
                managers.remove(manager);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public int getNumberOfManagers(){
        return this.managers.size();
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
        return "entities.Event[id=" + id + "]: "+ name;
    }

    public void clearAttendants() {
        attendants.clear();
    }
    public void clearManagers() {
        managers.clear();
    }

    public void clearCategories() {
        categories.clear();
    }
    
}
