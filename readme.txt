For correct work need start in the tomcat 7 or later
also need add jstl & mysql library to tomcat lib
(for me jstl-1.2.jar,mysql-connector-java-8.0.13.jar
from .m2 repo to tomcatX/lib directory)

set mysql user and password in liquibase.properties and MySqlProvider.java
mysql> CREATE DATABASE nicofog CHARACTER SET utf8 COLLATE utf8_unicode_ci;
mvn liquibase:update

