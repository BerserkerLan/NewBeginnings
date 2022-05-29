## Grail Take Home Test
Grail take home test regarding "New Beginnings" for registering participants

## Requirements
Make sure you have Java JDK 17 installed as that is what this project uses. Please also install gradle as that is what this project uses to be built. If just testing, importing into IntelliJ should allow you to download everything you need and run the project through it.

## How to run
```./gradlew clean build && ./gradlew bootRun```. If there are any JDK version errors, please ensure you have the same version of Java and JAVA_HOME is configured properly. If all else fails, please simply import this project into an IDE like IntelliJ and it should be runnable.

## Endpoints

| Endpoint                                 | Description                                                                                                                                                                                                                                                                                                 |
|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET /participant/{referenceNumber}       | GET endpoint to retrieve the details of a participant.                                                                                                                                                                                                                                                      |
| POST /enroll                             | POST endpoint to save a participants details. When saving, this will return the referenceNumber that has been used to save this participant that can be used to get, update or remove this participant.                                                                                                     |
| PUT update/participant/{referenceNumber} | PUT endpoint to update a given participant. This takes a referenceNumber as well as a body of JSON format with example request ```{{"phoneNumber" : "(123) 123-4567","address": {"streetName" : "Jane Street","zipCode" : "99501"}}```. This currently only supports phoneNumber and address modifications. |
| DELETE /remove/{referenceNumber}         | DELETE endpoint to remove a participant detail from the system using a referenceNumber.                                                                                                                                                                                                                     |

## Main Assumptions
* There is no Database in this system as there was no requirement for persistence in the Question Sheet.
* You can only update the address and phone number, as the name and date of birth would not be something to update often.
* In the assignment it is said to assume the reference number is provided by an external party. I have simply configured a service component to generate this reference number, but I would query this external third party service for the reference number instead.
* The reference number is in a similar format as the question i.e. "XXXX-YYYY" where X are alphabets from A to Z, and Y are integers from 0 to 9.
* There is 84% class test coverage in this project as well as 89% lines coverage by tests.
