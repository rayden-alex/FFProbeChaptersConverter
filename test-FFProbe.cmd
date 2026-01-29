@echo off
chcp 65001 > nul

c:\Rip\FFMPEG\bin\ffprobe.exe -hide_banner -v error  -i ".\src\test\resources\youtube_chapters.opus" -of json -show_format -show_streams -show_chapters | java -XX:AOTCache=FFProbeChapters2Cue.aot -XX:+UseCompactObjectHeaders -jar .\build\libs\FFProbeChapters2Cue.jar

pause
