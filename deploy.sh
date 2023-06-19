#!/bin/bash

# Build the Maven project
mvn clean package

# Run Docker Compose
docker-compose up -d