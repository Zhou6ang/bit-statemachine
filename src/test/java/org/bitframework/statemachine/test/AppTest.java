package org.bitframework.statemachine.test;

import java.io.IOException;
import java.util.Random;

import org.bitframework.statemachine.StateMachineApp;
import org.bitframework.statemachine.annotation.Action;
import org.bitframework.statemachine.annotation.Condition;
import org.bitframework.statemachine.annotation.State;
import org.bitframework.statemachine.engine.States;

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

    /**
     * Rigourous Test :-)
     * @throws ClassNotFoundException 
     */
    public void testApp() throws ClassNotFoundException
    {
        try {
			StateMachineApp.registry(Mock.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
class Mock {
	
	@State("a")
	public Object testingState1(){
		int a = new Random().nextInt(100);
		System.out.println("state-a: current data:"+a);
		return a;
	}
	@State("b")
	public Object testingState2(Object obj){
		int a = (int)obj + 1;
		System.out.println("state-b: current data:"+a);
		return a;
	}
	
	@State("c")
	public Object testingState3(Object obj){
		int a = (int)obj + 1;
		System.out.println("state-c: current data:"+a);
		return a;
	}
	
	@State("d")
	public Object testingState4(Object obj){
		int a = (int)obj + 1;
		System.out.println("state-d: current data:"+a);
		return a;
	}
	
	@Condition(name="cond1",from = States.START_STATE, to = "a")
	public Boolean testingCondition1(){
		boolean check = true;
		System.out.println(States.START_STATE+"--->a,condition:"+check);
		return check;
	}
	
	@Condition(name="cond2",from = "a", to = "b")
	public Boolean testingCondition2(Object obj){
		boolean check = ((int)obj%3 == 0);
		System.out.println("a--->b,condition:"+check);
		return check;
	}
	
	@Condition(name="cond3",from = "b", to = "c")
	public Boolean testingCondition3(){
		boolean check = true;
		System.out.println("b--->c,condition:"+check);
		return check;
	}
	
	@Condition(name="cond4",from = "c", to = "d")
	public boolean testingCondition4(){
		boolean check = true;
		System.out.println("c--->d,condition:"+check);
		return check;
	}
	
	@Condition(name="cond5",from = "d", to = States.END_STATE)
	public boolean testingCondition5(Object obj){
		
		boolean check = true;
		System.out.println("d--->"+States.END_STATE+",condition:"+check);
		System.out.println("final count:"+(int)obj);
		System.out.println("ending state machine.");
		return check;
	}
	
	@Condition(name="cond6",from = "a", to = "c")
	public boolean testingCondition6(Object obj){
		boolean check = ((int)obj%3 == 1);
		System.out.println("a--->c,condition:"+check);
		return check;
	}
	
	@Condition(name="cond7",from = "a", to = "d")
	public boolean testingCondition7(Object obj){
		boolean check = ((int)obj%3 == 2);
		System.out.println("a--->d,condition:"+check);
		return check;
	}
	
	@Action(name="act1",from = "a", to = "b")
	public void testingAction1(){
		System.out.println("testingAction1");
	}
	
	@Action(name="act2",from = "c", to = "d")
	public void testingAction2(Object obj){
		System.out.println("count:"+(int)obj);
	}
}
