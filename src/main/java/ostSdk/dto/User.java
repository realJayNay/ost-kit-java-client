package ostSdk.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("total_airdropped_tokens")
    @Expose
    private String totalAirdroppedTokens;
    @SerializedName("token_balance")
    @Expose
    private String tokenBalance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTotalAirdroppedTokens() {
        return totalAirdroppedTokens;
    }

    public void setTotalAirdroppedTokens(String totalAirdroppedTokens) {
        this.totalAirdroppedTokens = totalAirdroppedTokens;
    }

    public String getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(String tokenBalance) {
        this.tokenBalance = tokenBalance;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", tokenBalance='" + tokenBalance + '\'' +
                '}';
    }
}
