package com.risk.services;

import com.risk.services.gameplay.RoundRobinTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({ConnectedGraphTest.class,MapValidateTest.class,MapGraphTest.class,MapEditorTest.class,
                RoundRobinTest.class,StartUpPhaseTest.class})

/**
 * TestSuite Class for all services test classes
 * 
 * @author Palash Jain
 * @author Farhan Shaheen
 *
 */
public class ServicesTestSuite {
}
