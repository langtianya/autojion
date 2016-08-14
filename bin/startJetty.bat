@echo off
@echo ---------------------------run jetty -------------------------
@echo ---------------------------start------------------------
CALL set-envi.bat

set CURRENT_DIR=%~dp0

@echo CURRENT DIR:%CURRENT_DIR%

java -jar %CURRENT_DIR%\jetty\start.jar jetty.http.port-8081

@echo ---------------------------done------------------------
