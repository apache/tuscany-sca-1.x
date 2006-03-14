
function getGreeting() {
    var name = document.getElementById("name").value;
    var result = SCA.HelloWorldService.getGreetings(name);
    document.getElementById("greeting").value = result;
}
