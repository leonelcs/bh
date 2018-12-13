### Readme please

This is a dockerized version of the assessment

Make sure you have docker installed at your machine.

there is 2 folders on this project

go to account

cd account

use the wrapped version of gradle to generate the "Dockerfile"

./gradlew buildDocker

them after go to transaction folder

cd ../transaction

use the gradle wrapper again

./gradlew buildDocker

If you have any problem downloading the dependencies it can be a proxy on you machine, take a look before starting.

them go to the main folder

use the docker-compose up to run the application

The system is using the ports 8080 (accounts) 8090 (transactions) and 5432 (postgres)

to remove all your running docker containers use:

docker stop $(docker ps -a -q)

docker rm $(docker ps -a -q)

If you try to use the IDE to run the application, have in mind you need to change the postgres and the other services
address in the application.properties

The code was formated using the google style

PS: the folder k8s is related to kubernetes but I didn't have time to finish the kubernetes version and I used the docker-compose instead.
