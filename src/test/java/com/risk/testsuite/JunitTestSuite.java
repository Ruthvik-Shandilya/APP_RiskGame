package com.risk.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk.services.ConnectedGraphTest;
import com.risk.services.MapEditorTest;
import com.risk.services.MapGraphTest;
import com.risk.services.MapValidateTest;
import com.risk.services.gameplay.FortificationPhaseTest;
import com.risk.services.gameplay.ReinforcementPhaseTest;
import com.risk.services.gameplay.RoundRobinTest;



@RunWith(Suite.class)
@SuiteClasses({MapEditorTest.class,MapGraphTest.class,MapValidateTest.class,
	FortificationPhaseTest.class,ReinforcementPhaseTest.class,
	RoundRobinTest.class,ConnectedGraphTest.class})

/**
 * TestSuite Class to test all test cases
 * 
 * @author Ruthvik Shandilya
 *
 */
public class JunitTestSuite {   
}
