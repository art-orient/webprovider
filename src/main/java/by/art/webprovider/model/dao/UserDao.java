package by.art.webprovider.model.dao;

import by.art.webprovider.exception.ProviderDatabaseException;
import by.art.webprovider.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserDao} interface for working with database table users
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface UserDao {

  /**
   * Saves the user
   *
   * @param user {@link User} the user
   * @return the boolean is the user saved
   * @throws ProviderDatabaseException the ProviderDatabaseException exception     *
   */
  boolean createUser(User user) throws ProviderDatabaseException;

  /**
   * Сhecks if the username is busy or free
   *
   * @param username {@link String} the username
   * @return the boolean is the username busy
   * @throws ProviderDatabaseException the ProviderDatabaseException exception
   */
  boolean checkIsUsernameBusy(String username) throws ProviderDatabaseException;

  /**
   * Validates user credentials and activity
   *
   * @param username {@link String} the username
   * @param password {@link String} the password
   * @return the boolean[] are credentials valid and is active user
   * @throws ProviderDatabaseException the ProviderDatabaseException exception
   */
  boolean validateCredentialsAndActivity(String username, String password) throws ProviderDatabaseException;

  /**
   * Сounts the number of all users
   *
   * @return the number of all users
   * @throws ProviderDatabaseException the ProviderDatabaseException exception
   */
  int countUsers() throws ProviderDatabaseException;

  /**
   * Finds the user by his username
   *
   * @param username {@link String} the username
   * @return {@link Optional} of {@link User} the optional of found user
   * @throws ProviderDatabaseException the ProviderDatabaseException exception
   */
  Optional<User> findUserByUsername(String username) throws ProviderDatabaseException;

  /**
   * Changes user data (role & activity), blocks and unblocks the user
   *
   * @param user {@link User} the user
   * @return the boolean is the user updated
   * @throws ProviderDatabaseException the ProviderDatabaseException exception
   */
  boolean updateUser(User user) throws ProviderDatabaseException;

  /**
   * Finds all users
   *
   * @param limit  number of users per page
   * @param offset index of the first user on the page
   * @return {@link List} of {@link User} the list of found users
   * @throws ProviderDatabaseException the ProviderDatabaseException exception
   */
  List<User> findUsers(int limit, int offset) throws ProviderDatabaseException;

  /**
   * Removes the user by his username
   *
   * @param username {@link String} the username
   * @return the boolean is the user deleted
   * @throws ProviderDatabaseException the ProviderDatabaseException exception
   */
  boolean deleteUser(String username) throws ProviderDatabaseException;
}

