@echo off
chcp 65001 > nul

type .\src\test\resources\FFProbeChapters_1.json | java -XX:AOTCache=FFProbeChapters2Cue.aot -XX:+UseCompactObjectHeaders -jar .\build\libs\FFProbeChapters2Cue.jar

pause
