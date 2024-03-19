package boot.spring.service;

// Defines the LoginService interface with methods for user authentication and management.
// This interface serves as a contract for implementing classes to provide
// specific operations related to user login functionality within the application.
public interface LoginService {
	// Retrieves the hashed password for a user identified by their username.
	// @param name the username of the user
	// @return the hashed password of the user if found; null otherwise
	String getpwdbyname(String name);

	// Retrieves the unique identifier (UID) for a user identified by their username.
	// @param name the username of the user
	// @return the UID of the user if found; null otherwise
	Long getUidbyname(String name);

	// Retrieves the username for a user identified by their unique identifier (ID).
	// @param id the unique identifier of the user
	// @return the username of the user if found; null otherwise
	String getnamebyid(long id);

	// Checks whether a user with the specified username exists in the system.
	// @param name the username to check
	// @return true if the user exists; false otherwise
	boolean checkUserExists(String name);

	// Adds a new user to the system with the provided username, password, salt, and email.
	// @param username the username for the new user
	// @param password the hashed password for the new user
	// @param salt the salt used in the hashing process for the password
	// @param email the email address for the new user
	// @return true if the user was successfully added; false otherwise
	boolean addNewUser(String username, String password, String salt, String email);

	// Retrieves the salt associated with a user identified by their username.
	// This is typically used in conjunction with hashing algorithms for password verification.
	// @param username the username of the user
	// @return the salt associated with the user if found; null otherwise
	String getSaltByName(String username);
}
