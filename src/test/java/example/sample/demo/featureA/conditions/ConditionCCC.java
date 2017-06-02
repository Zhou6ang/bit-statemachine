package example.sample.demo.featureA.conditions;

import com.github.zhou6ang.statemachine.annotation.Condition;

public class ConditionCCC {

	@Condition(name="conditionC",from = "StateBBB", to = "StateCCC")
	public boolean run(Object lastStateOutputObj){
		System.out.println("Current condition is true. conditionC will do some condition checking here.");
		//TODO here must return boolean type, otherwise will be terminated.
		return true;
	}
}
