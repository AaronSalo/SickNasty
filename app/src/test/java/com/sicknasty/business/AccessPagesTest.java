package com.sicknasty.business;

import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.User;
import static org.junit.Assert.*;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AccessPagesTest {

    AccessPages pages = new AccessPages();

    @Test
    public void testInsertPage(){
        try {
            User jay = new User("Jay K", "jay", "abcmmdef");

            PersonalPage page = new PersonalPage(jay);

            assertTrue("page not added", pages.insertNewPage(page));
            assertEquals("username is different", pages.getPage("jay").getPageName(), "jay");

            assertNotNull("", pages.getPage("jay"));

            page = new PersonalPage(jay);
            assertFalse("two pages with same username added", pages.insertNewPage(page));
            assertTrue("object exist but not deleted", pages.deletePage("jay"));

            assertFalse("object not found but deleted", pages.deletePage("jay"));

            assertFalse("object not found but deleted", pages.deletePage("aaron"));
        } catch (Exception e ){
            System.out.println(e.getMessage());
            fail();
        }
    }

    //this test is 100% useless. WILL DELETE
    @Test
    public void testNullPages(){
        PersonalPage page =null;

        try {
            assertFalse("page not added", pages.insertNewPage(page));
            assertEquals("page does not exist", pages.getPage("jay"), null);

            User user = null;
            PersonalPage page1 = new PersonalPage(user);

            assertFalse("null user's page created and added", pages.insertNewPage(page1));

            assertEquals("null user's page created and added", pages.getPage(page1.getPageName()), null);

            assertFalse("object exist but not deleted", pages.deletePage(page1.getPageName()));
        }catch (Exception e){
            fail();
        }
    }
}
