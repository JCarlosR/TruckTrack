package com.programacionymas.trucktrack.io.response;

import com.programacionymas.trucktrack.model.User;

public class LoginResponse {

    private boolean success;
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
