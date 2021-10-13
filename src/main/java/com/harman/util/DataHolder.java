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
    private static DataHolder instance = new DataHolder(15);
    private boolean isDataAccess = false;

    Logger logger = LoggerFactory.getLogger(DataHolder.class);

    private DataHolder(int capacity) {
        this.capacity = capacity;
    }

    //Creating an object of datastore class
    public static DataHolder getInstance(){
        return instance;
    }

    //method to add each character into the list
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

    //To sort the characters based on power and access count
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

    //To refresh data after every few seconds
    public void refreshData(Characters newCharacter){
        for(Characters character : queue){
            if(character.getName().equalsIgnoreCase(newCharacter.getName())){
                character.setMax_power(newCharacter.getMax_power());
                break;
            }
        }
    }

    //Optional method which can be implemented in the schedules task to make the list pre filled
    public  void putAll(Characters characters){
        if(capacity != queue.size()){
            queue.add(characters);
        }
    }


    //To obtain the entered character name
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
    
    //To check if the character name is received
    public boolean isDataAccess() {
        return isDataAccess;
    }

    public void setDataAccess(boolean dataAccess) {
        isDataAccess = dataAccess;
    }
}
