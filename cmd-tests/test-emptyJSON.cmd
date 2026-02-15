@echo off
chcp 65001 > nul

echo {} | java -XX:AOTCache=..\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar ..\build\libs\FFProbeChaptersConverter.jar

if %ERRORLEVEL% NEQ 0 (
  echo ERRORLEVEL: %ERRORLEVEL%
)

pause
