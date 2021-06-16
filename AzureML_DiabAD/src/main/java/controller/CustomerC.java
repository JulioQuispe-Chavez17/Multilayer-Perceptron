package controller;

import lombok.Data;
import model.Customer;
import org.primefaces.json.JSONObject;
import services.Services;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;


@Named(value = "customerC")
@SessionScoped
@Data
public class CustomerC implements Serializable {
    Customer customer;

    public CustomerC() {
        customer = new Customer();
    }

    public  void obtenerDatos() throws IOException, InterruptedException {
        try {
            JSONObject cadenaJson = Services.obtenerJSon(customer);
            customer.setCategory(cadenaJson.getString("Scored Labels"));
            customer.setProbability(cadenaJson.getDouble("Scored Probabilities"));
            if (customer.getCategory().equals("False")) {
                customer.setResult("El cliente no renueva el servicio");
            } else {
                customer.setResult("El cliente renueva el servicio");
            }
        } catch (Exception e) {
            System.out.println("Error en obtenerDatosC: " + e.getMessage());
            e.printStackTrace();
        }



    }



}

   /* Request-Response
    /execute?api-version=2.0&format=swagger
    {
   {
      "Inputs": {
        "input1": [
         {
            "Embarazos": 3,
            "Glucosa": 110,
           "Presión sanguínea": 75,
            "Pliegue cutáneo": 21,
            "Insulina": 81,
            "Índice de masa corporal": 32.168,
            "Pedigrí diabetes": 0.42354,
            "Edad": 33,
            "Diabetes": "",
            "Medicación previa": "",
            "Observaciones": "",
            "Fecha de diagnóstico": ""
          }
        ]
      },
      "GlobalParameters": {}
    }
}
*/