package com.harman.service;

import com.harman.controller.MockyClient;
import com.harman.models.Characters;
import com.harman.util.DataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
public class CharacterService {

    Logger logger = LoggerFactory.getLogger(CharacterService.class);

    DataHolder dataStore = DataHolder.getInstance();

    public Integer getMAxPower(String actorName) {
        Characters actor = dataStore.get(actorName);
        Integer maxPower = null;
        if (actor == null) {
            logger.info("cache miss for actor : " + actorName);
            Characters actorFromService = getFromService(actorName);
            actorFromService.setAccessCount(1);
            if (actorFromService == null) return null;
            dataStore.put(actorFromService);
            actor = actorFromService;
//            actor = dataStore.get(actorName);
        }

        dataStore.print();
        return actor.getMax_power();

    }

    private Characters getFromService(String actorName) {
        try {
            MockyClient mockyClient = new MockyClient();
            List<Characters> characters = mockyClient.getCharList();
            for(Characters character : characters){
                if(character.getName().equals(actorName)){
                    return character;
                }
            }

        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;

    }
}
