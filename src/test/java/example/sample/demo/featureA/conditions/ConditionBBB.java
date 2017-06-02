package example.sample.demo.featureA.conditions;

import com.github.zhou6ang.statemachine.annotation.Condition;

public class ConditionBBB {

	@Condition(name="conditionB",from = "StateAAA", to = "StateBBB")
	public boolean run(Object lastStateOutputObj){
		System.out.println("Current condition is true. conditionB will do some condition checking here.");
		//TODO here must return boolean type, otherwise will be terminated.
		return true;
	}
}
