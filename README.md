# Micronaut Vs Quarkus

We are going to compare Micronaut and Quarkus frameworks by running a simple scenario and getting some telemetry data such as 'Avg time to 1st request', 'resident set size (RSS)' and 'transactions per second'. 

## Environment

JVM: Temurin 17.0.5

Native: GraalVM 22.3.1 Java 17 CE / Mandrel 22.3 Java 17

Arch: AMD 5950X (16 cores) 64Gb RAM

OS: Linux / Fedora 37 

## Requirements

- Java 17
- Docker
- python 3
- Locust

## Scenario

Given an endpoint `http://localhost:8080/postgres` retrieve the postgres version and return it as a String.

Docker will be up and running in Docker:

```
docker run --name some-postgres -e POSTGRES_PASSWORD=topsecret -e POSTGRES_USER=user -e POSTGRES_DB=mydb -p 5432:5432 -d postgres:14
```

## Scripts

* 1strequest.sh: calculate the RSS and Avg time to 1st req.
	* Generate and run a final Jar or binary
	* Run the script, for example: `./1strequest.sh "java -jar {ROOT_PROJECT_FOLDER}/build/quarkus-app/quarkus-run.jar" 30`
*  postgresIT.py: this is a performance testing script (locust script)
	* Generate and run a final Jar or binary
	* Run the script, for example: `locust  -f postgresIT.py`
	* Configure your scenario params on `http://localhost:8089`

## Results

