package ostSdk.dto;

public enum TransactionTypeKind {
    USER_TO_USER("user_to_user"), COMPANY_TO_USER("company_to_user"), USER_TO_COMPANY("user_to_company");

    private final String code;

    TransactionTypeKind(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
