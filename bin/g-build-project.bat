@echo off
@echo ------   build with gradle  ----
call set-envi.bat
@echo ------  build start  ------------------
cmd /c gradle clean build warAndWebappXml
@echo ------  build done  -------------------
@pause