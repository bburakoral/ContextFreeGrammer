package com.company;

import java.util.ArrayList;

public class Transition {

    private ArrayList<String> transition;

    public Transition(ArrayList<String> transition) {
        this.transition = transition;
    }

    public Transition() {
        this.transition = new ArrayList<String>();
    }

    public Transition(String[] strArray){
        this.transition = new ArrayList<String>();

        for(String str: strArray){
            this.transition.add(str);
        }

    }
    public ArrayList<String> getTransition() {
        return transition;
    }

    public void setTransition(ArrayList<String> transition) {
        this.transition = transition;
    }

    public boolean addChar(String ch){
        return this.transition.add(ch);
    }

    public boolean removeChar(String ch){
        return this.transition.remove(ch);
    }

    public String toString(){
        String result = "";
        for(String str: this.transition){
            result = result + str;
        }
        return result;
    }

}
