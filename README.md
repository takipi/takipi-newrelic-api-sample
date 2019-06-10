# takipi-newrelic-api-sample
Demonstrate Takipi SDK + NewRelic Java Agent API integration to enhance NR notice errors

`./gradlew build`

Run with both agents:

`java -javaagent:<path-to-newrelic>/newrelic.jar -agentlib:TakipiAgent -Dtakipi.parallax -Dtakipi.etl -jar ./build/libs/takipi-newrelic-api-sample-0.1.jar`

Go to: `http://localhost/8080`

