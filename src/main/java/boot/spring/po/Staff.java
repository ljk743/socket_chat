package boot.spring.po;

// Defines the Staff class to represent staff member entities within the application.
public class Staff {
    // Unique identifier for the staff member
    private byte staff_id;
    // Username of the staff member
    private String username;
    // Hashed password for the staff member's account
    private String hashedpassword;
    // Timestamp of the last update made to the staff member's record
    private String last_update;
    // Salt used for hashing the staff member's password
    private String salt;
    // Email address of the staff member
    private String email;

    // Getter method for the last update timestamp
    public String getLast_update() {
        return last_update;
    }

    // Setter method for the last update timestamp
    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    // Getter method for the staff member's ID
    public byte getStaff_id() {
        return staff_id;
    }

    // Setter method for the staff member's ID
    public void setStaff_id(byte staff_id) {
        this.staff_id = staff_id;
    }

    // Getter method for the staff member's username
    public String getUsername() {
        return username;
    }

    // Setter method for the staff member's username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for the staff member's hashed password
    public String getHashedpassword() {
        return hashedpassword;
    }

    // Setter method for the staff member's hashed password
    public void setHashedpassword(String hashedpassword) {
        this.hashedpassword = hashedpassword;
    }

    // Getter method for the salt used in the staff member's password hash
    public String getSalt() {
        return salt;
    }

    // Setter method for the salt used in the staff member's password hash
    public void setSalt(String salt) {
        this.salt = salt;
    }

    // Getter method for the staff member's email address
    public String getEmail() {
        return email;
    }

    // Setter method for the staff member's email address
    public void setEmail(String email) {
        this.email = email;
    }

}
