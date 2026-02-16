@echo off
chcp 65001 > nul

echo Building new AOT cache ...
java -XX:AOTCacheOutput=.\build\libs\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar .\build\libs\FFProbeChaptersConverter.jar -h

echo ERRORLEVEL:%ERRORLEVEL%

pause
