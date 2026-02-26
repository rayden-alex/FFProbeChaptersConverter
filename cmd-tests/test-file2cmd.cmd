@echo off
chcp 65001 > nul

java -XX:AOTCache=..\build\libs\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar ..\build\libs\FFProbeChaptersConverter.jar -f CMD -i ..\src\test\resources\FFProbeChapters_1.json --out-file .\FFProbeChapters_1.cmd

pause
