package example.sample.demo.featureA.states;

import com.github.zhou6ang.statemachine.annotation.State;

public class StateAAA {
	
	@State("StateAAA")
	public void run(Object inputObj){
		System.out.println("From CLI param: "+inputObj);
		System.out.println("State AAA will do some things here.");
	}
}
