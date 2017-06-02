package example.sample.demo.featureA.states;

import com.github.zhou6ang.statemachine.annotation.State;

public class MultipleStates {

	@State("stateX")
	public void state1(Object obj){
		System.out.println("State stateX will do some things here.");
	}
	
	@State("stateY")
	public void state2(){
		System.out.println("State stateY will do some things here.");
	}
	
	@State("stateZ")
	public void state3(){
		System.out.println("State stateZ will do some things here.");
	}
}
