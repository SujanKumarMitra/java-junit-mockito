package org.example.javajunitmockito.mocking.singleton;

/**
 * 
 * Use constructor injection/ setter injection for loose coupling
 * 
 * Avoid using {@link #getInstance()} inside methods, <br>
 * because you won't be able to mock it.
 * 
 * @author skmitra
 *
 */
public class Calculator {
	private Calculator() {
	}

	private static class InstanceHolder {
		static final Calculator INSTANCE = new Calculator();
	}

	public static Calculator getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public Integer add(Integer i, Integer j) {
		return i + j;
	}
}
