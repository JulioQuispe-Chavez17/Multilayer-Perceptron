package services;

import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import model.Customer;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

public class Services {

    public static JSONObject obtenerJSon(Customer modelo) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"Inputs\": {\r\n"
                + "\"input1\": [\r\n      {\r\n"
                + "\"International plan\": 'Yes',\r\n"
                + "\"Voice mail plan\": 'Yes',\r\n"
                + "\"Number vmail messages\":" + modelo.getNumber_vmail_messages() + ",\r\n"
                + "\"Total day minutes\":" + modelo.getTotal_Day_Minutes() + ",\r\n"
                + "\"Total eve minutes\":"+ modelo.getTotal_eve_minutes() + ",\r\n"
                + "\"Total night minutes\":" + modelo.getTotal_night_minutes()  + ",\r\n"
                + "\"Total intl calls\":" + modelo.getTotal_intl_calls() + ",\r\n"
                + "\"Customer service calls\":" + modelo.getCustomer_service_calls() + ",\r\n"
                + "\"Churn\": \"\"\r\n      }\r\n]"
                + "\r\n  },\r\n  "
                + "\"GlobalParameters\": {}\r\n}");

        String name = "{\n" +
                "  \"Inputs\": {\n" +
                "    \"input1\": [\n" +
                "        {           \n" +
                "        \"International plan\": "+modelo.getInternational_plan()+",\n" +
                "        \"Voice mail plan\": "+modelo.getVoice_mail_plan()+",\n" +
                "        \"Number vmail messages\": "+modelo.getNumber_vmail_messages()+",\n" +
                "        \"Total day minutes\": "+ modelo.getTotal_Day_Minutes() +",\n" +
                "        \"Total eve minutes\": "+ modelo.getTotal_eve_minutes() +",\n" +
                "        \"Total night minutes\": "+ modelo.getTotal_night_minutes() +",\n" +
                "        \"Total intl calls\": "+modelo.getTotal_intl_calls()+",\n" +
                "        \"Customer service calls\": "+modelo.getCustomer_service_calls()+",\n" +
                "        \"Churn\": \"\"\n" +
                "        }\n" +
                "    ]},\n" +
                "    \"GlobalParameters\": {}\n" +
                "}";
        Request request = new Request.Builder()
                .url("https://ussouthcentral.services.azureml.net/workspaces/5f00c6c80a3947b081534cdb8fe9349a/services/4989fa47624f4dea9a2e5cd0539cd0c1/execute?api-version=2.0&format=swagger")
                .method("POST", body)
                .addHeader("Authorization", "Bearer wHxlJkqCazIG7nmfWaCSOUO/82LZg1cRFjkASqW+9ia/QUb6nO/B/r/lxBpuCj3xzLnuFl0CH1MAyGUcqipGDQ==")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        Gson gson = new Gson();
        // Convierte la cadena body en un objeto jsonObject
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONObject cadenaJson = jsonObject.getJSONObject("Results")
                .getJSONArray("output1")
                .getJSONObject(0);
        return cadenaJson;
    }

    public static void main(String[] args) throws IOException {
        try {
            Customer customertest = new Customer();
            customertest.setCustomer_service_calls(20);
            customertest.setInternational_plan("Yes");
            customertest.setNumber_vmail_messages(25);
            customertest.setTotal_Day_Minutes(1000);
            customertest.setTotal_eve_minutes(2000);
            customertest.setTotal_intl_calls(3000);
            customertest.setTotal_night_minutes(4000);
            customertest.setVoice_mail_plan("Yes");

            System.out.println("Lista " + Services.obtenerJSon(customertest));
            JSONObject cadenaJson = Services.obtenerJSon(customertest);
            System.out.println("Scored " + cadenaJson.getString("Scored Labels"));
            System.out.println("Probabilities " + cadenaJson.getDouble("Scored Probabilities"));
        } catch (Exception e) {
            System.out.println("error en " + e.getMessage());
        }
    }
}
