:::
@echo off
setlocal

:: Maven Wrapper Script for Windows

set MAVEN_HOME=%~dp0\.mvn\wrapper
set MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=256m

if not exist "%MAVEN_HOME%\mvnw.cmd" (
    echo "Maven wrapper script not found."
    exit /b 1
)

"%MAVEN_HOME%\mvnw" %*
endlocal
:::