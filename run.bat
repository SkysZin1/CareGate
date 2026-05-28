@echo off
setlocal enabledelayedexpansion

REM Define diretórios
set SOURCE_DIR=%~dp0src
set BUILD_DIR=%~dp0build
set TITLE=CareGate - Sistema de Gestao de Clinica

REM Cria pastas build se não existir
if not exist "%BUILD_DIR%\application" mkdir "%BUILD_DIR%\application"
if not exist "%BUILD_DIR%\entities" mkdir "%BUILD_DIR%\entities"

REM Limpa arquivos .class antigos
for /d %%i in ("%BUILD_DIR%\*") do del /q "%%i\*.class" 2>nul

REM Compila todos os .java da pasta source para build
echo Compilando...
cd /d "%SOURCE_DIR%"
javac -d "%BUILD_DIR%" application\*.java entities\*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao!
    pause
    exit /b 1
)

REM Executa
echo.
echo [OK] Compilacao concluida com sucesso!
echo.
echo Iniciando aplicacao...
echo.
cd /d "%BUILD_DIR%"
title %TITLE%
java -cp . application.Programa

pause

