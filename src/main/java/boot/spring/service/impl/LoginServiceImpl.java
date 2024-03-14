package boot.spring.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.mapper.LoginMapper;
import boot.spring.po.Staff;
import boot.spring.service.LoginService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("loginservice")
public class LoginServiceImpl implements LoginService{
	@Autowired
	LoginMapper loginmapper;
	
	public String getpwdbyname(String name) {
		Staff s=loginmapper.getpwdbyname(name);
		if(s!=null)
		return s.getHashedpassword();
		else
		return null;
	}
	
	public Long getUidbyname(String name) {
		Staff s=loginmapper.getpwdbyname(name);
		if(s!=null)
			return (long) s.getStaff_id();
			else
			return null;
	}
	
	public String getnamebyid(long id) {
		Staff s=loginmapper.getnamebyid(id);
		if(s!=null)
			return s.getUsername();
			else
			return null;
	}
	@Override
	public boolean checkUserExists(String name) {
		Staff s = loginmapper.getpwdbyname(name); // 这里我们重用现有的mapper方法，您可能需要根据实际情况调整
		return s != null;
	}
	@Override
	public boolean addNewUser(String username, String hashedpassword,String salt) {
		Staff newUser = new Staff();
		newUser.setUsername(username);
		newUser.setHashedpassword(hashedpassword);
		newUser.setSalt(salt);
		return loginmapper.insertUser(newUser) > 0;
	}

	@Override
	public String getSaltByName(String username) {
		Staff s = loginmapper.getSaltByName(username);
		if(s!=null)
			return s.getSalt();
		else
			return null;
	}
}
