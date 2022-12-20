#!/bin/bash
cd ..
#(Check the link for adding multiple sources)
#https://github.com/spring-projects/spring-boot/issues/7624
#Line with debug flag
#mvn -Dspring-boot.run.jvmArguments="-Dspring.config.location=/apps/configs/application.properties" spring-boot:run -X &
mvn -Dspring-boot.run.jvmArguments="-Dspring.config.location=/apps/configs/telegramRevenueSummary.properties" spring-boot:run  &

exit 0;

