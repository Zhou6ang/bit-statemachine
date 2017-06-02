package example.sample.demo.featureA.states;

import com.github.zhou6ang.statemachine.annotation.State;

public class StateBBB {
	
	@State("StateBBB")
	public void run(Object lastOutput){
		System.out.println("State BBB will do some things here.");
	}
}
