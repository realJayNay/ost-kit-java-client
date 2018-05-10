package ostSdk.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirdropStatus {
    @SerializedName("airdrop_uuid")
    @Expose
    private String airdropUuid;
    @SerializedName("current_status")
    @Expose
    private String currentStatus;
    @SerializedName("steps_complete")
    @Expose
    private List<String> stepsComplete = null;

    public String getAirdropUuid() {
        return airdropUuid;
    }

    public void setAirdropUuid(String airdropUuid) {
        this.airdropUuid = airdropUuid;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<String> getStepsComplete() {
        return stepsComplete;
    }

    public void setStepsComplete(List<String> stepsComplete) {
        this.stepsComplete = stepsComplete;
    }

    @Override
    public String toString() {
        return "AirdropStatus{" +
                "airdropUuid='" + airdropUuid + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", stepsComplete=" + stepsComplete +
                '}';
    }
}
