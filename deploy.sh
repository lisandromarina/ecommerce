#!/bin/bash

echo "Running Maven build..."
# Build the Maven project
mvn clean package

# Check the exit status of the Maven build
maven_exit_status=$?
if [ $maven_exit_status -ne 0 ]; then
  echo "Maven build failed. Exiting..."
  exit $maven_exit_status
fi

echo "Running docker compose..."
# Run Docker Compose
docker-compose up -d