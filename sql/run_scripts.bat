@echo off
setlocal enabledelayedexpansion

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

mysql -h %database_host% -u %database_user% -p%database_pass% < delete_database.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < create_database.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < collate_UTF8.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < create_tables.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < create_functions.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < create_stored_procedures.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < create_views.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < insert_initial_data.sql
mysql -h %database_host% -u %database_user% -p%database_pass% < insert_dummy_data.sql

echo.
echo "Database reset complete."
pause
