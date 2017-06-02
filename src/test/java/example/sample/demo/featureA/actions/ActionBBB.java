package example.sample.demo.featureA.actions;

import com.github.zhou6ang.statemachine.annotation.Action;

public class ActionBBB {

	@Action(from = "StateAAA", to = "StateBBB")
	public Object action4(Object stateOutputObj){
		//TODO do some logic
		System.out.println("here is action4");
		return null;
	}
	
}
