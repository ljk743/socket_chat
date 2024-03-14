package boot.spring.mapper;

import boot.spring.po.Staff;

public interface LoginMapper {
	Staff getpwdbyname(String name);
	Staff getnamebyid(long id);
	int insertUser(Staff user);

	Staff getSaltByName(String username);
}
