package model;

public enum UserStatus {
    DEACTIVATE,
    ACTIVATE,
    BANNED,
    REQUEST,
    REJECT;

    public boolean isDeactivated() { return this == DEACTIVATE; }
    public boolean isActivated() { return this == ACTIVATE; }
    public boolean isBanned() { return this == BANNED; }
    public boolean isRequested() { return this == REQUEST; }
    public boolean isRejected() { return this == REJECT; }


}
