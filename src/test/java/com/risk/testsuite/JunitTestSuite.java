package com.risk.testsuite;

import com.risk.model.CardTest;
import com.risk.model.DiceTest;
import com.risk.model.PlayerTest;
import com.risk.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk.services.gameplay.RoundRobinTest;



@RunWith(Suite.class)
@SuiteClasses({MapEditorTest.class,MapGraphTest.class,MapValidateTest.class,
	RoundRobinTest.class,ConnectedGraphTest.class,CardTest.class,DiceTest.class,PlayerTest.class,StartUpPhaseTest.class})

/**
 * TestSuite Class to test all test cases
 * 
 * @author Ruthvik Shandilya
 *
 */
public class JunitTestSuite {   
}
