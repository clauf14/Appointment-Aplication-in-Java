package service;

import java.util.List;

import javax.persistence.Persistence;

import controllers.LoginPageController;
import dao.UserDao;
import model.User;

public class UserService {
	private UserDao userDao;

	private static UserService instance;

	public UserService() {
		instance = this;
		try {
			userDao = new UserDao(Persistence.createEntityManagerFactory("Football"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static UserService getInstance() {
		return instance;
	}

	public String errorMessage() {
		return "Invalid login!";
	}

	public void deleteUser(User deletedUser) {
		userDao.remove(deletedUser, deletedUser.getAccountId());
	}

	public User addUser(String fname, String lname, String pass, String user, int active) throws Exception {
		List<User> users = userDao.find(user);
		if (!users.isEmpty())
			throw new Exception("User already exists!");
		User newUser = new User(fname, lname, pass, user, active);
		userDao.create(newUser);
		return newUser;
	}

	public void updateUser(User oldUser, User updatedUser) {
		oldUser.setFirstName(updatedUser.getFirstName());
		oldUser.setLastName(updatedUser.getLastName());
		oldUser.setUsername(updatedUser.getUsername());
		oldUser.setPassword(updatedUser.getPassword());
		userDao.update(oldUser);
	}

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public User findUser(String user, String pass) throws Exception {
		List<User> users = userDao.find(user);
		if (users.size() == 0) {
			LoginPageController.getInstance().error("User not found! Please try again or register!");
			throw new Exception("User not found!");
		}
		User u = users.get(0);

		if (pass.compareTo(u.getPassword()) != 0) {
			LoginPageController.getInstance().error("User exists but password does not match!");
			throw new Exception("Password does not match");
		}
		return u;
	}
}
