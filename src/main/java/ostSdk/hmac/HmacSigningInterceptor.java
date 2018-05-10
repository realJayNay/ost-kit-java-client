package ostSdk.hmac;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.ByteString;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class HmacSigningInterceptor implements Interceptor {

    private static final Escaper ESCAPER = UrlEscapers.urlFormParameterEscaper();

    private final String apiKey;
    private final String secretKey;
    private final String hmacAlgo;

    public HmacSigningInterceptor(String apiKey, String secretKey, String hmacAlgo) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.hmacAlgo = hmacAlgo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(signRequest(chain.request()));
    }

    private Request signRequest(Request request) {
        HttpUrl url = request.url();
        SortedMap<String, String> parameters = new TreeMap<>();
        parameters.put("request_timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        parameters.put("api_key", ESCAPER.escape(apiKey));
        if ("GET".equals(request.method())) {
            for (int i = 0; i < url.querySize(); i++) {
                parameters.put(ESCAPER.escape(url.queryParameterName(i)), ESCAPER.escape(url.queryParameterValue(i).replace("%20", "+").replace("%5B%5D", "[]")));
            }
        } else if ("POST".equals(request.method())) {
            RequestBody requestBody = request.body();
            if (requestBody instanceof FormBody) {
                FormBody formBody = (FormBody) requestBody;
                for (int i = 0; i < formBody.size(); i++) {
                    String value = formBody.value(i);
                    parameters.put(formBody.name(i).replace("%5B%5D", "[]"), ESCAPER.escape(value.replace("%20", "+").replace("%5B%5D", "[]")));
                }
            }
        }

        Buffer input = new Buffer();
        for (String path : url.pathSegments()) {
            input.writeByte('/').writeUtf8(ESCAPER.escape(path));
        }
        input.writeByte('?');

        HttpUrl.Builder builder = request.url().newBuilder().query(null);

        FormBody.Builder body = "POST".equals(request.method()) ? new FormBody.Builder() : null;
        boolean first = true;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (!first) {
                input.writeByte('&');
            }
            first = false;
            input.writeUtf8(entry.getKey());
            input.writeByte('=');
            input.writeUtf8(entry.getValue());
            if (body == null) {
                builder.addQueryParameter(ESCAPER.escape(entry.getKey()), ESCAPER.escape(entry.getValue()));
            } else {
                body.addEncoded(entry.getKey(), entry.getValue());
            }
        }

        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), hmacAlgo);
        Mac mac;
        try {
            mac = Mac.getInstance(hmacAlgo);
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
        byte[] bytes = input.readByteArray();
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        byte[] result = mac.doFinal(bytes);
        String signatureValue = ByteString.of(result).hex();

        if (body == null) {
            builder.addQueryParameter("signature", signatureValue);
            return request.newBuilder().url(builder.build()).build();
        } else {
            body.addEncoded("signature", signatureValue);
            return request.newBuilder().post(body.build()).url(builder.build()).build();
        }
    }
}
