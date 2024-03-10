package boot.spring.po;


public class Staff {
    private byte staff_id;
    private String username;
    private String password;
    private String last_update;


    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public byte getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(byte staff_id) {
        this.staff_id = staff_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}