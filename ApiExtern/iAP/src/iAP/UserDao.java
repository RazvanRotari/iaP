package iAP;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

	public List<User> userList = new ArrayList<User>();

	public UserDao() {
		User user = new User(1, "AnaMaria", "Ana Maria", "Password", "anamaria@gmail.com");
		User user2 = new User(2, "Mitica", "Mitica Mitica", "Password2", "mitica@gmail.com");
		userList.add(user);
		userList.add(user2);
	}

	public List<User> getAllUsers() {
		return userList;
	}

	public User getUser(int i) {
		for (User u : userList) {
			if (u.getId() == i) {
				return u;
			}
		}
		return null;
	}

	public int updateUser(User pUser) {
		List<User> userList = getAllUsers();
		for (User user : userList) {
			if (user.getUsername().equals(pUser.getUsername())) {
				int index = userList.indexOf(user);
				userList.set(index, pUser);
				return 1;
			}
		}
		return 0;
	}

	public int deleteUser(String username) {
		List<User> userList = getAllUsers();

		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				int index = userList.indexOf(user);
				userList.remove(index);
				return 1;
			}
		}
		return 0;
	}
}
