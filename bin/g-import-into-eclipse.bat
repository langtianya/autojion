@echo off
@echo ------  inport into eclipse with gradle  ----
call set-envi.bat
@echo ------  start  ------------------------
cmd /c gradle clean cleanEclipse eclipse jettyLaunchForEclipse
@echo ------  done  -------------------------
@pause