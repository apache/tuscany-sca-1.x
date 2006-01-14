wsdl2java -s -S true -o wsdl2javaOut helloworld.wsdl
java2wsdl -o helloworld.wsdl -l http://localhost:8080/axis/services/helloworld -y WRAPPED org.apache.tuscany.samples.helloworldaxis.HelloWorldServiceImpl
