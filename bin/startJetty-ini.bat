@echo off
@echo -------------  start jetty  init -------
call set-envi.bat
@echo -----------------  start  ----------------

java -jar %~dp0\jetty\start.jar --add-to-startd-http,deploy

@echo -----------------  done ----------------
@pause
