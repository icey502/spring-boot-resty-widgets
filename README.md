# spring-boot-resty-widgets
A bootstrap project demonstrating spring boot, spring security with JWT, integration testing with Rest assured, and an example persisted widget implementation

# Running #

## Services

### Docker
To run the docker services:
```
cd ${project-root-dir}
docker-compose up
```

### Run the spring-boot service
From intellij you can run the services by opening up the gradle tab (on the right side of the screen),
right clicking on Tasks -> application -> run and selecting run (or debug).

## Intellij Configuration ##

Install the Lombok Plugin and
[enable annotation processing](https://www.jetbrains.com/idea/help/configuring-annotation-processing.html)
in intellij preferences.
