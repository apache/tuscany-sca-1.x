SCA Samples for Apache Tuscany
==============================

This distribution contains source code for samples that illustrate how to develop
build and run SCA applications using Apache Tuscany 1.0-incubator-M2.

The samples are basically divided into three categories:
* standalone containing simple samples that can be run from the command line
* webapp containing simple samples intended to be deployed to a web container such as Tomcat
* applications containing more complex sample applications

All samples are intended to be built using Maven 2.0.4. For detailed instructions on
building and running individual samples please refer to the README file in each directory.

Configuring Apache Tomcat
-------------------------
All of the webapp samples are self-contained and do not require any libraries to be
installed in the Tomcat server directories.

The webapp samples can be deployed using the tomcat-maven-plugin described here:
http://mojo.codehaus.org/tomcat-maven-plugin/introduction.html

This plugin accesses Tomcat using the Manager application and requires a suitably configured
role to be set up. The plugin assumes a default username "admin" with no password (so this
should not be enabled on a production or visible server). To configure Tomcat to allow this
the following line should be added to the user's file ${catalina.home}/conf/tomcat-users.xml

  <user username="admin" password="" roles="manager"/>

