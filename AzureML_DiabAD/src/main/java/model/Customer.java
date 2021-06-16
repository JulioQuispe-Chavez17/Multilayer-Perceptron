package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Customer implements Serializable {
    private @Getter
    @Setter
    Integer Customer_service_calls, Total_Day_Minutes,
            Total_eve_minutes, Total_intl_calls,
            Total_night_minutes, Number_vmail_messages;
    private @Getter
    @Setter String International_plan, Voice_mail_plan;

    @Getter @Setter double confidence;
    @Getter @Setter String category;
    @Getter @Setter double credits;
    @Getter @Setter double probability;
    @Getter @Setter String result;
    private @Getter @Setter String churn;
}
