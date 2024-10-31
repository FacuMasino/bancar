#!/bin/bash

# Para ejecutar, abrir la terminar en la ubicación que se encuentre este archivo y ejecutar:
# chmod +x run_scripts.sh
# ./run_scripts.sh

export PATH="/usr/local/mysql/bin:$PATH"

echo "Choose database host:"
echo "1- Default localhost"
echo "2- Localhost with custom password"
echo

read -p "Enter your choice (1 or 2): " choice

if [ "$choice" == "1" ]; then
    database_host="localhost"
    database_user="root"
    database_pass="root"
elif [ "$choice" == "2" ]; then
    database_host="localhost"
    database_user="root"
    read -sp "Enter database password: " database_pass
    echo
else
    echo "Invalid option. Please try again."
    exit 1
fi

echo
echo "The selected host data is: $database_host"
echo
read -p "Do you really want to overwrite the current database? (Y/N): " confirm

if [[ "$confirm" != "Y" && "$confirm" != "y" ]]; then
    echo "Cancelled by user."
    exit 0
fi

mysql -h $database_host -u $database_user -p$database_pass < delete_database.sql
mysql -h $database_host -u $database_user -p$database_pass < create_database.sql
mysql -h $database_host -u $database_user -p$database_pass < collate_UTF8.sql
mysql -h $database_host -u $database_user -p$database_pass < create_tables.sql
mysql -h $database_host -u $database_user -p$database_pass < create_functions.sql
mysql -h $database_host -u $database_user -p$database_pass < create_stored_procedures.sql
mysql -h $database_host -u $database_user -p$database_pass < create_views.sql
mysql -h $database_host -u $database_user -p$database_pass < insert_initial_data.sql
mysql -h $database_host -u $database_user -p$database_pass < insert_dummy_data.sql

echo ""
echo "Database reset complete."
