#!/bin/bash

dir=target

kill $(ps aux | grep 'spring.profiles.active' | awk '{print $2}')

nohup java -Dspring.profiles.active=dev -jar $dir/api-cotations-boursieres-0.0.1-SNAPSHOT.jar &
