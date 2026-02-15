@echo off
chcp 65001 > nul

java -XX:AOTCache=..\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar ..\build\libs\FFProbeChaptersConverter.jar -i ..\src\test\resources\FFProbeChapters_1.json --out-file .\FFProbeChapters_1.cue

pause
