@echo off
setlocal enabledelayedexpansion

REM Directorio base de instalación de MySQL
set "mysqlDir=C:\Program Files\MySQL"

REM Variable para guardar la ruta de MySQL encontrada
set "mysqlBin="

REM Busca carpetas que empiecen con "MySQL Server" y luego obtiene la más reciente
for /d %%D in ("%mysqlDir%\MySQL Server *") do (
    set "mysqlBin=%%D\bin"
)

REM Si se encontró una carpeta, agregarla al PATH
if defined mysqlBin (
    echo Ruta de MySQL encontrada: %mysqlBin%
    set "PATH=%mysqlBin%;%PATH%"
) else (
    echo No se encontró ninguna instalación de MySQL.
)

:menu
echo Choose database host:
echo.
echo 1- Default localhost
echo 2- Localhost with custom password
echo.

set /p choice=Enter your choice (1 or 2): 

if "%choice%"=="1" (
    set database_host="localhost"
    set database_user="root"
    set database_pass="root"
) else if "%choice%"=="2" (
    set database_host="localhost"
    set database_user="root"
    set /p database_pass=Enter database password: 
) else (
    echo Invalid option. Please try again.
    goto menu
)

cls
echo.
echo The selected host data is: %database_host%
echo.
echo Do you really want to overwrite the current database?
echo.
set /p confirm=Enter your choice (Y/N):

if /i "%confirm%" NEQ "Y" (
    echo Cancelled by user.
    pause
    goto menu
)

mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < delete_database.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < create_database.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < collate_UTF8.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < create_tables.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < create_functions.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < create_stored_procedures.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < create_views.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < insert_initial_data.sql
mysql -h %database_host% -u %database_user% -p%database_pass% --default-character-set=utf8mb4 < insert_dummy_data.sql

echo.
echo "Database reset complete."
pause
