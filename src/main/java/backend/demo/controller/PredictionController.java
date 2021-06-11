package backend.demo.controller;



import backend.demo.model.Customer;
import org.springframework.web.bind.annotation.*;

import backend.demo.services.Conexion;

import java.io.IOException;

@CrossOrigin({"*"})
@RestController
@RequestMapping(path = "/customer")
public class PredictionController {


    private final Conexion conexion = new Conexion();

    @PostMapping("/predecir")
    public String save(@RequestBody Customer model) throws IOException {
        /*Estructura de Json*/
        String json = "{\n" +
                "    \"model\": \"deepnet/60b542b75e269e0554002475\",\n" +
                "    \"input_data\" : {\n" +
                "        \"Customer service calls\":  " + model.getCustomer_service_calls() + " ,\n" +
                "        \"International plan\": " + model.getInternational_plan() + ",\n" +
                "        \"Voice mail plan\": " + model.getVoice_mail_plan() + ",\n" +
                "        \"Total day minutes\": " + model.getTotal_Day_Minutes() + ",\n" +
                "        \"Total eve minutes\": " + model.getTotal_eve_minutes() + ",\n" +
                "        \"Total intl calls\": " + model.getTotal_intl_calls() + ",\n" +
                "        \"Total night minutes\": " + model.getTotal_night_minutes() + ",\n" +
                "        \"Number vmail messages\": " + model.getNumber_vmail_messages() + "\n" +
                "    }\n" +
                "}";
        /*Retonarmos la respuesta de la conexion*/
        return conexion.conectar(json);
    }


}
