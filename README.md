# Biznes elektroniczny - projekt

## How to save prestashop
1. Copy whole prestasop installation catalog to the host\
`docker cp prestashopv2:/var/www/html ./prestashop/`
2. Save database

## How to save database
1. Go into mysql container\
`docker exec -it mysql bash`
2. Make dump of database\
`mysqldump -u root -p prestashop > db_dump.sql`
3. Go back to the host and copy db dump from mysql conatiner\
`docker cp mysql:/db_dump.sql ./mysql/init`

## How to run prestashop
In order to run prestashop, all you need to do is run:\
`docker-compose up --build -d`\
from the main project folder.

After containers starts, run command\
`docker exec -it prestashopv2 chown -R www-data:www-data "/var/www/html"`

After a while, shop should be available at: http://localhost:8000

## Members
[Aleksandra Nadzieja](https://github.com/a-leandra)

[Natalia Mierkiewicz](https://github.com/Nataliamier)

[Michał Cwynar](https://github.com/Winetq)

[Michał Błajet](https://github.com/Michal299)
