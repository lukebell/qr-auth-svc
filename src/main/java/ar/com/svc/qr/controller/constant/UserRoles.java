package ar.com.svc.qr.controller.constant;

public enum UserRoles {

    ADMIN(0),
    USER(1),
    NON_ROLE(-1);

    private final int id;

    UserRoles(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRoles getUserRole(int id) {
        for (UserRoles role : UserRoles.values()) {
            if (role.getId() == id)
                return role;
        }
        return UserRoles.NON_ROLE;
    }
}
