package ostSdk.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionType {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("currency_type")
    @Expose
    private String currencyType;
    @SerializedName("currency_value")
    @Expose
    private String currencyValue;
    @SerializedName("commission_percent")
    @Expose
    private String commissionPercent;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("uts")
    @Expose
    private Integer uts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUts() {
        return uts;
    }

    public void setUts(Integer uts) {
        this.uts = uts;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", name='" + name + '\'' +
                ", kind='" + kind + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", currencyValue='" + currencyValue + '\'' +
                ", commissionPercent='" + commissionPercent + '\'' +
                ", status='" + status + '\'' +
                ", uts=" + uts +
                '}';
    }
}
