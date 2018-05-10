package ostSdk.dto;

public enum ListType {
    ALL("all"), NEVER_AIRDROPPED("never_airdropped");

    private final String code;

    ListType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
