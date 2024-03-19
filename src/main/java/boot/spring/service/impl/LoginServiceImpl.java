package boot.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.mapper.LoginMapper;
import boot.spring.po.Staff;
import boot.spring.service.LoginService;

// Annotates this class for declarative transaction management, specifying
// that transactions should be automatically started before each method execution,
// committed after its completion, or rolled back if an exception occurred.
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 5)
@Service("loginservice") // Indicates that this class is a Spring-managed service bean.
public class LoginServiceImpl implements LoginService{
	@Autowired // Injects the LoginMapper bean automatically by Spring's dependency injection facilities.
	LoginMapper loginmapper;

	// Retrieves the hashed password of a staff member by their name.
	// Returns the hashed password if found, null otherwise.
	public String getpwdbyname(String name) {
		Staff s = loginmapper.getpwdbyname(name);
		if(s != null)
			return s.getHashedpassword();
		else
			return null;
	}

	// Retrieves the unique ID of a staff member by their name.
	// Returns the ID if found, null otherwise.
	public Long getUidbyname(String name) {
		Staff s = loginmapper.getpwdbyname(name);
		if(s != null)
			return (long) s.getStaff_id();
		else
			return null;
	}

	// Retrieves the username of a staff member by their ID.
	// Returns the username if found, null otherwise.
	public String getnamebyid(long id) {
		Staff s = loginmapper.getnamebyid(id);
		if(s != null)
			return s.getUsername();
		else
			return null;
	}

	// Checks if a user exists in the database by their name.
	// Returns true if the user exists, false otherwise.
	@Override
	public boolean checkUserExists(String name) {
		Staff s = loginmapper.getpwdbyname(name); // Reuses an existing mapper method, might need adjustment based on actual context
		return s != null;
	}

	// Adds a new user to the database with the provided username, hashed password, salt, and email.
	// Returns true if the operation is successful, false otherwise.
	@Override
	public boolean addNewUser(String username, String hashedpassword, String salt, String email) {
		Staff newUser = new Staff();
		newUser.setUsername(username);
		newUser.setHashedpassword(hashedpassword);
		newUser.setSalt(salt);
		newUser.setEmail(email);
		return loginmapper.insertUser(newUser) > 0;
	}

	// Retrieves the salt associated with a username.
	// Returns the salt if found, null otherwise.
	@Override
	public String getSaltByName(String username) {
		Staff s = loginmapper.getSaltByName(username);
		if(s != null)
			return s.getSalt();
		else
			return null;
	}
}
