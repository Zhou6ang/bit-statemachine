/**
 * 
 */
package com.github.zhou6ang.statemachine.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.github.zhou6ang.statemachine.parsing.AnnotationParser;
import com.github.zhou6ang.statemachine.pojo.Action;
import com.github.zhou6ang.statemachine.pojo.Condition;
import com.github.zhou6ang.statemachine.pojo.State;
import com.github.zhou6ang.statemachine.pojo.Transition;

/**
 * @author ganzhou
 *
 */
public class StateEngine {
	private static final Logger logger = Logger.getLogger(StateEngine.class.getName());

	// key: "a->b", value: Transition
	private Map<String, Transition> transitionsMap = new HashMap<>();
	// key: "State.name", value: State
	private Map<String, State> stateEntityMap = new HashMap<>();
	// key: "State.name", value: List<Transition>
	private Map<String, List<Transition>> stateTransitionMap = new HashMap<>();

	/**
	 * @param ann
	 */
	public StateEngine(AnnotationParser ann) {
		List<State> states = ann.getStates();
		List<Condition> condition = ann.getConditions();

		if (condition.isEmpty()) {
			throw new RuntimeException("Not found defined @Condition.");
		}

		validateState(states);
		vaildateCondition(condition);
		validateAction(ann.getActions());
		constructStateEntityMap(states);
		setConditionToState(condition);
		setActionToState(ann.getActions());
		constructTransitionMap();

	}

	/**
	 * @param actions
	 */
	private void validateAction(List<Action> actions) {
		if (actions == null) {
			throw new RuntimeException("No valid @Action definition found.");
		}
		Map<String, Action> map = new HashMap<>();
		actions.parallelStream().forEach(e -> {
			if (StringInvalid(e.getName())) {
				throw new RuntimeException("Invalid @Action.");
			}
			if (StringInvalid(e.getFrom())) {
				throw new RuntimeException("Invalid field \"from\" in @Action(name=\"" + e.getName() + "\" ...).");
			}

			if (StringInvalid(e.getTo())) {
				throw new RuntimeException("Invalid field \"to\" in @Action(name=\"" + e.getName() + "\" ...).");
			}

			if (map.get(e.getName()) != null) {
				throw new RuntimeException("Duplicate @Action(\"" + e.getName() + "\") definition.");
			} else {
				map.put(e.getName(), e);
			}
		});
	}

	/**
	 * @param condition
	 */
	private void vaildateCondition(List<Condition> condition) {
		if (condition == null || condition.isEmpty()) {
			throw new RuntimeException("No valid @Condition definition found.");
		}
		Map<String, Condition> map = new HashMap<>();
		condition.forEach(e -> {
			if (StringInvalid(e.getName())) {
				throw new RuntimeException("Invalid @Condition.");
			}
			if (StringInvalid(e.getFrom())) {
				throw new RuntimeException("Invalid field \"from\" in @Condition(name=\"" + e.getName() + "\" ...).");
			}

			if (StringInvalid(e.getTo())) {
				throw new RuntimeException("Invalid field \"to\" in @Condition(name=\"" + e.getName() + "\" ...).");
			}

			if (map.get(e.getName()) != null) {
				throw new RuntimeException("Duplicate @State(\"" + e.getName() + "\") definition.");
			} else {
				map.put(e.getName(), e);
			}
		});
	}

	/**
	 * @param to
	 * @return
	 */
	private boolean StringInvalid(String to) {
		return to == null || to.isEmpty();
	}

	/**
	 * @param states
	 */
	private void validateState(List<State> states) {
		if (states == null || states.isEmpty()) {
			throw new RuntimeException("No valid @State definition found.");
		}
		Map<String, State> map = new HashMap<>();
		states.forEach(e -> {
			if (StringInvalid(e.getName())) {
				throw new RuntimeException("Invalid @State.");
			}
			if (map.get(e.getName()) != null) {
				throw new RuntimeException("Duplicate @State(\"" + e.getName() + "\") definition.");
			} else {
				map.put(e.getName(), e);
			}

		});
	}

	/**
	 * @param states
	 * @return
	 */
	private void constructStateEntityMap(List<State> states) {
		states.forEach(e -> {
			stateEntityMap.put(e.getName(), e);
		});
		State startState = stateEntityMap.get(States.START_STATE);
		if (startState == null) {
			State start = new State();
			start.setName(States.START_STATE);
			stateEntityMap.put(States.START_STATE, start);
		}

		State endState = stateEntityMap.get(States.END_STATE);
		if (endState == null) {
			State start = new State();
			start.setName(States.END_STATE);
			stateEntityMap.put(States.END_STATE, start);
		}
	}

