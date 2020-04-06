package com.sicknasty;

import com.sicknasty.acceptanceTests.AccountAccessAndUpdatesTest;
import com.sicknasty.acceptanceTests.CommunityCreationAndFollowTest;
import com.sicknasty.acceptanceTests.FollowPagesTest;
import com.sicknasty.acceptanceTests.MessageBetween2UsersTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountAccessAndUpdatesTest.class,
        CommunityCreationAndFollowTest.class,
        FollowPagesTest.class,
        MessageBetween2UsersTest.class,
})
public class AllAcceptanceTests
{

}
