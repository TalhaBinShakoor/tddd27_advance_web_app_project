# TDDD27_2021_API_Monitor

This is the TDDD27 (Advance Web) Course Project at Linkoping University.

**api-monitor**
A Service Poller web application that tracks a list of Rest web services (API) , and periodically checks the services’ status.

**Functional Requirements**
- User could add/update/delete Rest-web services using user interface (web application)
- Each web service has Url,name,method,status.
- User Management module , to add/delete/update application’s users.
- Dash board to  display the apis(Rest-web services) status.
-Report Module, which allows application’s users to print reports about the Api status
Using different filters (e.g by Date, Method Type,name,..etc). The Reposrt could also
Be displayed/printed as a chart (e,g Bar chart, ..etc)

**Technology  Stack**
- Front-End (UI) Reactjs with hooks or redux.
-Back-End Vert.x (java asynchronous event-driven application framework )
-ORM Hibernate-reactive
-Database Mysql
- Testing (Jest,Junit,Vert.x test,selenuim)
- CI (Jenkins or travis)

_________________________________________________________________________

**How to Strat and Build the application?** 

_Run the following commands:_

Step1: mvn package

A new file with the name of "api-monitor-1.0.0-fat.jar" will be created inside target folder.

Step2: java -jar target/api-monitor-1.0.0-fat.jar


_________________________________________________________________________
**TDDD27 mid-term screencast**
------------------------------

https://youtu.be/LXOj7fDvcKU

**Link to the application**

http://89.47.166.22:8080/


**TDDD27 Final (User Interface Explained - By Talha and Mutaz)**

https://youtu.be/SDBm9apodgs


**TDDD27 Final - Mutaz Explaining the code**

https://youtu.be/lMnoS6lJe1M


**TDDD27 Final - Talha Explaining the code**

https://drive.google.com/file/d/1mp_EM701pNPxrPs7mtXn_LnVwwYQWbBl/view?usp=sharing


