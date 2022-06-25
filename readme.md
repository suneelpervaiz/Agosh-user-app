1-install docker
2-clone project
3-build project using command (make sure maven is installed)
command is :  mvn clean install -DskipTests
4-Once project is built you will see the target folder created
5-In the target folder root you will see a jar file user-0.0.1-SNAPSHOT.jar
6-Copy this jar into docker folder using following command
cp target/user-0.0.1-SNAPSHOT.jar src/main/docker
7-Now go to the folder src/main/docker
8-Run the following command
docker-compose up
9-It will take some time to build after that you will be able to see your app running on port 8080
10- type into your browser "localhost:8080" you will see default login page