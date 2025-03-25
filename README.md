# JobFinder
## Web application helping find job offer!

### Description
JobFinder is a Spring Boot web application that aggregates job offers for Junior Java Developers. It fetches offers from external servers on a schedule. Users register to receive a token, which they use to access or add offers. A simple React frontend enhances the presentation. Application was deployed on AWS EC2 server.

> **Author:**  Szymon Bartkowiak <br>
> **Linkedin:** https://www.linkedin.com/in/szymon-bartkowiak-7516532b4/  <br>
> **Email:** szymon.b4310@gmail.com



### Video


https://github.com/user-attachments/assets/33582e0e-583a-4832-a5fe-f5465fe1faeb





### Architecture
![image](https://github.com/user-attachments/assets/25a41acb-b898-4048-a557-80c09e6a8bdc)



### Technologies

#### Core:
- Java 21  
- Apache Maven  
- Spring Boot 3.3.6  
- MongoDB  
- Redis  
- Docker  
- React  

#### Testing:
- JUnit 5   
- Mockito   
- Testcontainers  
- AssertJ  
- WireMock   
- Awaitility   

### Endpoints

Application provides the following endpoints:  

|   ENDPOINT   | METHOD |              REQUEST              |             RESPONSE             |      FUNCTION       |
|:------------:|:------:|:---------------------------------:|:--------------------------------:|:-------------------:|
|  /register   |  POST  | JSON BODY (username and password) | JSON (username and created flag) |   register a user   |
|    /token    |  POST  | JSON BODY (username and password) |           JSON (token)           |  give user a token  |
|   /offers    |  GET   |       PATH VARIABLE (token)       |          JSON (offers)           |   sending offers    |
| /offers/{id} |  GET   |       PATH VARIABLE (token)       |           JSON (offer)           | finding offer by id |
|   /offers    |  POST  |       PATH VARIABLE (token)       |           JSON (offer)           |  creates new offer  |


### Main Page
  ![image](https://github.com/user-attachments/assets/45a4a87b-a64e-4e99-9331-1dd5517f00ca)
  
  

















