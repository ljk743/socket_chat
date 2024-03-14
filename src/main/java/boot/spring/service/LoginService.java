package boot.spring.service;


public interface LoginService {
	String getpwdbyname(String name);
	Long getUidbyname(String name);
	String getnamebyid(long id);
	boolean checkUserExists(String name);
	boolean addNewUser(String username, String password, String salt);
	String getSaltByName(String username);
}
