package com.harman.util;

import com.harman.models.Characters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class DataHolder {

    private int capacity;
    private List<Characters> queue = new LinkedList<Characters>();
    private static DataHolder instance = new DataHolder(5);
    private boolean isDataAccess = false;

    Logger logger = LoggerFactory.getLogger(DataHolder.class);

    private DataHolder(int capacity) {
        this.capacity = capacity;
    }

    public static DataHolder getInstance(){
        return instance;
    }

    public  void put(Characters character){

        if(queue.contains(character)){
            int index = queue.indexOf(character);
            queue.set(index,character);
            return;
        }

        if(queue.size() < capacity ){
            queue.add(character);
            return;
        }

        if(queue.size() == capacity){
            sortBasedOnAccesCountAndMaxPower();
            queue.remove(0);
            queue.add(character);
        }
    }

    public void sortBasedOnAccesCountAndMaxPower(){
        Collections.sort(queue, new Comparator<Characters>() {
            @Override
            public int compare(Characters o1, Characters o2) {
                if(o1.getAccessCount().compareTo(o2.getAccessCount()) == 0){
                    return o1.getMax_power().compareTo(o2.getMax_power());
                }
                return o1.getAccessCount().compareTo(o2.getAccessCount());
            }
        });


    }

    public void refreshData(Characters newCharacter){
        for(Characters character : queue){
            if(character.getName().equalsIgnoreCase(newCharacter.getName())){
                character.setMax_power(newCharacter.getMax_power());
                break;
            }
        }
    }

    public  void putAll(Characters characters){
        if(capacity != queue.size()){
            queue.add(characters);
        }
    }



    public  Characters get(String characterName){
        isDataAccess = true;
        Characters foundCharacter = null;
        for(Characters character : queue){
            if(character.getName().equalsIgnoreCase(characterName)){
                character.setAccessCount(character.getAccessCount()+1);
                foundCharacter = character;
                break;
            }
        }

        return foundCharacter;
    }

    public void print(){
        for(Characters character : queue){
            logger.info(character.toString());
        }
    }

    public boolean isDataAccess() {
        return isDataAccess;
    }

    public void setDataAccess(boolean dataAccess) {
        isDataAccess = dataAccess;
    }
}
