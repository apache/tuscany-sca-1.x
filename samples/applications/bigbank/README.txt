BigBank Sample Application
==========================

The BigBank sample application shows multiple SCA composites cooperating to provide a
single application. There are two modules: webclient provides the front-end user
interface and account provides the back-end business services. These are deployed
as separate web applications.

Building
--------

This sample can be built from the bigbank directory using Maven 2.0.4:
$ mvn package

Maven will recurse into and build the two sub-modules: account and webclient

Deploying
---------

If you have enabled remote deployment as described in the root README file, you
can deploy the WARs to Tomcat using the tomcat-maven-plugin:
$ mvn tomcat:deploy

Alternatively you can copy the WAR files to the webapps directory
$ cp account/target/sample-bigbank-account.war ${catalina.home}/webapps/.
$ cp webclient/target/sample-bigbank-webclient.war ${catalina.home}/webapps/.

Running
-------
The home page for the application is
http://localhost:8080/sample-bigbank-webclient/
