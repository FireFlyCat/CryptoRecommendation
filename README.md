# Crypto Recommendation

Crypto Recommendation is a service created for developers. It helps to investigate historical data of cryptocurrency movement 
by providing simple amount of analytical functions and allows to decide which currency to choose. 

## API Documentation 
[Swagger UI](http://localhost:8080/documentation)


## Install 
```
git clone https://github.com/FireFlyCat/CryptoRecommendation.git
```
## Build & Deploy locally
### Presetup

- Java 17 or later required 
- JAVA_HOME env variables setup
- port 8080 is free
### Build
in root folder run the command

Windows
```
gradlew.bat build
```

Linux
```
/.gradlew build
```
### Run the Tests

Windows
```
gradlew.bat
```

Linux
```
/.gradlew
```

### Deploy Locally
```
gradlew.bat bootRun
```

Linux
```
/.gradlew bootRun
```
### Deploy to Kubernetes 
TODO
## Notes for Reviewers
- JobExecutor is a simple imitation of Job task that will import the initial data into Records table. In future can be replaced to Spring Batch with chunks
- Metadata views are used to provide data for main API application. Can be optimized by creating plain table that will be updated with nw historical data. currently it is too much overhead
- units tests are not written because of a hurry
