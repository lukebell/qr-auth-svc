package ar.com.svc.qr.model.constant;

public enum QRCodeConstants {


    ID_COLUMN("ID"),
    CLIENT_CODE_COLUMN("CLIENT_CODE"),
    TOKEN_COLUMN("TOKEN"),
    UUID_COLUMN("UUID"),
    CREATED_DATE_COLUMN("CREATED_DATE"),

    FIND_ALL_QUERY("SELECT * FROM AUTH.USER_CODES"),
    FIND_BY_ID_QUERY("SELECT * FROM AUTH.USER_CODES WHERE ID=?"),
    FIND_ALL_BY_QUERY(FIND_ALL_QUERY.getValue() + " WHERE UUID LIKE ?"),
    FIND_BY_CLIENT_CODE_QUERY("SELECT * FROM AUTH.USER_CODES WHERE CLIENT_CODE=?"),
    FIND_BY_CLIENT_CODE_AND_QUERY(FIND_BY_CLIENT_CODE_QUERY.getValue()+ " AND UUID LIKE ?"),
    FIND_BY_CLIENT_CODE_AND_UUID_QUERY("SELECT * FROM AUTH.USER_CODES WHERE CLIENT_CODE=? AND UUID=?"),
    FIND_BY_CLIENT_CODE_AND_TOKEN_UUID_QUERY("SELECT * FROM AUTH.USER_CODES WHERE CLIENT_CODE=? AND TOKEN=? AND UUID=?"),
    FIND_BY_CLIENT_CODE_AND_TOKEN_QUERY("SELECT * FROM AUTH.USER_CODES WHERE CLIENT_CODE=? AND TOKEN=?"),
    EXISTS_QUERY("SELECT COUNT(*) FROM AUTH.USER_CODES WHERE CLIENT_CODE=?"),
    INSERT_QUERY("INSERT INTO AUTH.USER_CODES (CLIENT_CODE, TOKEN, UUID) VALUES (?, ?, ?)"),
    INSERT_OR_UPDATE_QUERY("INSERT INTO AUTH.USER_CODES (CLIENT_CODE, TOKEN, UUID) VALUES (?, ?, ?)\n" +
            "ON DUPLICATE KEY UPDATE CREATED_DATE=SYSDATE()"),
    DELETE_BY_ID_QUERY("DELETE FROM AUTH.USER_CODES WHERE ID=?"),
    DELETE_BY_CLIENT_CODE_AND_TOKEN_QUERY("DELETE FROM AUTH.USER_CODES WHERE CLIENT_CODE=? AND TOKEN=?"),
    DELETE_ALL_QUERY("DELETE FROM AUTH.USER_CODES"),
    UPDATE_BY_ID_QUERY("UPDATE AUTH.USER_CODES SET CLIENT_CODE=?, TOKEN=?, UUID=? WHERE ID=?");


    private final String value;

    QRCodeConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
