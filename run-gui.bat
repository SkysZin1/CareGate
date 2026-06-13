@echo off
setlocal enabledelayedexpansion

set SOURCE_DIR=%~dp0src
set BUILD_DIR=%~dp0build
set TITLE=CareGate 2.0 - Interface Grafica

if not exist "%BUILD_DIR%" mkdir "%BUILD_DIR%"

echo Compilando interface grafica...
pushd "%SOURCE_DIR%"
javac -encoding UTF-8 -d "%BUILD_DIR%" application\*.java entities\*.java interfaces\*.java gui\*.java
set "JC_ERR=%ERRORLEVEL%"
popd

if %JC_ERR% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao.
    pause
    exit /b %JC_ERR%
)

echo.
echo [OK] Compilacao concluida com sucesso.
echo.
echo Iniciando CareGate 2.0...
echo.
title %TITLE%
java -cp "%BUILD_DIR%" gui.CareGateSwing

pause
