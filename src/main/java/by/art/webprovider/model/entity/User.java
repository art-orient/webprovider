package by.art.webprovider.model.entity;

import java.util.StringJoiner;

public class User {
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private UserRole role;
  private boolean active;

  public User() {
  }

  public User(String username, String firstName, String lastName, String email) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public User(String username, String password, String firstName, String lastName, String email, UserRole role,
              boolean active) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.role = role;
    this.active = active;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return active == user.active
            && username.equals(user.username)
            && password.equals(user.password)
            && firstName.equals(user.firstName)
            && lastName.equals(user.lastName)
            && email.equals(user.email)
            && role == user.role;
  }

  @Override
  public int hashCode() {
    int result = username.hashCode();
    result = 31 * result + password.hashCode();
    result = 31 * result + firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + role.hashCode();
    result = 31 * result + Boolean.hashCode(active);
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
            .add("username='" + username + "'")
            .add("password='" + password + "'")
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .add("email='" + email + "'")
            .add("role=" + role)
            .add("active=" + active)
            .toString();
  }
}
