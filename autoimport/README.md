# Autoimport 

This is a Java app which allows to import categories, products and combinations to prestashop.

## Config
In `src/main/resources/config.properties` you can specify admin credentials as well as the max time to wait for successful import.

## Build
In order to build app use\
`mvn clean compile assembly:single`

## Run
`java -jar <path to generated jar> <url to admin page> <path to csv with categories> <path to csv with products> <path to csv with combinations>`