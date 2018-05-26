package com.company;

public class Main {

    /*
    Burhan Burak Oral
    14070006006
    */

    public static void main(String[] args) {

        State s,a,b,c,d,e,x;

        //-------------------------------
        //Creating cfg1 for 1. Question.

        CFG cfg1 = new CFG();

        s = new State("S");
        s.addTransition(new Transition(new String[] {"A","B","A","C"}));
        a = new State("A");
        a.addTransition(new Transition(new String[] {"a","A"}));
        a.addTransition(new Transition(new String[] {"<epsilon>"}));
        b = new State("B");
        b.addTransition(new Transition(new String[] {"b","B"}));
        b.addTransition(new Transition(new String[] {"<epsilon>"}));
        c = new State("C");
        c.addTransition(new Transition(new String[] {"c"}));

        cfg1.addState(s);
        cfg1.addState(a);
        cfg1.addState(b);
        cfg1.addState(c);



        //-------------------------------
        //Creating cfg2 for 2. Question.

        s = new State("S");
        s.addTransition(new Transition(new String[] {"A","B"}));
        a = new State("A");
        a.addTransition(new Transition(new String[] {"a"}));
        b = new State("B");
        b.addTransition(new Transition(new String[] {"C"}));
        b.addTransition(new Transition(new String[] {"b"}));
        c = new State("C");
        c.addTransition(new Transition(new String[] {"D"}));
        d = new State("D");
        d.addTransition(new Transition(new String[] {"E"}));
        e = new State("E");
        e.addTransition(new Transition(new String[] {"A"}));

        CFG cfg2 = new CFG();
        cfg2.addState(s);
        cfg2.addState(a);
        cfg2.addState(b);
        cfg2.addState(c);
        cfg2.addState(d);
        cfg2.addState(e);




        //-------------------------------
        //Creating cfg3 for 3. Question.

        s = new State("S");
        s.addTransition(new Transition(new String[] {"a","B"}));
        s.addTransition(new Transition(new String[] {"b","X"}));
        a = new State("A");
        a.addTransition(new Transition(new String[] {"B","a","d"}));
        a.addTransition(new Transition(new String[] {"b","S","X"}));
        a.addTransition(new Transition(new String[] {"a"}));
        b = new State("B");
        b.addTransition(new Transition(new String[] {"a","S","B"}));
        b.addTransition(new Transition(new String[] {"b","B","X"}));
        x = new State("X");
        x.addTransition(new Transition(new String[] {"S","B","D"}));
        x.addTransition(new Transition(new String[] {"a","B","x"}));
        x.addTransition(new Transition(new String[] {"a","d"}));

        CFG cfg3 = new CFG();
        cfg3.addState(s);
        cfg3.addState(a);
        cfg3.addState(b);
        cfg3.addState(x);


        //-------------------------------
        //Creating cfg4 for 4. Question.

        s = new State("S");
        s.addTransition(new Transition(new String[] {"a","A","a"}));
        s.addTransition(new Transition(new String[] {"b","B","b"}));
        s.addTransition(new Transition(new String[] {"<epsilon>"}));
        a = new State("A");
        a.addTransition(new Transition(new String[] {"C"}));
        a.addTransition(new Transition(new String[] {"a"}));
        b = new State("B");
        b.addTransition(new Transition(new String[] {"C"}));
        b.addTransition(new Transition(new String[] {"b"}));
        c = new State("C");
        c.addTransition(new Transition(new String[] {"C","D","E"}));
        c.addTransition(new Transition(new String[] {"<epsilon>"}));
        d = new State("D");
        d.addTransition(new Transition(new String[] {"A"}));
        d.addTransition(new Transition(new String[] {"B"}));
        d.addTransition(new Transition(new String[] {"a","b"}));

        CFG cfg4 = new CFG();
        cfg4.addState(s);
        cfg4.addState(a);
        cfg4.addState(b);
        cfg4.addState(c);
        cfg4.addState(d);



        // Printing all CFGs.

        System.out.println("Question 1 Before Process");
        cfg1.printCFG();
        System.out.println();
        System.out.println("Question 1 After Process");
        cfg1.eliminateEpsilons();
        cfg1.printCFG();
        System.out.println();
        System.out.println("-------------------------");

        System.out.println("Question 2 Before Process");
        cfg2.printCFG();
        System.out.println();
        System.out.println("Question 2 After Process");
        cfg2.eliminateUnitProductions();
        cfg2.printCFG();
        System.out.println();
        System.out.println("-------------------------");

        System.out.println("Question 3 Before Process");
        cfg3.printCFG();
        System.out.println();
        System.out.println("Question 3 After Process");
        cfg3.eliminateNonGeneratings();
        cfg3.eliminateUnreachables();
        cfg3.printCFG();
        System.out.println();
        System.out.println("-------------------------");

        System.out.println("Question 4 Before Process");
        cfg4.printCFG();
        System.out.println();
        System.out.println("Question 4 After Process");
        cfg4.transformFromCFGToCNF();
        cfg4.printCFG();
        System.out.println();
        System.out.println("-------------------------");


    }
}
