package ui.MenuItem;

import user.User;

public class LobbyMenu extends LoginMenu {

    protected  User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
