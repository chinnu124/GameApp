package com.harman.util;


import com.harman.controller.MockyClient;
import com.harman.models.Characters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class ScheduleTask {

    Logger logger = LoggerFactory.getLogger(ScheduleTask.class);


    @Scheduled(fixedRate = 10000) // This time in milliseconds
    public void pullData() throws Exception {
        logger.info("Get Data ");
        MockyClient mockyClient = new MockyClient();
        List<Characters> characters = mockyClient.getCharMap();
        DataHolder dataStore = DataHolder.getInstance();
        // Once the data access is done then use this to refresh the data
        if (dataStore.isDataAccess()) {
            for (Characters actor : characters) {
                dataStore.refreshData(actor);
            }
        }
        //This will get execute until there is no request made by client based on sorted max power
        else {
            Collections.sort(characters);
            for (Characters actor : characters) {
                dataStore.putAll(actor);
            }
        }

    }


}
