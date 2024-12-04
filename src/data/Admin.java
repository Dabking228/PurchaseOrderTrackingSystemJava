
package data;

public class Admin extends Account {
    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }

    public Admin(String password, Role role, String Id) {
        super(password, role, Id);
    }

    public Admin(String username, String password, Role role) {
        super(username, password, role);
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
