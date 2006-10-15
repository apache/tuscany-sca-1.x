Simple Calculator Sample
========================

This sample illustrates the use of SCA to wire components together inside a composite.
All connections between the components are local and are defined using Java interfaces.

Building
--------

To build and install the sample using Maven use:
$ mvn install

This will build the sample, package a JAR file for the composite and install it in your
local maven repository for use by other samples.

Running
-------

To unpack the distribution to run the sample use:
$ mvn dependency:unpack

The 1.0-incubator-M2 distribution will be unpacked to the target/distribution directory.

You can then run the sample using the launcher:
$ java -jar target/distribution/bin/launcher.jar target/sample-calculator.jar

Modifying
---------

The Java source code for the calculator is in the src/main/java directory.
The XML for the SCA composite is in src/main/resouces/META-INF/sca/default.scdl