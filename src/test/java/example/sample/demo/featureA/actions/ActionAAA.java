package example.sample.demo.featureA.actions;

import com.github.zhou6ang.statemachine.annotation.Action;
import com.github.zhou6ang.statemachine.engine.States;

public class ActionAAA {

	@Action(name="action5",from = States.START_STATE, to = "StateAAA")
	public void action(Object stateOutputObj){
		//TODO do some logic
		System.out.println("here is action5");
	}
	
}
