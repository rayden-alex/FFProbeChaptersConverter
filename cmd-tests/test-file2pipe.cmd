@echo off
chcp 65001 > nul

type ..\src\test\resources\FFProbeChapters_1.json | java -XX:AOTCache=..\build\libs\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar ..\build\libs\FFProbeChaptersConverter.jar

pause
