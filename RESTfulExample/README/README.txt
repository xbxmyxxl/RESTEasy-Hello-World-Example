In order for this protocol to work on  a different computer the folloeing thing should be changed.

1. Database, this protocol uses postgre sql and jdbc driver for opening and closing database, change the password and name of the table if neede
just make up some data, if no databse at hand.
See the database folder for all the script

2. Tomcat 8 is used, and cunnect to port 8080 on default, may need to change that.

3. currently the log file is written to T:/log4j-application.log, change to any position you want , however, it should begin to "T://" instead of "T:/".
If thge log4j.properties file is not loaded, please copy it under target->classes folder.

4.no matter what the GroupCommTest should work without any additional support.

5. if you change the change the project name , you need to change the path as well

6. Java 1.8 is used, if you are having a differnt version, please change the version specified in pom.xml to the verison you have