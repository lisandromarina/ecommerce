#!/bin/bash

echo "Running Maven build..."
# Build the Maven project
mvn clean package

echo "Running docker compose..."
# Run Docker Compose
docker-compose up -d