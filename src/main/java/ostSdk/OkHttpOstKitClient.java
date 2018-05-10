package ostSdk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ostSdk.dto.AirdropStatus;
import ostSdk.dto.ListType;
import ostSdk.dto.Transaction;
import ostSdk.dto.TransactionType;
import ostSdk.dto.TransactionTypeCurrency;
import ostSdk.dto.TransactionTypeKind;
import ostSdk.dto.User;
import ostSdk.hmac.HmacSigningInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpOstKitClient {
    private final OkHttpClient client;
    private final HttpUrl baseUrl;
    private final Gson gson = new Gson();
    private final JsonParser jsonParser = new JsonParser();
    private final static Logger log = LoggerFactory.getLogger(OkHttpOstKitClient.class);

    public static OkHttpOstKitClient create(String apiKey, String apiSecret, String url) {
        return new OkHttpOstKitClient(apiKey, apiSecret, url);
    }

    public static OkHttpOstKitClient create(String apiKey, String apiSecret) {
        return create(apiKey, apiSecret, "https://playgroundapi.ost.com");
    }

    private OkHttpOstKitClient(String apiKey, String secretKey, String url) {
        baseUrl = HttpUrl.parse(url);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(500);
        dispatcher.setMaxRequestsPerHost(150);
        client = new OkHttpClient.Builder()
                .addInterceptor(new HmacSigningInterceptor(apiKey, secretKey, "HmacSHA256"))
                .connectionPool(new ConnectionPool(10, 2, TimeUnit.MINUTES))
                .connectTimeout(3, TimeUnit.SECONDS)
                .dispatcher(dispatcher)
                .retryOnConnectionFailure(false)
                .build();
    }

    public User createUser(String name) throws IOException {
        String json = post("/users/create", new ImmutablePair<>("name", name));
        List<User> result = getResult(json, User.class);
        return result.isEmpty() ? null : result.get(0);
    }

    public User editUser(String uuid, String name) throws IOException {
        String json = post("/users/edit", new ImmutablePair<>("uuid", uuid), new ImmutablePair<>("name", name));
        List<User> result = getResult(json, User.class);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<User> listUsers() throws IOException {
        String json = get("/users/list");
        return getResult(json, User.class);
    }

    public TransactionType createTransactionType(String name, TransactionTypeKind kind, double currencyValue, TransactionTypeCurrency currency, double commissionPercent) throws IOException {
        String json = post("/transaction-types/create",
                new ImmutablePair<>("name", name),
                new ImmutablePair<>("kind", kind.getCode()),
                new ImmutablePair<>("currency_type", currency.name()),
                new ImmutablePair<>("currency_value", String.valueOf(currencyValue)),
                new ImmutablePair<>("commission_percent", String.valueOf(commissionPercent))
        );
        List<TransactionType> result = getResult(json, TransactionType.class);
        return result.isEmpty() ? null : result.get(0);
    }

    public TransactionType editTransactionType(String uuid, String name, TransactionTypeKind kind, double amount, TransactionTypeCurrency currency, double commissionPercent) throws IOException {
        String json = post("/transaction-types/edit",
                new ImmutablePair<>("client_transaction_id", uuid),
                new ImmutablePair<>("name", name),
                new ImmutablePair<>("kind", kind.getCode()),
                new ImmutablePair<>("currency_type", currency.name()),
                new ImmutablePair<>("currency_value", String.valueOf(amount)),
                new ImmutablePair<>("commission_percent", String.valueOf(commissionPercent))
        );
        List<TransactionType> result = getResult(json, TransactionType.class);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<TransactionType> listTransactionTypes() throws IOException {
        String json = get("/transaction-types/list");
        return getResult(json, TransactionType.class);
    }

    public String executeTransactionType(String fromUuid, String toUuid, String name) throws IOException {
        String json = post("/transaction-types/execute",
                new ImmutablePair<>("from_uuid", fromUuid),
                new ImmutablePair<>("to_uuid", toUuid),
                new ImmutablePair<>("transaction_kind", name)
        );
        JsonElement root = jsonParser.parse(json);
        JsonObject data = root.getAsJsonObject().get("data").getAsJsonObject();
        return data.get("transaction_uuid").getAsString();
    }

    public Transaction getTransactionStatus(String uuid) throws IOException {
        String json = post("/transaction-types/status", new ImmutablePair<>("transaction_uuids[]", uuid));
        List<Transaction> result = getResult(json, Transaction.class);
        return result.isEmpty() ? null : result.get(0);
    }

    public String airdrop(double amount, ListType listType) throws IOException {
        String json = post("/users/airdrop/drop",
                new ImmutablePair<>("amount", String.valueOf(amount)),
                new ImmutablePair<>("list_type", listType.getCode())
        );
        JsonElement root = jsonParser.parse(json);
        JsonObject data = root.getAsJsonObject().get("data").getAsJsonObject();
        return data.get("airdrop_uuid").getAsString();
    }

    public AirdropStatus getAirdropStatus(String uuid) throws IOException {
        String json = get("/users/airdrop/status", new ImmutablePair<>("airdrop_uuid", uuid));
        JsonElement root = jsonParser.parse(json);
        JsonObject data = root.getAsJsonObject().get("data").getAsJsonObject();
        return gson.fromJson(data, AirdropStatus.class);
    }

    private <T> List<T> getResult(String json, Class<T> classOfT) {
        JsonElement root = jsonParser.parse(json);
        JsonObject data = root.getAsJsonObject().get("data").getAsJsonObject();
        String resultType = data.get("result_type").getAsString();
        JsonArray result = data.get(resultType).getAsJsonArray();
        List<T> items = new ArrayList<>();
        for (JsonElement element : result) {
            T user = gson.fromJson(element, classOfT);
            items.add(user);
        }
        return items;
    }

    public String getUserTokenBalance(User user) throws IOException {
        return getUserTokenBalance(user.getUuid(), user.getName());
    }

    public String getUserTokenBalance(String uuid, String name) throws IOException {
        User user = editUser(uuid, name);
        return user.getTokenBalance();
    }

    private String post(String endpoint, Pair<String, String>... args) throws IOException {
        HttpUrl url = baseUrl.newBuilder(endpoint).build();
        Request.Builder request = new Request.Builder();
        FormBody.Builder formBody = new FormBody.Builder();
        for (Pair<String, String> arg : args) {
            formBody.addEncoded(arg.getKey(), arg.getValue());
        }
        Call call = client.newCall(request.url(url).post(formBody.build()).build());
        okhttp3.Response response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            String body = response.body().string();
            log.debug("{} -> {}", endpoint, body);
            if (body.startsWith("{\"success\":false")) {
                throw new IOException(jsonParser.parse(body).getAsJsonObject().get("err").getAsJsonObject().get("msg").getAsString());
            }
            return body;
        } else {
            throw new IOException("Unable to POST request: code=" + response.code() + ", message=" + response.message());
        }
    }

    private String get(String endpoint, Pair<String, String>... args) throws IOException {
        Request.Builder request = new Request.Builder();
        HttpUrl.Builder url = baseUrl.newBuilder(endpoint);
        for (Pair<String, String> arg : args) {
            url.addQueryParameter(arg.getKey(), arg.getValue());
        }

        Call call = client.newCall(request.url(url.build()).get().build());
        okhttp3.Response response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            String body = response.body().string();
            log.debug("{} -> {}", endpoint, body);
            if (body.startsWith("{\"success\":false")) {
                throw new IOException(jsonParser.parse(body).getAsJsonObject().get("err").getAsJsonObject().get("msg").getAsString());
            }
            return body;
        } else {
            throw new IOException("Unable to GET request: code=" + response.code() + ", message=" + response.message());
        }
    }

    public static void main(String[] args) throws IOException {
        OkHttpOstKitClient ost = OkHttpOstKitClient.create(args[0], args[1]);

        /* USERS functions */
        // Create a user named 'Ria'.
        User user = ost.createUser("Ria");
        System.out.println(user);

        // ... and rename 'Ria' to 'Fred'.
        user = ost.editUser(user.getUuid(), "Fred");
        System.out.println(user);

        // List users.
        List<User> users = ost.listUsers();
        System.out.println(users);


        /* TRANSACTION TYPES functions */
        // Create a transaction type.
        TransactionType transactionType = ost.createTransactionType("Clap1", TransactionTypeKind.USER_TO_USER, 1, TransactionTypeCurrency.BT, 0); // user_to_user transaction of 1 BT named 'Clap'
        System.out.println(transactionType);

        // List transaction types.
        List<TransactionType> transactionTypes = ost.listTransactionTypes();
        System.out.println(transactionTypes);

        // Execute a transaction type. This transfers a preconfigured amount of Branded Tokens from a user or company to another user or company.
        String transactionUuid = ost.executeTransactionType("72b683c0-0877-4ff9-ba82-f687dfa81313", "0fe12919-73c0-46ef-990c-637b2f72e4be", "Clap");

        // ... and retrieve the status of the transaction. Allow the transaction some time to get processed on the OpenST utility chains.
        Transaction status = ost.getTransactionStatus(transactionUuid);
        System.out.println(status);


        /* AIRDROP functions */
        // Airdrop tokens either to all users or only to the users that have never been airdropped before.
        String airdropUuid = ost.airdrop(1, ListType.ALL); // airdrop 1 token to all users
        System.out.println(airdropUuid);

        // ... and retrieve the status of the airdrop.
        AirdropStatus airdropStatus = ost.getAirdropStatus(airdropUuid);
        System.out.println(airdropStatus);


        /* NON-API functions */
        // Retrieve a single user's token balance.
        //This is not implemented by the OST KIT API, but is done via a workaround by renaming a user to its own username to get the user info.
        String tokenBalance = ost.getUserTokenBalance(user.getUuid(), user.getName());
        System.out.println(tokenBalance);

        // ... or just pass the user object
        tokenBalance = ost.getUserTokenBalance(user);
        System.out.println(tokenBalance);
    }
}
