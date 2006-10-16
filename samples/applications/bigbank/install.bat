rmdir test /s /q
mkdir test
set TC_VERSION=apache-tomcat-5.5.17
rem unzip apache-tomcat-5.5.17.zip -d test
pushd test
jar -xvf ..\%TC_VERSION%.zip 
popd
copy /y server.xml test\%TC_VERSION%\conf
copy account\target\sample-bigbank-account-1.0-incubator-M2-SNAPSHOT.war test\%TC_VERSION%\webapps
copy webclient\target\sample-bigbank-webclient-1.0-incubator-M2-SNAPSHOT.war test\%TC_VERSION%\webapps


rem rmdir /s /q  test\apache-tomcat-5.5.17\webapps\jsp-examples
rem rmdir /s /q  test\apache-tomcat-5.5.17\webapps\servlets-examples
rem rmdir /s /q  test\apache-tomcat-5.5.17\webapps\tomcat-docs
rem rmdir /s /q  test\apache-tomcat-5.5.17\webapps\webdav
