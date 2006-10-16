set TC_VERSION=apache-tomcat-5.5.17
set catalina_opts=
set catalina_opts=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=3720,server=y,suspend=n
erase /s /q test\%TC_VERSION%\logs\*.*
pushd test\%TC_VERSION%\bin & start "tomcat" catalina run 2>&1 ^| tee catalina.out & popd
