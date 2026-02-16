@echo off
chcp 65001 > nul

java -XX:AOTCache=..\build\libs\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar ..\build\libs\FFProbeChaptersConverter.jar -h

pause
