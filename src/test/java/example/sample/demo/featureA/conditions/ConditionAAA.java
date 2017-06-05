package example.sample.demo.featureA.conditions;

import com.github.zhou6ang.statemachine.annotation.Condition;
import com.github.zhou6ang.statemachine.engine.States;

public class ConditionAAA {

	@Condition(name="conditionA",from = States.START_STATE, to = "StateAAA")
	public boolean run(Object obj){
		System.out.println("####"+obj);
		System.out.println("Current condition is true. conditionA will do some condition checking here.");
		//TODO here must return boolean type, otherwise will be terminated.
		return true;
	}
}
