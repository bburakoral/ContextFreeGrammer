package com.company;

import java.util.*;

public class CFG {

    private Set<State> states;

    public CFG(Set<State> states) {
        this.states = states;
    }

    public CFG() {
        this.states = new LinkedHashSet<State>();
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public boolean addState(State state){
        return this.states.add(state);
    }

    public boolean removeState(State state){
        return this.states.remove(state);
    }

    public void printCFG(){
        for(State state: this.states){
            for(Transition transition: state.getTransitions()){
                System.out.println(state.getName() +" -> " + transition.toString());
            }
        }
    }


    public void eliminateEpsilons(){
        Set<String> nullables = new LinkedHashSet<>();

        for(State state: this.states){      //Adding to array states that directly have epsilon.
            if(isNullable(state)){
                nullables.add(state.getName());
            }
        }

        for(int i=0; i<states.size(); i++){
            for(State state: this.states){
                for(Transition transition: state.getTransitions()){
                    if(transition.getTransition().size()==1 && nullables.contains(transition.getTransition().get(0))){
                        nullables.add(state.getName());
                    }
                }
            }
        }
        for(State state: this.states){
            state.removeTransition("<epsilon>");
        }

        int maxTransitionSize = maxStateNumberForSingleTransition();
        for(int i=0; i<maxTransitionSize*maxTransitionSize; i++){
            for(String nullable: nullables){


                for(int s=0; s < states.toArray().length; s++){
                    State state = (State) states.toArray()[s];

                    for(int t=0; t < state.getTransitions().toArray().length; t++){
                        boolean controlForHasNullable=false;
                        String str = "";
                        ArrayList<String> temp = new ArrayList<>();

                        Transition transition = (Transition) state.getTransitions().toArray()[t];


                        for(String ch: transition.getTransition()){
                            if(ch.equals(nullable)){
                                controlForHasNullable=true;
                                break;
                            }
                        }
                        if(controlForHasNullable){
                            for(String ch: transition.getTransition()){
                                Random rand = new Random();
                                if(!ch.equals(nullable) || rand.nextBoolean()){
                                    temp.add(ch);
                                    str = str + ch;
                                }
                            }
                            if(temp.size()>0 && !hasThisTransition(state,str)){     //to prevent dublicates in arraylist.
                                state.addTransition(temp);
                            }
                        }


                    }

                }
            }
        }

    }

    public boolean hasThisTransition(State state, String str){
        boolean result = false;
        for(Transition transition: state.getTransitions()){
            if(transition.toString().equals(str))
                result = true;
        }
        return result;
    }

    public boolean isNullable(State state){
        boolean isNullable = false;
        for(Transition transition: state.getTransitions()){
            if(transition.getTransition().get(0).equals("<epsilon>")){
                isNullable = true;
                break;
            }
        }
        return isNullable;
    }

    public int maxStateNumberForSingleTransition(){
        int max=0;
        for(State state: this.states){
            for(Transition transition: state.getTransitions()){
                if(transition.getTransition().size() > max){
                    max = transition.getTransition().size();
                }
            }
        }
        return max;
    }

    public void eliminateUnitProductions(){

        for(int i=0; i<states.size(); i++){
            for(int s=0; s < states.toArray().length; s++) {
                State state = (State) states.toArray()[s];

                for (int t = 0; t < state.getTransitions().toArray().length; t++) {
                    Transition transition = (Transition) state.getTransitions().toArray()[t];

                    if(transition.getTransition().size()==1 && Character.isUpperCase(transition.getTransition().get(0).toCharArray()[0])){
                        //it is control for: 1. if transition has one state. 2. if it is a non-terminal State.

                        State unitState = null;
                        for(State tempState: this.states){
                            if(tempState.getName().equals(transition.getTransition().get(0))){  // selecting the target state as unitState.
                                unitState = tempState;
                                break;
                            }
                        }

                        if(unitState!=null){
                            for(Transition unitsTransition: unitState.getTransitions()){
                                if(!hasThisTransition(state,unitsTransition.toString())){
                                    state.addTransition(unitsTransition);
                                }
                            }

                            state.removeTransition(unitState.getName());
                        }
                    }
                }
            }
        }
    }
    public boolean hasOnlyTerminatingSymbol(State state){
        boolean result = false;

        for(Transition transition: state.getTransitions()){
            boolean allTerminatingInRow = true;
            for(String ch: transition.getTransition()){
                if(Character.isUpperCase(ch.toCharArray()[0])){
                    allTerminatingInRow = false;
                    break;
                }
            }
            if(allTerminatingInRow){
                result = true;
            }
        }


        return result;
    }

    public void eliminateNonGeneratings(){
        HashMap<String,Boolean> generatings = new HashMap<String, Boolean>();

        for(State state: states){       //initializing hash map for states that directly have terminating states.
            generatings.put(state.getName(),false);
            if(hasOnlyTerminatingSymbol(state)){
                generatings.put(state.getName(),true);
            }
        }

        for(int i=0; i<states.size(); i++){     //to find terminating transition that has another generating states.
            for(State state: states){
                for(Transition transition: state.getTransitions()){
                    boolean isGenerating = true;
                    for(String ch: transition.getTransition()){
                        if(Character.isUpperCase(ch.toCharArray()[0])){
                            if(!generatings.containsKey(ch) || !generatings.get(ch)){
                                isGenerating = false;
                            }
                        }
                    }
                    if(isGenerating){
                        generatings.put(state.getName(),true);
                    }
                }
            }
        }

        for(int i=0; i<states.size(); i++){
            for(int s=0; s < states.toArray().length; s++) {
                State state = (State) states.toArray()[s];
                for (int t = 0; t < state.getTransitions().toArray().length; t++) {
                    Transition transition = (Transition) state.getTransitions().toArray()[t];
                    for(int c=0; c<transition.getTransition().size(); c++) {
                        String ch = transition.getTransition().get(c);

                        if(Character.isUpperCase(ch.toCharArray()[0])){
                            if(!generatings.containsKey(ch) || !generatings.get(ch)){
                                state.removeTransition(transition.toString());
                            }
                        }
                    }

                }
            }
        }

        for(int s=0; s < states.toArray().length; s++){     //Eliminating non-generating states.
            State state = (State) states.toArray()[s];

            if(!generatings.get(state.getName())){
                states.remove(state);
            }
        }



    }

    public void eliminateUnreachables(){
        Set<String> reachables = new LinkedHashSet<>();
        reachables.add("S");

        for(int i=0; i<states.size(); i++){     //Finding reachable states.
            for(State state: states){

                if(reachables.contains(state.getName())){
                    for(Transition transition: state.getTransitions()){
                        for(String ch: transition.getTransition()){
                            if(Character.isUpperCase(ch.toCharArray()[0])){
                                reachables.add(ch);
                            }
                        }
                    }
                }
            }
        }

        for(int s=0; s < states.toArray().length; s++){     //Eliminating unreachable states.
            State state = (State) states.toArray()[s];

            if(!reachables.contains(state.getName())){
                states.remove(state);
            }
        }

    }

    public void transformFromCFGToCNF(){
        this.eliminateEpsilons();
        this.eliminateUnitProductions();
        this.eliminateNonGeneratings();
        this.eliminateUnreachables();

        int indexState = 0;
        for(int s=0; s < states.toArray().length; s++) {
            State state = (State) states.toArray()[s];

            for (int t = 0; t < state.getTransitions().toArray().length; t++) {
                Transition transition = (Transition) state.getTransitions().toArray()[t];
                ArrayList<String> aList = new ArrayList<>();
                boolean firstOneIsTerminal = false;
                boolean firstOneIsNonTerminal = false;

                for(int c=0; c<transition.getTransition().size(); c++) {
                    String ch = transition.getTransition().get(c);

                    if(c==0){   //first state of transition
                        if(Character.isLowerCase(ch.toCharArray()[0])){       //if first state is terminal
                            if(transition.getTransition().size() > 1){      //if there is more than one state in transition
                                firstOneIsTerminal = true;
                            }
                        }else{      //if first state is non-terminal
                            if(transition.getTransition().size()>2 || Character.isLowerCase(transition.getTransition().get(1).toCharArray()[0])){
                                //transition number should be grater than 2 or second state should be terminal state.
                                firstOneIsNonTerminal=true;
                            }
                        }
                    }

                    if(c>0){
                        if(firstOneIsTerminal || firstOneIsNonTerminal){
                            aList.add(ch);
                        }
                    }

                }

                if(firstOneIsTerminal){
                    State tempState1 = new State("X"+indexState);
                    indexState++;
                    State tempState2 = new State("X"+indexState);
                    ArrayList<String> tempList = new ArrayList<>();

                    tempList.add(transition.getTransition().get(0));
                    tempState1.addTransition(tempList);

                    tempState2.addTransition(aList);
                    transition.getTransition().clear();
                    transition.getTransition().add(tempState1.getName());
                    transition.getTransition().add(tempState2.getName());

                    states.add(tempState1);
                    states.add(tempState2);
                    indexState++;

                }

                if(firstOneIsNonTerminal){
                    State tempState = new State("X"+indexState);
                    String firstNonTerminal = transition.getTransition().get(0);
                    transition.getTransition().clear();
                    transition.getTransition().add(firstNonTerminal);
                    transition.getTransition().add(tempState.getName());

                    tempState.addTransition(aList);
                    states.add(tempState);
                    indexState++;
                }

            }
        }

        for(int i=0; i<states.size(); i++){
            for(int s1=0; s1 < states.toArray().length; s1++) {     //eliminating equal states.
                State state1 = (State) states.toArray()[s1];
                for(int s2=0; s2 < states.toArray().length; s2++) {
                    State state2 = (State) states.toArray()[s2];

                    if(!state1.equals(state2)){
                        if(isEqualStates(state1,state2)){
                            for(int s=0; s < states.toArray().length; s++) {
                                State state = (State) states.toArray()[s];
                                for (int t = 0; t < state.getTransitions().toArray().length; t++) {
                                    Transition transition = (Transition) state.getTransitions().toArray()[t];
                                    for (int c = 0; c < transition.getTransition().size(); c++) {
                                        String ch = transition.getTransition().get(c);

                                        if(ch.equals(state2.getName())){
                                            transition.getTransition().set(c,state1.getName());
                                        }
                                    }
                                }
                            }

                            states.remove(state2);
                        }
                    }
                }
            }
        }


    }

    public boolean isEqualStates(State state1, State state2){
        boolean result = true;

        if(state1.getTransitions().size() == state2.getTransitions().size()){
            for(int t=0; t<state1.getTransitions().size(); t++){
                if(!state1.getTransitions().toArray()[t].toString().equals(state2.getTransitions().toArray()[t].toString())){
                    result = false;
                }
            }
        }else{
            result = false;
        }

        return result;
    }

}

