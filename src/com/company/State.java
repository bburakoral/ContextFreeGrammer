package com.company;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class State {

    private String name;
    private Set<Transition> transitions;

    public State(String name, Set<Transition> transitions) {
        this.name = name;
        this.transitions = transitions;
    }

    public State() {
    }

    public State(String name) {
        this.name = name;
        this.transitions = new LinkedHashSet<Transition>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<Transition> transitions) {
        this.transitions = transitions;
    }

    public boolean addTransition(Transition transition){
        return this.transitions.add(transition);
    }
    public boolean addTransition(ArrayList<String> aList){
        return this.transitions.add(new Transition(aList));
    }

    public boolean removeTransition(Transition transition){
        return this.transitions.remove(transition);
    }
    public void removeTransition(String str){
        Transition temp = new Transition();
        for(Transition transition: this.transitions){
            if(transition.toString().equals(str)){
                temp = transition;

            }
        }
        transitions.remove(temp);
    }


}
