package com.github.zhou6ang.test;

import java.io.IOException;
import java.util.Random;

import com.github.zhou6ang.statemachine.StateMachineApp;
import com.github.zhou6ang.statemachine.annotation.Action;
import com.github.zhou6ang.statemachine.annotation.Condition;
import com.github.zhou6ang.statemachine.annotation.State;
import com.github.zhou6ang.statemachine.engine.States;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp() throws IOException
    {
		StateMachineApp.registry(Mock.class);
		StateMachineApp.start();
    }
    
    public void testAppWithParameter() throws IOException
    {
		StateMachineApp.registry(Mock.class);
		StateMachineApp.start("can passing some input here.");
    }
    
    public void testAppWithregistryAndStart() throws IOException
    {
		StateMachineApp.registryAndStart(Mock.class);
    }
    
    public void testAppWithregistryAndStartParameter() throws IOException
    {
		StateMachineApp.registryAndStart(Mock.class,"input something here");
    }
    
}
class Mock {
	
	@State(MyConstants.STATE_A)
	public Object testingState1(Object inputObj){
		System.out.println("state #####"+inputObj);
		int a = new Random().nextInt(100);
		System.out.println("state-a: current data:"+a);
		return a;
	}
	@State(MyConstants.STATE_B)
	public Object testingState2(Object obj){
		int a = (int)obj + 1;
		System.out.println("state-b: current data:"+a);
		return a;
	}
	
	@State(MyConstants.STATE_C)
	public Object testingState3(Object obj){
		int a = (int)obj + 1;
		System.out.println("state-c: current data:"+a);
		return a;
	}
	
	@State(MyConstants.STATE_D)
	public Object testingState4(Object obj){
		int a = (int)obj + 1;
		System.out.println("state-d: current data:"+a);
		return a;
	}
	
	@Condition(name="cond1",from = States.START_STATE, to = MyConstants.STATE_A)
	public Boolean testingCondition1(Object inputObj){
		System.out.println("condition #####"+inputObj);
		boolean check = true;
		System.out.println(States.START_STATE+"--->a,condition:"+check);
		return check;
	}
	
	@Condition(name="cond2",from = MyConstants.STATE_A, to = MyConstants.STATE_B)
	public Boolean testingCondition2(Object obj){
		boolean check = ((int)obj%3 == 0);
		System.out.println("a--->b,condition:"+check);
		return check;
	}
	
	@Condition(name="cond3",from = MyConstants.STATE_B, to = MyConstants.STATE_C)
	public Boolean testingCondition3(){
		boolean check = true;
		System.out.println("b--->c,condition:"+check);
		return check;
	}
	
	@Condition(name="cond4",from = MyConstants.STATE_C, to = MyConstants.STATE_D)
	public boolean testingCondition4(){
		boolean check = true;
		System.out.println("c--->d,condition:"+check);
		return check;
	}
	
	@Condition(name="cond5",from = MyConstants.STATE_D, to = States.END_STATE)
	public boolean testingCondition5(Object obj){
		
		boolean check = true;
		System.out.println("d--->"+States.END_STATE+",condition:"+check);
		System.out.println("final count:"+(int)obj);
		System.out.println("ending state machine.");
		return check;
	}
	
	@Condition(name="cond6",from = MyConstants.STATE_A, to = MyConstants.STATE_C)
	public boolean testingCondition6(Object obj){
		boolean check = ((int)obj%3 == 1);
		System.out.println("a--->c,condition:"+check);
		return check;
	}
	
	@Condition(name="cond7",from = MyConstants.STATE_A, to = MyConstants.STATE_D)
	public boolean testingCondition7(Object obj){
		boolean check = ((int)obj%3 == 2);
		System.out.println("a--->d,condition:"+check);
		return check;
	}
	
	@Action(name="act1",from = MyConstants.STATE_A, to = MyConstants.STATE_B)
	public void testingAction1(){
		System.out.println("testingAction1");
	}
	
	@Action(name="act2",from = MyConstants.STATE_C, to = MyConstants.STATE_D)
	public void testingAction2(Object obj){
		System.out.println("count:"+(int)obj);
	}
}
