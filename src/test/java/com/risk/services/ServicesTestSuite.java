package com.risk.services;

import com.risk.services.gameplay.FortificationPhaseTest;
import com.risk.services.gameplay.ReinforcementPhaseTest;
import com.risk.services.gameplay.RoundRobinTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({ConnectedGraphTest.class,MapValidateTest.class,MapGraphTest.class,MapEditorTest.class,
        FortificationPhaseTest.class,ReinforcementPhaseTest.class,RoundRobinTest.class,StartUpPhaseTest.class})

public class ServicesTestSuite {
}
