
**WEATHER FORECAST API**
-
This project is part of the job application process for **FINLEAP**.

**Main Objectives For This Project:**
-
- Developing an endpoint which communicate with Weather Api
- Api should return next three day weather forecast.
- Information which api provides must contains daily and nightly average of temperatures.
- In addition this, api must provide daily average of pressure value along with temperatures averages.

**Development Notes:**
- Weather api is a free api
- Due to project specifications **5 day weather forecast** used for this project.
- Project consists of two modules:
    - Server Module
    - Api Module
- Main reason behind the separation of codes into two modules is abstraction of responsibility of the codes.
- Server module only responsible for providing next three days averages. For it uses Api module, does not know how api module reach and manipulate the data.
- In the future instead of using Weather Api, we can choose another provider.It is enough to change api implementation in Api Module.
- Api module is responsible for calling weather api as we mentioned above and it maps response into object which will be use later for manipulation.
  
**Improvement Notes:**
- Cache can be added for already requested cities.
- Documentation can be added(such as swagger) and provide versioning ability
- More tests can be added for extreme cases.
- Nothing was particularly hard in development process. But it took so much time which i expected because of the configurational problems of the development IDE.
- Annotations, component scans, separated modules can cause confusion in IDE.  


