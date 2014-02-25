/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import charlie.advisor.section1.Section1Suite;
import charlie.advisor.section2.Section2Suite;
import charlie.advisor.section3.Section3Suite;
import charlie.advisor.section4.Section4Suite;
/**
 *
 * @author Phil Picinic
 * This Test suite runs the four test suites for each card section
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({Section1Suite.class, Section2Suite.class, 
    Section3Suite.class, Section4Suite.class})
public class BasicStrategyTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
