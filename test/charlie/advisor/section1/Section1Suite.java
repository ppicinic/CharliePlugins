/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor.section1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Phil Picinic
 * This test suite runs all the tests for section 1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({charlie.advisor.section1.Test00_1217_7A.class, 
    charlie.advisor.section1.Test01_1217_26.class, 
    charlie.advisor.section1.Test00_1217_26.class, 
    charlie.advisor.section1.Test01_1217_7A.class})
public class Section1Suite {

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
