package example.sample.demo;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.zhou6ang.statemachine.StateMachineApp;

public class DemoMain {

	public static void main(String[] args) throws IOException {
		
		/**
		 * If there is only 1 task, then below can be replaced with
		 * StateMachineApp.registryAndStart(DemoMain.class, args);
		 */
		
		StateMachineApp.registry(DemoMain.class);
		String inputStr = "here is input String. "+args;
		StateMachineApp.start(inputStr);
		
		//for multiple tasks.
		ExecutorService es = Executors.newFixedThreadPool(4);
		es.submit(()->{
			StateMachineApp.start(Thread.currentThread().getName());
		});
		es.submit(()->{
			StateMachineApp.start(Thread.currentThread().getName());
		});
		es.submit(()->{
			StateMachineApp.start(Thread.currentThread().getName());
		});
		es.submit(()->{
			StateMachineApp.start(Thread.currentThread().getName());
		});
		es.submit(()->{
			StateMachineApp.start(Thread.currentThread().getName());
		});
		es.submit(()->{
			StateMachineApp.start(Thread.currentThread().getName());
		});
		
		es.shutdown();
	}

}
