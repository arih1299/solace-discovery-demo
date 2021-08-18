**Solace Discovery Demo**

This demo is used to build a Solace broker and few applications that publish/subscribe events. It can then be used as a target environment to run a Solace Event Portal Discovery demo against.

For Solace Event Portal Discovery, please refer to https://console.solace.cloud/event-discovery

**Run the demo env**
```
$ docker compose up
```

**Run the applications**

Enter the app directory and run
```
$ mvn spring-boot:run
```



**(Optional) Generating The Apps with AsyncAPI Generator**

The applications were generated from the spec files under AsyncAPI folder, and then modified to add the publishing parts and some specific Spring Cloud Stream consumer section to define the queues.

If you need to generate a new code, please follow the sample command below. Please refer to AsyncAPI Generator project for more details: https://github.com/asyncapi/generator


```
$ ag -o PatientMobileApp -p binder=solace -p view=client -p artifactId=PatientMobileApp -p groupId=sohealthy -p javaPackage=com.sohealthy.demo -p host=localhost:5555 -p username=default -p password=default -p msgVpn=default ./AsyncAPI/PatientMobileApp.yaml https://github.com/asyncapi/java-spring-cloud-stream-template.git
$ ag -o DoctorMobileApp -p binder=solace -p view=client -p artifactId=DoctorMobileApp -p groupId=sohealthy -p javaPackage=com.sohealthy.demo -p host=localhost:5555 -p username=default -p password=default -p msgVpn=default ./AsyncAPI/DoctorMobileApp.yaml https://github.com/asyncapi/java-spring-cloud-stream-template.git
$ ag -o PatientDataProcessor -p binder=solace -p view=client -p artifactId=PatientDataProcessor -p groupId=sohealthy -p javaPackage=com.sohealthy.demo -p host=localhost:5555 -p username=default -p password=default -p msgVpn=default ./AsyncAPI/PatientDataProcessor.yaml https://github.com/asyncapi/java-spring-cloud-stream-template.git
```

