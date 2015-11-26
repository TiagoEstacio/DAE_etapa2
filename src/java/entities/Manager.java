/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import static auxCategories.UserType.MANAGER;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
//@Table(name = "MANAGERS")
@NamedQueries({
    @NamedQuery(
        name="getAllManagers",
        query="SELECT ma FROM Manager ma ORDER BY ma.id"
    )
})
public class Manager extends User implements Serializable {
    
    @ManyToMany(mappedBy = "managers")
    private List<Event> events;

    public Manager() {
        this.events = new LinkedList<>();
    }

    public Manager(String username, String password, String name, String email) {
        super(username, password, name, email, MANAGER);
        this.events = new LinkedList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    public void addEvent(Event event){
        try {
            if (!events.contains(event)){
                events.add(event);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void removeEvent(Event event){
        try {
            if (events.contains(event)){
                events.remove(event);
            }
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public int getNumberOfEvents(){
        return this.events.size();
    }

    @Override
    public String toString() {
        return "entities.Manager[id=" + id + "]: "+ name;
    }
    
}
