package ui.menuitem;

import user.User;

public abstract class LobbyMenu extends LoginMenu {

    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
