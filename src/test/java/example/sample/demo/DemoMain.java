package example.sample.demo;

import java.io.IOException;
import com.github.zhou6ang.statemachine.StateMachineApp;

public class DemoMain {

	public static void main(String[] args) throws IOException {
		StateMachineApp.registry(DemoMain.class);
	}

}
