@rem 返回上级目录
cd ..
set "JAVA_HOME=G:\Program Files\Java\jdk1.8.0_05"
set "CLASSPATH=%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar"

@rem 如果在当前目录%~dp0与%cd%\想等，区别：cd是跟随cd进的目录的
set CURRENT_DIR=%~dp0

@rem 如果出现'Could not find the main class: org.gradle.launcher.GradleMain.'错误，证明GRADLE_HOME设置不正确
set "GRADLE_HOME=%CURRENT_DIR%gradle"

set "PATH=%JAVA_HOME%\bin;%GRADLE_HOME%\bin;%PATH%"

set "JETTY_HOME=%CURRENT_DIR%jetty"

@echo -----------JAVA_HOME ----------- : 
@echo %JAVA_HOME%
@echo -----------CURRENT_DIR ----------- : 
@echo %cd%
@rem  pause
