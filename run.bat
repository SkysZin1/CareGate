@echo off
setlocal enabledelayedexpansion

set SOURCE_DIR=%~dp0src
set BUILD_DIR=%~dp0build
set LIB_DIR=%~dp0lib
set JAVA_OPTS=--enable-native-access=ALL-UNNAMED
set TITLE=CareGate - Console

if not exist "%LIB_DIR%\sqlite-jdbc.jar" (
    echo.
    echo [ERRO] Driver SQLite nao encontrado em lib\sqlite-jdbc.jar
    echo Adicione o sqlite-jdbc.jar na pasta lib antes de executar.
    pause
    exit /b 1
)

if not exist "%BUILD_DIR%" mkdir "%BUILD_DIR%"

echo Compilando versao console...
pushd "%SOURCE_DIR%"
javac -encoding UTF-8 -d "%BUILD_DIR%" repository\*.java service\*.java application\*.java entities\*.java interfaces\*.java
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
echo Iniciando CareGate...
echo.
title %TITLE%
java %JAVA_OPTS% -cp "%BUILD_DIR%;%LIB_DIR%\*" application.Programa

pause