	/**
	 * @param condition
	 * @param stateEntityMap
	 */
	private void setConditionToState(List<Condition> condition) {

		condition.forEach(e -> {
			String key = e.getFrom() + "->" + e.getTo();
			Transition value = transitionsMap.get(key);
			if (value != null) {
				if (value.getCondition() == null) {
					value.setCondition(e);
				} else {
					throw new RuntimeException("Duplicate switch state definition, @Condition(... from=\"" + e.getFrom()
							+ "\" to=\"" + e.getTo() + "\" ...)");
				}

			} else {
				transitionsMap.put(key, createTransition(e, stateEntityMap));
			}

		});
	}

	private Transition createTransition(Object e, Map<String, State> statesMap) {
		Transition transition = new Transition();
		String from = null;
		String to = null;

		if (e instanceof Condition) {
			Condition cond = ((Condition) e);
			from = cond.getFrom();
			to = cond.getTo();
			transition.setCondition(cond);

		} else if (e instanceof Action) {
			Action act = ((Action) e);
			from = ((Action) e).getFrom();
			to = ((Action) e).getTo();
			transition.setAction(act);
		} else {
			throw new RuntimeException("Not support type " + e);
		}
		State fromState = statesMap.get(from);
		if (fromState == null) {
			throw new RuntimeException("Should have defined a @State(\"" + from + "\")");
		}
		State toState = statesMap.get(to);
		if (toState == null) {
			throw new RuntimeException("Should have defined a @State(\"" + to + "\")");
		}

		transition.setFrom(fromState);
		transition.setTo(toState);
		return transition;
	}

	/**
	 * @param actions
	 */
	private void setActionToState(List<Action> actions) {
		actions.forEach(e -> {
			String key = e.getFrom() + "->" + e.getTo();
			Transition value = transitionsMap.get(key);
			if (value != null) {
				if (value.getAction() == null) {
					value.setAction(e);
				} else {
					throw new RuntimeException("Duplicate switch state definition, @Action(... from=\"" + e.getFrom()
							+ "\" to=\"" + e.getTo() + "\" ...)");
				}
			} else {
				transitionsMap.put(key, createTransition(e, stateEntityMap));
			}
		});
	}

	/**
	 * @throws Exception
	 * 
	 */
	public void start() throws Exception {
		State startState = stateEntityMap.get(States.START_STATE);
		if (startState != null) {
			doExecution(startState, null);
		} else {
			throw new RuntimeException("No States.START_STATE found in @Condition.");
		}
	}

	/**
	 * @param current
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void doExecution(State current, Object lastStateOutput) throws Exception {
		List<Transition> list = stateTransitionMap.get(current.getName());
		if (list == null || list.isEmpty()) {
			throw new RuntimeException("No found @Condition definition for @State(\""+current.getName()+"\")");
		}

		Transition transition = null;
		for (Transition tran : list) {
			if (isMatchedState(current, tran) && tran.getCondition().invoke(lastStateOutput)) {
				transition = tran;
				break;
			}
		}
		if (transition != null) {
			if (States.END_STATE.equalsIgnoreCase(transition.getTo().getName())) {
				logger.info("State Machine is running successfully.");
				return;
			}
			// if no output from last State, then invoke next State immediately;
			// otherwise, passing output to next State with a parameter.
			Object obj = transition.getTo().invoke(lastStateOutput);
			if (transition.getAction() != null) {
				Object actionOutput = transition.getAction().invoke(obj);
				obj = actionOutput == null ? obj : actionOutput;
			}
			doExecution(transition.getTo(), obj);
		} else {
			// TODO should switch to States.TERMINATE??
			throw new RuntimeException("No matched @Condition from @State(\""+ current.getName() + "\") to other @State.");
		}

	}

	/**
	 * @param current
	 * @param transition
	 * @return
	 */
	private boolean isMatchedState(State current, Transition transition) {
		return current == transition.getFrom();
	}

	/**
	 * @param transition
	 */
	private void constructTransitionMap() {
		transitionsMap.values().forEach(e -> {
			String key = e.getFrom().getName();
			List<Transition> list = stateTransitionMap.get(key);
			if (list != null) {
				list.add(e);
			} else {
				List<Transition> l = new ArrayList<>();
				l.add(e);
				stateTransitionMap.put(key, l);
			}

		});
	}
}
