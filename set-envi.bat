set "JAVA_HOME=G:\Program Files\Java\jdk1.8.0_05"
set "CLASSPATH=%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar"

set "GRADLE_HOME=%cd%\gradle"

set "PATH=%JAVA_HOME%\bin;%GRADLE_HOME%\bin;%PATH%"

set CURRENT_DIR=%~dp0
@echo CURRENT DIR:%CURRENT_DIR%
set "JETTY_HOME=%CURRENT_DIR%\jetty"

@echo -----------JAVA_HOME ----------- : 
@echo %JAVA_HOME%