/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor.section3;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author phil
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({charlie.advisor.section3.Test00_A210_26.class, 
    charlie.advisor.section3.Test00_A210_7A.class, 
    charlie.advisor.section3.Test01_A210_26.class, 
    charlie.advisor.section3.Test01_A210_7A.class})
public class Section3Suite {

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
