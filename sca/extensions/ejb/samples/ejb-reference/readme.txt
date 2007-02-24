Tuscany - EJB Reference Binding Sample

Overview:
This is a Stateless Session Bean Reference binding sample. This sample demonstrates how SCA composite can invoke Stateless Session Bean. 
In this sample Stateless Session Bean is running on Geronimo application server.
 
Location:
This sample is located under samples\sca\ejb-reference

Jar can be found under target sub-directory of the samples/sca/ejb-reference project, if you have downloaded the source and have built Tuscany 
Java SCA.  This is also found under local Maven repository under org/apache/tuscany/samples/sca/ejb-reference 

Setup:
Setup involves two steps. 
	1) Install Geronimo and deploy Stateless EJB sample application 
	2) Setup and run EJB reference binding sample in J2SE env. This readme outlines steps to run the sample inside Eclipse.

Install Geronimo and deploy EJB sample application  
	1) Download and install Geronimo application server
	2) Go to http://cwiki.apache.org/GMOxDOC11/ejb-sample-application.html#EJBsampleapplication-overview
	3) Follow all steps under sections
		a) "Configuring, Building and Deploying the Sample Application
		b)  Make sure EJB is deployed and running correctly by following "Testing of the Sample Application" section.
		     Above steps will install sample bank EJB application on Geronimo. 

Setup for running EJB reference binding sample
	1) Run mvn -Peclipse eclipse:eclipse at the top level to create eclipse projects for all Tuscany projects.
	2) Import all projects into eclipse. 
	3) Select sample-ejb-reference project from list of  projects.   
	4) Click on menu Run --> Run ... Run dialog box will be open.
	5) Create a new configuration for junit test case. Make sure the class is account.AccountTestCase and project is sample-ejb-reference
	5) Click on Arguments tab and enter VM arguments as below
		-Dmanaged=false -Djava.naming.factory.initial=org.openejb.client.RemoteInitialContextFactory 
		-Djava.naming.provider.url=localhost:4201 -Djava.naming.security.principal=system -Djava.naming.security.credentials=manager
	6) Click on Classpath tab 
	7) Add below jar under "Bootstrap Entries"
		%GERONIMO_HOME%\repository\geronimo\geronimo-security\1.1.1\geronimo-security-1.1.1.jar
		(GERONIMO_HOME is where Geronimo is installed.)
	8) Add below jars under "User entries"
		a) %GERONIMO_HOME%\lib\geronimo-kernel-1.1.1.jar
		b) %GERONIMO_HOME%\lib\geronimo-common-1.1.1.jar
		c) %GERONIMO_HOME%\repository\openejb\openejb-core\2.1.1\openejb-core-2.1.1.jar
		d) %GERONIMO_HOME%\repository\samples\Bank\1.0\Bank-1.0.car\BankEJB.jar. Or extract just home (BankManagerFacadeHome) and bean
		   interface (BankmanagerFacade) to include it the  classpath.  If the Reference interface in SCDL, is same as EJB interface, including EJB interface
 		   is not needed. Including home class in the caller’s (composite with reference binding) classpath is needed if EJB is running Geronimo (openEJB).  
		   This step (step d) ) is not needed if EJB is running  on application servers like WebSphere.
	9) Click on "Run"

Result:
Composite invokes external Bank EJB which is running on Geronimo and prints out the current bank balance on the console.  
Message printed on console:
	" In component implementation. Invoking external EJB through EJB reference binding  
	  Balance amount for account 1234567890 is $xxxx.xx"

  
 

 
