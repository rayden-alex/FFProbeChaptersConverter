@echo off
chcp 65001 > nul

type ..\src\test\resources\FFProbeChapters_1.json | java -XX:AOTCache=..\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar ..\build\libs\FFProbeChaptersConverter.jar -f CSV > 111.csv

pause
