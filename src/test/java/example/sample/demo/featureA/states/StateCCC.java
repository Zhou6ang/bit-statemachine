package example.sample.demo.featureA.states;

import com.github.zhou6ang.statemachine.annotation.State;

public class StateCCC {
	
	@State("StateCCC")
	public Object run(Object lastOutput){
		System.out.println("State CCC will do some things here.");
		return "return output something like 'javaBean,String,Map,Collection,etc.";
	}
}
