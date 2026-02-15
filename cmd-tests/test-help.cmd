@echo off
chcp 65001 > nul

java -XX:AOTCache=..\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar ..\build\libs\FFProbeChaptersConverter.jar -h

pause
