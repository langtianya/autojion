@echo off
@echo -------------   build with gradle  -------
call set-envi.bat
@echo -----------------  start  ----------------
@rem gradle clean 命令先清理build中文件，然后运行任务releaseForRun
cmd /c gradle clean packWarForRun
@echo -----------------  done ----------------
@pause