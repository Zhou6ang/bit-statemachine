package example.sample.demo.featureA.conditions;

import com.github.zhou6ang.statemachine.annotation.Condition;
import com.github.zhou6ang.statemachine.engine.States;

public class ConditionDDD {

	@Condition(name="conditionD",from = "StateCCC", to = States.END_STATE)
	public boolean run(){
		System.out.println("Current condition is true. conditionD will do some condition checking here.");
		//TODO here must return boolean type, otherwise will be terminated.
		return true;
	}
}
