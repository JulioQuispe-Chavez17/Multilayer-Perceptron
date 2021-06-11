package backend.demo.services;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Conexion {

    public String conectar(String json) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            /*Agregamos la api que nos otorga bigML,con nuestro usuario y key*/
            HttpPost httpPost = new HttpPost("https://bigml.io/andromeda/prediction?username=julioquispe&api_key=45144bb86790abe56fc4b3a66ca1767bd5581b78");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            /*Ingresamos nuestros datos con formato Json*/
            StringEntity stringEntity = new StringEntity(json);

            /*Enviamos nuestro Json*/
            httpPost.setEntity(stringEntity);

            /*Personalisamos los tipos de respuesta*/
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Estado de la respuesta " + status);
                }
            };

            /*Agregamos  nuestra respuesta*/
            String responseBody = httpclient.execute(httpPost, responseHandler);

            /*Parseamos nuestra respuesta*/
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody);

            Double confidence = jsonObject.get("confidence").getAsDouble();
            Integer category = jsonObject.get("category").getAsInt();
            Double credits = jsonObject.get("credits").getAsDouble();
            Double probability = jsonObject.get("probability").getAsDouble();
            String output = jsonObject.get("output").getAsString();


            String json_respuesta = "{\n" +
                    "\"confidence\": "+confidence+",\n" +
                    "\"category\": "+category+",\n" +
                    "\"credits\": "+credits+",\n" +
                    "\"probability\": "+probability+",\n" +
                    "\"output\": "+output+"\n" +
                    "}";

            /*Obtenes la respuesta del Json*/
            return json_respuesta;
        }


    }
}