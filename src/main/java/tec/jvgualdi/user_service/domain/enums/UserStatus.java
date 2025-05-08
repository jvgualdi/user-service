package tec.jvgualdi.user_service.domain.enums;

public enum UserStatus {
    ACTIVE,
    INACTIVE,
    PENDING,
    SUSPENDED,
    DELETED;

    public static UserStatus fromString(String status) {
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.name().equalsIgnoreCase(status)) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
