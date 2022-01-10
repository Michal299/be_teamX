DATABASE_NAME="BE_175132"
DATABASE_USER="BE_175132_user"
DATABASE_PASSWORD="password"
DATABASE_ROOT_PASSWORD="student"	# hasło do serwera bazodanowego
SHOP_URL="ip_address_1"	# jakie mamy adresy?
SHOP_SSL_URL="ip_address_2" 	# ?

mysql -p$DATABASE_ROOT_PASSWORD -e "CREATE DATABASE IF NOT EXISTS ${DATABASE_NAME};"
mysql -p$DATABASE_ROOT_PASSWORD -e "CREATE USER IF NOT EXISTS ${DATABASE_USER}@'%' IDENTIFIED BY '${DATABASE_PASSWORD}';"
mysql -p$DATABASE_ROOT_PASSWORD -e "GRANT ALL PRIVILEGES ON ${DATABASE_NAME}.* TO '${DATABASE_USER}'@'%';"
mysql -p$DATABASE_ROOT_PASSWORD -e "FLUSH PRIVILEGES;"
mysql -u $DATABASE_USER -p$DATABASE_PASSWORD $DATABASE_NAME < /tmp/db_dump.sql		# odzyskiwanie dumpa bazy danych
mysql -u $DATABASE_USER -p$DATABASE_PASSWORD $DATABASE_NAME -e "UPDATE ps_shop_url SET domain='${SHOP_URL}', domain_ssl='${SHOP_SSL_URL}' WHERE id_shop_url=1;"	# ustawienie url i ssl url dla sklepu
