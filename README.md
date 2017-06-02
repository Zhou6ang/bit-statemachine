Bit-Statemachine
=============

Last Modified: 2017-06-02

License
=======

Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements. See the NOTICE file
distributed with this work for additional information
regarding copyright ownership. The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.

Introduction
============
The purpose of bit-statemachine is about open source state machine 
framework, it provides a good and easy way for developer/user to 
implement state-machine in programming point of view. The 
bit-statemachine will load all related Classes automatically once 
you have registered a class, and it will parse annotation for each
classes. So it has good efficiency to get transitions of state-machine
via parsing annotation once it loads classes.

Installation
============
#maven dependency
```xml
<dependency>
    <groupId>com.github.zhou6ang.framework</groupId>
    <artifactId>bit-statemachine</artifactId>
    <version>1.1.0</version>
</dependency>
```

Note
=========
1. @Condition and @State are mandatory, @Action is optional.
2. Fields "name", "from" and "to" is mandatory for @Condition, default value of field "name" for @Condition is method name in class.
3. Field "value" is mandatory for @State, default value of field "value" for @State is method name in class.
4. All of method that used annotation's: parameter type should be Object type and size can 0 or 1; return type only can choose one of void and Object.

Tutorial
=========
I. creating a maven java project as below structure.

```java
example
	|-pom.xml
	|-src
		|-main
			|-java
				|-example.sample.demo
					|-DemoMain.java
				|-example.sample.demo.featureA.api
					|-FeatureFactory.java
					|-FeatureImpl.java
					|-FeatureInterface.java
					|-...
				|-example.sample.demo.featureA.states
					|-StateAAA.java
					|-StateBBB.java
					|-StateCCC.java
					|-MultipleStates.java
					|-...
				|-example.sample.demo.featureA.conditions
					|-ConditionAAA.java
					|-ConditionBBB.java
					|-ConditionCCC.java
					|-MultipleConditions.java
					|-...
				|-example.sample.demo.featureA.actions
					|-ActionAAA.java
					|-ActionBBB.java
					|-MultipleActions.java
					|-...
				|-example.sample.demo.featureA.utils
					|-FeatureUtils.java
					|-...
```
more detail please go through https://github.com/Zhou6ang/bit-statemachine/tree/master/src/test/java/example

II. registering entry class as below:

Program entry:
```java
public class DemoMain {

	public static void main(String[] args) throws IOException {
		StateMachineApp.registry(DemoMain.class);
	}

}
```

State definition:
```java
public class StateAAA {
	
	@State("StateXXX")
	public Object run(Object lastStateOutputObj){
		System.out.println("State XXX will do some things here.");
		return xxx;
	}
}
```


Multiple States definition:
```java
public class MultipleStates {

	@State("stateX")
	public void state1(Object obj){
		System.out.println("State stateX will do some things here.");
	}
	
	@State("stateY")
	public void state2(){
		System.out.println("State stateY will do some things here.");
	}
	
	@State("stateZ")
	public void state3(){
		System.out.println("State stateZ will do some things here.");
	}
}
```

Condition definition:
```java
public class ConditionBBB {

	@Condition(name="xx",from = "StateAAA", to = "StateBBB")
	public boolean run(Object lastStateOutputObj){
		System.out.println("Current condition is true. conditionB will do some condition checking here.");
		//TODO here must return boolean type, otherwise will be terminated.
		return true;
	}
}
```

Multiple Conditions definition:
```java
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
```

Action definition:
```java
public class ActionBBB {

	@Action(from = "StateAAA", to = "StateBBB")
	public Object action4(Object stateOutputObj){
		//TODO do some logic
		System.out.println("here is action4");
		return null;
	}
	
}
```

Multiple Actions definition:
```java
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
```


