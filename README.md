# Dokkang Server

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![GithubAction Test & Build](https://github.com/de-alone/dokkang-server/actions/workflows/test-and-build.yml/badge.svg)

This is backend server of dokkang-dokkang application.

## Build

```sh
$ ./gradlew build
BUILD SUCCESSFUL in 634ms
7 actionable tasks: 7 up-to-date

$ ls app/build/libs/app.jar                 
app/build/libs/app.jar

$ java -jar app/build/libs/app.jar
Hello World!
```

## Testing

```sh
$ ./gradlew test
```

# Direct Run

```sh
$ ./gradlew run
```

# This is test

this is test
