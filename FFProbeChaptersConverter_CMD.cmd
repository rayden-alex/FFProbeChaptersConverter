@echo off
chcp 65001 > nul

set in_file=%1
set out_file=%in_file:~0,-6%.cmd"

set "_ffprobe=c:\Rip\ffmpeg\bin\ffprobe.exe -hide_banner  -v error "

echo Creating %in_file%

%_ffprobe% -i %in_file% -of json -show_format -show_streams -show_chapters | ^
java -XX:AOTCache=.\build\libs\FFProbeChaptersConverter.aot -XX:+UseCompactObjectHeaders -jar .\build\libs\FFProbeChaptersConverter.jar  -f CMD > ^
"%out_file%"

pause
