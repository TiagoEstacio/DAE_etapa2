/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

public class EventDTO {
    
    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String finishDate;
    private boolean openForEnroll; 
    
    public EventDTO() {
    }
    
    public EventDTO(Long id,String name,String description ,String startDate, String finishDate) {
        this.id = id;
        this.name = name;
        this.description=description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        openForEnroll = false;
    }
    
    public void reset(){
        this.name = null;
        this.description = null;
        this.openForEnroll = false;
        this.finishDate = null;
        this.startDate = null;
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

    public boolean isOpenForEnroll() {
        return openForEnroll;
    }

    public void setOpenForEnroll(boolean OpenForEnroll) {
        this.openForEnroll = OpenForEnroll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
