# E-Commerce Application

## Introduction

Welcome to our e-commerce application! This README provides information on setting up and running the application in different environments, as well as details about configuration and deployment.

## Prerequisites

Before you get started, make sure you have the following prerequisites installed:

- Docker
- Docker Compose

## Environment Variables

The application relies on environment variables to configure various aspects of the system. You'll need to set these variables appropriately based on whether you're running in a production or development environment.

### Production Environment (.env)

In a production environment, make sure to set the following environment variables in your `.env` file:

- `SPRING_DATASOURCE_URL`: Production database connection URL.
- `SPRING_DATASOURCE_USERNAME`: Production database username.
- `SPRING_DATASOURCE_PASSWORD`: Production database password.
- `IMAGEKIT_CREDENTIAL_URLENDPOINT`: ImageKit URL endpoint.
- `IMAGEKIT_CREDENTIAL_PRIVATEKEY`: ImageKit private key.
- `IMAGEKIT_CREDENTIAL_PUBLICKEY`: ImageKit public key.
- `SPRING_MAIL_HOST`: SMTP host for email notifications.
- `SPRING_MAIL_PORT`: SMTP port for email notifications.
- `SPRING_MAIL_USERNAME`: SMTP username for email notifications.
- `SPRING_MAIL_PASSWORD`: SMTP password for email notifications.

### Development Environment (.env.dev)

For developers, use the following environment variables in the `.env.dev` file:

- `POSTGRES_DB`: Development database name.
- `POSTGRES_USER`: Development database username.
- `POSTGRES_PASSWORD`: Development database password.
- `SPRING_DATASOURCE_URL`: Development database connection URL.
- `SPRING_DATASOURCE_USERNAME`: Development database username.
- `SPRING_DATASOURCE_PASSWORD`: Development database password.
- `IMAGEKIT_CREDENTIAL_URLENDPOINT`: ImageKit URL endpoint.
- `IMAGEKIT_CREDENTIAL_PRIVATEKEY`: ImageKit private key.
- `IMAGEKIT_CREDENTIAL_PUBLICKEY`: ImageKit public key.
- `SPRING_MAIL_HOST`: SMTP host for email notifications.
- `SPRING_MAIL_PORT`: SMTP port for email notifications.
- `SPRING_MAIL_USERNAME`: SMTP username for email notifications.
- `SPRING_MAIL_PASSWORD`: SMTP password for email notifications.

## Getting Started

To run the application in different environments, follow the commands below.

### Production Environment

To run in a production environment:

```shell
docker-compose up

### Development Environment
For development, use:

```shell
docker-compose -f docker-compose.dev.yml up

### Authors and Acknowledgments
Lisandro Marina
Acknowledgment: Mention contributors or sources of inspiration.

### License
This project is licensed under the License Lisandri.
