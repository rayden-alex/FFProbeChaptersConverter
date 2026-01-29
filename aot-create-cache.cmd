@echo off
chcp 65001 > nul

echo Building new AOT cache ...
echo {} | java -XX:AOTCacheOutput=FFProbeChapters2Cue.aot -XX:+UseCompactObjectHeaders -jar .\build\libs\FFProbeChapters2Cue.jar

echo ERRORLEVEL:%ERRORLEVEL%

pause
