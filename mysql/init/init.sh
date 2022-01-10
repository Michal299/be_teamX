mysql -pstudent -e "CREATE DATABASE IF NOT EXISTS BE_175132;"
mysql -pstudent -e "CREATE USER IF NOT EXISTS BE_175132_user IDENTIFIED BY 'password';"
mysql -pstudent -e "GRANT ALL PRIVILEGES ON BE_175132.* TO 'BE_175132_user';"
mysql -pstudent -e "FLUSH PRIVILEGES;"
mysql -u BE_175132_user -ppassword BE_175132 < db_dump.sql		# odzyskiwanie dumpa bazy danych
mysql -u BE_175132_user -ppassword BE_175132 -e "UPDATE ps_shop_url SET domain='localhost:15132', domain_ssl='localhost:15133' WHERE id_shop_url=1;"	# ustawienie url i ssl url dla sklepu
