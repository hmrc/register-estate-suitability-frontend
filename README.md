# Register estate suitability frontend

This service is responsible for determining if a user needs to register their estate based on legislation and by evaluating the estate's tax liability.

To run locally using the micro-service provided by the service manager:

***sm2 --start ESTATES_ALL***

If you want to run your local copy, then stop the frontend ran by the service manager and run your local code by using the following (port number is 8821 but is defaulted to that in build.sbt).

`sbt run`

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
