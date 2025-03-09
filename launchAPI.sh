#!/bin/bash

dir=target

java -Dspring.profiles.active=dev -jar $dir/api-cotations-boursieres-0.0.1-SNAPSHOT.jar
