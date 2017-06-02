package example.sample.demo.featureA.actions;

import com.github.zhou6ang.statemachine.annotation.Action;
import com.github.zhou6ang.statemachine.engine.States;

public class MultipleActions {

	@Action(from = "StateBBB", to = "StateCCC")
	public Object action1(Object stateOutputObj){
		//TODO do some logic
		System.out.println("here is action1");
		return null;
	}
	
	@Action(from = "StateCCC", to = States.END_STATE)
	public void action2(){
		//TODO do some logic
		System.out.println("here is action2");
	}
	
	@Action(from = States.START_STATE, to = "StateBBB")
	public void action3(Object stateOutputObj){
		//TODO do some logic
		System.out.println("here is action3");
	}
	
}
