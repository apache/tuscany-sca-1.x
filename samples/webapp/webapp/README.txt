Simple Webapp Sample
====================

This sample illustrates how to use Apache Tuscany to run a SCA composite inside a
web application. It describes how to run it on Apache Tomcat but the application
should be portable to other containers such as Jetty or commmercial servers.

Building
--------

Before building make sure you have built and installed the composite from the
simple calculator sample in standalone/calculator.

To build this sample using Maven use:
$ mvn package

This will create a standard WAR file and uses the tuscany-war-plugin to copy in
all of the Tuscany jars needed for the runtime.

Deploying
---------

If you have enabled remote deployment as described in the root README file, you
can deploy the WAR to Tomcat using the tomcat-maven-plugin:
$ mvn tomcat:deploy

Alternatively you can copy the WAR file to the webapps directory
$ cp target/sample-webapp.war ${catalina.home}/webapps/.

Running
-------
The home page for the application is
http://localhost:8080/sample-webapp/