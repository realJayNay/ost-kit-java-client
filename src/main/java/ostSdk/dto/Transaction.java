package ostSdk.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("transaction_uuid")
    @Expose
    private String transactionUuid;
    @SerializedName("from_user_id")
    @Expose
    private String fromUserId;
    @SerializedName("to_user_id")
    @Expose
    private String toUserId;
    @SerializedName("transaction_type_id")
    @Expose
    private String transactionTypeId;
    @SerializedName("client_token_id")
    @Expose
    private Integer clientTokenId;
    @SerializedName("transaction_hash")
    @Expose
    private String transactionHash;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("gas_price")
    @Expose
    private String gasPrice;
    @SerializedName("transaction_timestamp")
    @Expose
    private Integer transactionTimestamp;
    @SerializedName("uts")
    @Expose
    private Integer uts;
    @SerializedName("gas_used")
    @Expose
    private String gasUsed;
    @SerializedName("transaction_fee")
    @Expose
    private String transactionFee;
    @SerializedName("block_number")
    @Expose
    private Integer blockNumber;
    @SerializedName("bt_transfer_value")
    @Expose
    private String btTransferValue;
    @SerializedName("bt_commission_amount")
    @Expose
    private String btCommissionAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionUuid() {
        return transactionUuid;
    }

    public void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(String transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Integer getClientTokenId() {
        return clientTokenId;
    }

    public void setClientTokenId(Integer clientTokenId) {
        this.clientTokenId = clientTokenId;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public Integer getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(Integer transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public Integer getUts() {
        return uts;
    }

    public void setUts(Integer uts) {
        this.uts = uts;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(String transactionFee) {
        this.transactionFee = transactionFee;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBtTransferValue() {
        return btTransferValue;
    }

    public void setBtTransferValue(String btTransferValue) {
        this.btTransferValue = btTransferValue;
    }

    public String getBtCommissionAmount() {
        return btCommissionAmount;
    }

    public void setBtCommissionAmount(String btCommissionAmount) {
        this.btCommissionAmount = btCommissionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", transactionUuid='" + transactionUuid + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", transactionTypeId='" + transactionTypeId + '\'' +
                ", clientTokenId=" + clientTokenId +
                ", transactionHash='" + transactionHash + '\'' +
                ", status='" + status + '\'' +
                ", gasPrice='" + gasPrice + '\'' +
                ", transactionTimestamp=" + transactionTimestamp +
                ", uts=" + uts +
                ", gasUsed='" + gasUsed + '\'' +
                ", transactionFee='" + transactionFee + '\'' +
                ", blockNumber=" + blockNumber +
                ", btTransferValue='" + btTransferValue + '\'' +
                ", btCommissionAmount='" + btCommissionAmount + '\'' +
                '}';
    }

}