# Forensic-Case-Notes

Prerequisites <br /><br />
Java Version - The latest version of the JDK will be required (Version 8 at least)<br />
JAR FILES<br />
The following JAR files should be added to the Case Cadet project build path.<br />
Hibernate JAR Files (http://hibernate.org/orm/downloads/)<br />
PostgreSQL JDBC Database Driver (https://jdbc.postgresql.org/download.html)
<br /><br />
Hibernate Config File< br/>
Ensure the Hibernate configuration file has the correct database name specified within the correct tags:< br/>
     <property name="connection.url">jdbc:postgresql://localhost:5432/case_cadet</property>


Hibernate Configurations
