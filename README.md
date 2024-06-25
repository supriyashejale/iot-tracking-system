## iot-tracking-system

Used a Java 17, spring boot 3, maven 3 application of an Iot tracking system

Application has below RestApi’s which are:-

**Get ** -
*URL Path http://localhost:8081/iot/event/v1?productId=WG11155638&tstmp=1582605077000
*Input- productId, timestamp 


**Post ** -
*URL Path http://localhost:8081/iot/event/v1
*Input: File path  


**Compile and Run **
* 1. Clone the Repository:
  Open a terminal or command prompt.
  Navigate to the directory where you want to store the project.
  Use the git clone command to clone the application repository.
  Replace <git_url> with the actual URL of the repository you want to clone:

  git clone <git_url>


* 2. Navigate to the Project Directory:
     Use the cd command to change directory to the cloned project location:

cd <project_name>

* 3. Build the Application
   mvn clean package

* 4.Run the Application:
  java -jar iottrackingsystem-0.0.1-SNAPSHOT.jar


**Test **
Use postman collection or swagger file
