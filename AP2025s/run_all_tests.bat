@echo off
setlocal enabledelayedexpansion

REM Configuration
set "SRC_DIR=src"
set "OUT_DIR=bin"
set "INPUT_DIR=input"
set "OUTPUT_BASE=output"

REM Step 1: Compile Java source code
echo Compiling Java sources...
if not exist "!OUT_DIR!" mkdir "!OUT_DIR!"
dir /B /S "!SRC_DIR!\*.java" > sources.txt
javac -d "!OUT_DIR!" @sources.txt
if errorlevel 1 (
    echo Compilation failed!
    del sources.txt
    exit /b 1
)
del sources.txt

REM Step 2: Run each test
echo Running simulations...
for %%F in ("!INPUT_DIR!\*.txt") do (
    set "file=%%~nF"
    set "input=%%F"
    set "output=!OUTPUT_BASE!\!file!"
    if not exist "!output!" mkdir "!output!"

    echo Running test case: !file!
    java -cp "!OUT_DIR!" app.App "!input!" "!output!"
)

echo All test cases completed.
