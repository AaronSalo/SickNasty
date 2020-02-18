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
    - As of right now, this persistence contains one stub implementation named PagePersistenceStub
2. PostPersistence
    - As with PagePersistence, there is one implementation named PostPersistenceStub
3. UserPersistence
    - Same with the previous two, there is one implementation stub named UserPersistenceStub  
  
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
