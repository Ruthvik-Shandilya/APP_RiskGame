package com.risk.testsuite;

import com.risk.model.CardTest;
import com.risk.model.DiceTest;
import com.risk.model.PlayerTest;
import com.risk.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk.services.gameplay.RoundRobinTest;
import com.risk.services.saveload.ResourceManagerTest;
import com.risk.strategy.BenevolentTest;
import com.risk.strategy.HumanTest;



@RunWith(Suite.class)
@SuiteClasses({MapEditorTest.class,MapGraphTest.class,MapValidateTest.class,ResourceManagerTest.class,
	RoundRobinTest.class,BenevolentTest.class,HumanTest.class,ConnectedGraphTest.class,CardTest.class,DiceTest.class,PlayerTest.class,StartUpPhaseTest.class})

/**
 * TestSuite Class to test all test cases
 * 
 * @author Ruthvik Shandilya
 *
 */
public class JunitTestSuite {   
}
