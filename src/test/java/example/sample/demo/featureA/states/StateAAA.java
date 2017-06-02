package example.sample.demo.featureA.states;

import com.github.zhou6ang.statemachine.annotation.State;

public class StateAAA {
	
	@State("StateAAA")
	public void run(){
		System.out.println("State AAA will do some things here.");
	}
}
