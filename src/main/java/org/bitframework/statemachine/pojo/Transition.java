/**
 * 
 */
package org.bitframework.statemachine.pojo;

/**
 * @author ganzhou
 *
 */
public class Transition {

	private State from;
	private State to;
	private Condition condition;
	private Action action;
	public State getFrom() {
		return from;
	}
	public void setFrom(State from) {
		this.from = from;
	}
	public State getTo() {
		return to;
	}
	public void setTo(State to) {
		this.to = to;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condtion) {
		this.condition = condtion;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
}
