
package dtos;

import static auxCategories.UserType.MANAGER;
import java.io.Serializable;

public class ManagerDTO extends UserDTO implements Serializable{
    
    public ManagerDTO() {
    }    
    
    public ManagerDTO(Long id, String username, String password, String name, String email) {
        super(id, username, password, name, email, MANAGER);
    }
    
    @Override
    public void reset() {
        super.reset();
    }
    
}
