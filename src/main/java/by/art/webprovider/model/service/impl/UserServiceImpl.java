package by.art.webprovider.model.service.impl;

import by.art.webprovider.exception.ProviderDatabaseException;
import by.art.webprovider.exception.ServiceException;
import by.art.webprovider.model.dao.UserDao;
import by.art.webprovider.model.dao.impl.UserDaoJdbc;
import by.art.webprovider.model.entity.User;
import by.art.webprovider.model.service.UserService;
import by.art.webprovider.model.service.validator.UserValidator;
import by.art.webprovider.util.ErrorMessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserServiceImpl} class represents user service implementation
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 * @see UserService
 */
public class UserServiceImpl implements UserService {
  private static final Logger logger = LogManager.getLogger();
  private UserDao userDao = UserDaoJdbc.getInstance();

  @Override
  public boolean checkIsUsernameBusy(String username, StringBuilder validationStatus) throws ServiceException {
    boolean isUsernameBusy;
    try {
      isUsernameBusy = userDao.checkIsUsernameBusy(username);
    } catch (ProviderDatabaseException e) {
      validationStatus.append(e.getMessage());
      throw new ServiceException("Database access error occurred while validating username", e);
    }
    if (isUsernameBusy) {
      validationStatus.append(ErrorMessageManager.getMessage("msg.nameExists")).append("\n");
    }
    return isUsernameBusy;
  }

  @Override
  public boolean isFirstUser() throws ServiceException {
    try {
      return userDao.countUsers() == 0;
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("An error occurred while counting users in the database", e);
    }
  }

  @Override
  public boolean registerUser(User user) throws ServiceException {
    try {
      return userDao.createUser(user);
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("user registration error", e);
    }
  }

  @Override
  public boolean validateCredentialsAndActivity(String username, String password) throws ServiceException {
    try {
      return userDao.validateCredentialsAndActivity(username, password);
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("Database access error occurred while validating credentials", e);
    }
  }

  @Override
  public Optional<User> findUserByUsername(String username) throws ServiceException {
    try {
      return userDao.findUserByUsername(username);
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("An error occurred while retrieving the user from the database", e);
    }
  }

  @Override
  public List<User> findUsers(int limit, int offset) throws ServiceException {
    try {
      return userDao.findUsers(limit, offset);
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("An error occurred while retrieving users from the database", e);
    }
  }

  @Override
  public int countUsers() throws ServiceException {
    try {
      return userDao.countUsers();
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("An error occurred while retrieving number of users", e);
    }
  }

  @Override
  public boolean updateUser(User user) throws ServiceException {
    try {
      return userDao.updateUser(user);
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("An error occurred while updating of user", e);
    }
  }

  @Override
  public boolean deleteUser(String username) throws ServiceException {
    try {
      return userDao.deleteUser(username);
    } catch (ProviderDatabaseException e) {
      throw new ServiceException("An error occurred while deleting of user", e);
    }
  }

  @Override
  public boolean isValidUser(String username, String password, String confirmPassword, String firstname,
                             String lastname, String email, StringBuilder validationStatus) {
    boolean isValidUser = true;
    if (UserValidator.isNotValidUsername(username)) {
      validationStatus.append(ErrorMessageManager.getMessage("msg.notValidUsername")).append("\n");
      isValidUser = false;
    }
    if (UserValidator.isNotValidPassword(password)) {
      validationStatus.append(ErrorMessageManager.getMessage("msg.notValidPassword")).append("\n");
      isValidUser = false;
    }
    if (UserValidator.isNotEqualsPasswords(password, confirmPassword)) {
      validationStatus.append(ErrorMessageManager.getMessage("msg.notConfirmPassword")).append("\n");
      isValidUser = false;
    }
    if (UserValidator.isNotValidName(firstname)) {
      validationStatus.append(ErrorMessageManager.getMessage("msg.notValidFirstname")).append("\n");
      isValidUser = false;
    }
    if (UserValidator.isNotValidName(lastname)) {
      validationStatus.append(ErrorMessageManager.getMessage("msg.notValidLastname")).append("\n");
      isValidUser = false;
    }
    if (UserValidator.isNotValidEmail(email)) {
      validationStatus.append(ErrorMessageManager.getMessage("msg.notValidEmail"));
      isValidUser = false;
    }
    logger.info("User {} is valid = {}", username, isValidUser);
    return isValidUser;
  }
}
