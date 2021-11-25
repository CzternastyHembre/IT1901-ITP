package ui.menuitem;

import user.User;

/**
 * LobbyMenu extends the LoginMenu and allows the
 * other controllers to get and set the current user.
 */
public abstract class LobbyMenu extends LoginMenu {

  protected User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
