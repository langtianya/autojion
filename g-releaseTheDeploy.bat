@echo off
@echo -------------   build with gradle  -------
call set-envi.bat
@echo -----------------  start  ----------------
cmd /c gradle clean releaseTheDeploy
@echo -----------------  done ----------------
@pause