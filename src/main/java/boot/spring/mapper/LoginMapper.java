package boot.spring.mapper;

import boot.spring.po.Staff;

// Defines an interface for database operations related to login functionalities.
public interface LoginMapper {
	// Retrieves a Staff object containing the password for a given username.
	// The parameter 'name' represents the username of the Staff.
	Staff getpwdbyname(String name);

	// Retrieves a Staff object based on the given user ID.
	// The parameter 'id' is the unique identifier for the Staff.
	Staff getnamebyid(long id);

	// Inserts a new user record into the database.
	// The parameter 'user' is a Staff object containing the new user's information.
	// Returns an integer indicating the result of the insert operation, typically the number of rows affected.
	int insertUser(Staff user);

	// Retrieves a Staff object that contains the salt used for hashing the user's password, identified by username.
	// The parameter 'username' specifies the username of the Staff for which the salt is retrieved.
	Staff getSaltByName(String username);
}
