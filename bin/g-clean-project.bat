@echo off
@echo ------   clean with gradle  ----
call set-envi.bat
@echo ------  start  ------------------------
@REM /c 执行完相应的命令后自动退出命令提示符
cmd /c gradle clean
@echo ------  done  -------------------------
@pause