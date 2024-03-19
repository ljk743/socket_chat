package boot.spring.po;

// The User class represents a user entity with a unique identifier and a name.
// It showcases basic class structure and the use of constructors in Java, particularly
// the use of the `super` keyword for inheritance-related purposes.
public class User {
	// Unique identifier for the user
	Long uid;

	// Name of the user
	String name;

	// Default constructor. Calls the parent class constructor using `super()`.
	// In Java, the `super` keyword is used to refer to the parent class objects.
	// Here, it ensures that any initialization in the parent class is also applied to the User class,
	// even though Java does this automatically if the parent has a no-argument constructor.
	// It's a good practice for clarity and future-proofing, especially when extending custom classes.
	public User() {
		super(); // Explicit call to the parent class's no-argument constructor
	}

	// Parametrized constructor to initialize a User object with a uid and name.
	// This constructor also calls the parent class constructor using `super()`,
	// ensuring that the User object is properly initialized according to the parent class's specifications.
	public User(Long uid, String name) {
		super(); // Explicit call to the parent class's no-argument constructor
		this.uid = uid; // Set the user ID
		this.name = name; // Set the username
	}

	// Getter for uid. Returns the unique identifier of the user.
	public Long getUid() {
		return uid;
	}

	// Setter for uid. Allows setting the unique identifier of the user.
	public void setUid(Long uid) {
		this.uid = uid;
	}

	// Getter for name. Returns the name of the user.
	public String getName() {
		return name;
	}

	// Setter for name. Allows setting the name of the user.
	public void setName(String name) {
		this.name = name;
	}

}
