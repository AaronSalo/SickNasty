package com.sicknasty;

import com.sicknasty.business.AccessPagesIT;
import com.sicknasty.business.AccessPostsIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author aaron salo
 * this class simply calls all tests from every test class
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessPagesIT.class,
        AccessPostsIT.class
})
public class IntegrationTests {
}
