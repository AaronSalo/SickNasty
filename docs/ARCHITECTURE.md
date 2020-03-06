# Architecture
The architecture for SickNasty contains three layers: the Presentation, Business and Persistence layers.  
  

The Business layer will handle the interaction between the Presentation and Persistence layers. Those two layers will never communicate directly with each other  
Along side the three layers, we also have Domain Specific Objects (DSOs) that get passed around to each of the different layers.  
  
## Presentation Layer
This layer contains three different activities:  
1. PageActivity
    - This activity contains all the Posts that were posted to a Page and displays it
2. LoginActivity
    - This activity is the landing zone for new or returning users. Existing users will login here
3. RegistrationActivity
    - This activity is can be launched from the LoginActivity. This is where new users come to create a new account
3. UserAccountActivity
    - This activity will give the user the ability to change their personal details pertaining to their user account
4. adapter/PostAdapter
    - This adapter class provides the translation from the Post object into it's correct view object. This class figures out whether to display an image or video from the Post object
  
## Business
This layer contains three different managing classes that act as the middlemen for Presentation and Persistence:  
1. AccessPages
    - This manager class provides the link between PageActivity and PagePersistence
2. AccessPosts
    - This class provides the link between PageActivity and PostPersistence
3. AccessUsers
    - This class provides the link between the UserPersistence and all three activities  
  
## Persistence
This layer contains three persistence interfaces that save the DSOs and their information:
1. PagePersistence
    - This persistence contains two concrete implementations of this interface:
        - PagePersistenceStub that contains a fake implementation of Pages using a HashMap
        - PagePersistenceHSQLDB that contains calls to the HSQLDB driver that modifies a persistent database
2. PostPersistence
    - As with PagePersistence, there are two concrete implementations:
        - PostPersistenceStub that contains a fake implementation
        - PostPersistenceHSQLDB that contains the real calls to a persistent database
3. UserPersistence
    - Same with the previous two, there are two concrete implementations:
        - UserPersistenceStub that contains fake implementation
        - UserPersistenceHSQLDB that contains real calls to the persistent database  
  
## Diagram  
```
+------------------------------+------------------------------+------------------------------+
|                              |                              |                              |
|         PRESENTATION         |           BUSINESS           |         PERSISTENCE          |
|                              |                              |                              |
+--------------------------------------------------------------------------------------------+
|                              |                              |                              |
|                              |                         +------>PagePersistence             |
|                              |                         |    |  ++                          |
|         PageActivity+-----------+----->AccessPages+----+    |   +->PagePersistenceStub     |
|                              |  |                           |                              |
|                              |  |                           |                              |
|                              |  |                           |                              |
|        LoginActivity+------+ |  +-+--->AccessPosts+----------->PostPersistence             |
|                            | |    |                         |  ++                          |
|                            | |    |                         |   +->PostPeristenceStub      |
|                            | |    |                         |                              |
|     RegistrationActivity+--+------+--->AccessUsers+----+    |                              |
|                              |                         +------>UserPersistence             |
|                              |                              |  ++                          |
|                              |                              |   +->UserPersistenceStub     |
|                              |                              |                              |
|                              |                              |                              |
+------+-----------------------+------------------------------+------------------------------+
| DSOs |                                                                                     |
+------+       User              Page+-->CommunityPage        Post+-->VideoPost              |
|                                    |                            |                          |
|              Password              +-->PersonalPage             +-->PicturePost            |
|                                                                 |                          |
|                                                                 +-->TextPost               |
|                                                                                            |
+--------------------------------------------------------------------------------------------+
```
