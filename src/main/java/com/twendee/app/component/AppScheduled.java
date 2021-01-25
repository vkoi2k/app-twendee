package com.twendee.app.component;




import com.twendee.app.model.entity.BaseEntity;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


@Component
public class AppScheduled {
    @Scheduled(cron = "0 * * * * *")
    public void AutoCreatTimekeeping(){

        Request request=new Request();
        if(request.getLateEarly()==null) System.out.println("getLateEarly");
        if(request.getAbsenceOutside()==null) System.out.println("getAbsenceOutside");
        if(request.getCheckoutSupport()==null) System.out.println("getCheckoutSupport");
    }

}
