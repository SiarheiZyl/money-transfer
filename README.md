# money-transfer

Money-transfer is a RESTful API for money transfers between accounts.

## Description of relation data model

#### Client
This table stores information about clients which use current system.

#### Account
This tables stores information about client's accounts.

#### File
This tables stores information about all transactions.
## Stack of technologies
- Java 11
- Gradle 5+
- H2 Database
- JDBC
- Javalin 3.7
- SLF4J
- Unirest and JUnit (for integration tests)

## Building the app
#### Preconditions
- Java 11
- Gradle 5+
#### Building the app
Clone this repository. Go to the 'moneytransfer' directory and run the following command:

<code>gradle jar</code>

Go to the './build/libs' directory and run the app:

<code>java -jar moneytransfer-1.0.jar</code>

After these commands application will be running at http//:localhost:7000

## Running integration tests
#### Preconditions
- money-transfer app is running at http//:localhost:7000

Open new command line window and to the 'moneytransfer' directory and run the following command:

<code>gradle test</code>