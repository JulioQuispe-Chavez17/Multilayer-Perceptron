package services;

import com.google.gson.Gson;
import java.io.IOException;

import model.Diabetes;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

public class Servite {
    public static JSONObject obtenerJSon(Diabetes modelo) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"Inputs\": {\r\n"
                + "\"input1\": [\r\n      {\r\n"
                + "\"Embarazos\":" + modelo.getEmbarazos() + ",\r\n"
                + "\"Glucosa\":" + modelo.getGlucosa() + ",\r\n"
                + "\"Presión sanguínea\":" + modelo.getPresion() + ",\r\n"
                + "\"Pliegue cutáneo\":" + modelo.getPliegue() + ",\r\n"
                + "\"Insulina\":"+ modelo.getInsulina() + ",\r\n"
                + "\"Índice de masa corporal\":" + modelo.getImc() + ",\r\n"
                + "\"Pedigrí diabetes\":" + modelo.getPedigri() + ",\r\n"
                + "\"Edad\":" + modelo.getEdad() + ",\r\n"
                + "\"Diabetes\": \"\",\r\n"
                + "\"Medicación previa\": \"\",\r\n"
                + "\"Observaciones\": \"\",\r\n"
                + "\"Fecha de diagnóstico\": \"\"\r\n      }\r\n]"
                + "\r\n  },\r\n  "
                + "\"GlobalParameters\": {}\r\n}");
        Request request = new Request.Builder()
                .url("https://ussouthcentral.services.azureml.net/workspaces/b157bc48c63b40c0bb9c27bb06e533af/services/862382193e59440a8894946d9d3a9d28/execute?api-version=2.0&format=swagger")
                .method("POST", body)
                .addHeader("Authorization", "Bearer s1mPicE5QScWiOTDeFvrrpYyhjd8kH0N5RyCF41gba6rq/OGnPBCpRn0dd0NrzHi78B3N32NsTIHqCOUQS6v/A==")
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
            Diabetes diabetes = new Diabetes();
            diabetes.setEdad(33);
            diabetes.setGlucosa(121);
           diabetes.setPresion(69);
            diabetes.setInsulina(79);
            diabetes.setImc(32.16743);
            diabetes.setPedigri(0.47253);
            diabetes.setPliegue(20);
            diabetes.setEmbarazos(3);
           System.out.println("Lista " + Servite.obtenerJSon(diabetes));
           JSONObject cadenaJson = Servite.obtenerJSon(diabetes);
            System.out.println("Scored " + cadenaJson.getString("Scored Labels"));
            System.out.println("Probabilities " + cadenaJson.getDouble("Scored Probabilities"));
        } catch (Exception e) {
            System.out.println("error en " + e.getMessage());
        }
    }
}

//    Request-Response
//    /execute?api-version=2.0&format=swagger
//    {
//    {
//      "Inputs": {
//        "input1": [
//          {
//            "Embarazos": 3,
//            "Glucosa": 110,
//            "Presión sanguínea": 75,
//            "Pliegue cutáneo": 21,
//            "Insulina": 81,
//            "Índice de masa corporal": 32.168,
//            "Pedigrí diabetes": 0.42354,
//            "Edad": 33,
//            "Diabetes": "",
//            "Medicación previa": "",
//            "Observaciones": "",
//            "Fecha de diagnóstico": ""
//          }
//        ]
//      },
//      "GlobalParameters": {}
//    }

