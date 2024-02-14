package Model;

public enum UserStatus {
    DEACTIVATE,
    ACTIVATE,
    BANNED,
    REQUEST;

    public boolean isDeactivated() { return this == DEACTIVATE; }
    public boolean isActivated() { return this == ACTIVATE; }
    public boolean isBanned() { return this == BANNED; }
    public boolean isRequested() { return this == REQUEST; }


}
