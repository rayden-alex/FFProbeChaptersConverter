@echo off
chcp 65001 > nul

set in_file=%1
set out_cue_file=%in_file:~0,-6%.cue"

set "_ffprobe=c:\Rip\ffmpeg\bin\ffprobe.exe -hide_banner  -v error "

echo Creating %in_file%

%_ffprobe% -i %in_file% -of json -show_format -show_streams -show_chapters | java -XX:AOTCache=FFProbeChapters2Cue.aot -XX:+UseCompactObjectHeaders -jar .\build\libs\FFProbeChapters2Cue.jar > %out_cue_file%

timeout /t 5
