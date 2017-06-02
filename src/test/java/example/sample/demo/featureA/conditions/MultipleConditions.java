package example.sample.demo.featureA.conditions;

import com.github.zhou6ang.statemachine.annotation.Condition;
import com.github.zhou6ang.statemachine.engine.States;

public class MultipleConditions {

	@Condition(name = "conditionE", from = States.START_STATE, to = "StateBBB")
	public boolean condition1() {
		System.out.println("Current condition is false. If this condition is true, then switching over to StateBBB from START_STATE.");
		// TODO here must return boolean type, otherwise will be terminated.
		return false;
	}

	@Condition(name = "conditionF", from = "StateBBB", to = States.END_STATE)
	public boolean condition2(Object lastStateOutputObj) {
		System.out.println("Current condition is false. If this condition is true, then switching over to END_STATE from StateBBB.");
		// TODO here must return boolean type, otherwise will be terminated.
		return false;
	}
	
	@Condition(name = "conditionG", from = States.START_STATE, to = "StateCCC")
	public boolean condition3(Object lastStateOutputObj) {
		System.out.println("Current condition is false. If this condition is true, then switching over to StateCCC from START_STATE.");
		// TODO here must return boolean type, otherwise will be terminated.
		return false;
	}
	
	@Condition(name = "conditionH", from = "StateAAA", to = "StateCCC")
	public boolean condition4() {
		System.out.println("Current condition is false. If this condition is true, then switching over to StateCCC from StateAAA.");
		// TODO here must return boolean type, otherwise will be terminated.
		return false;
	}
}
