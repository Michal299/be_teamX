# Biznes elektroniczny - projekt

## How to save database
1. Go into mysql container\
`docker exec -it mysql bash`
2. Make dump of database\
`mysqldump -u root -p prestashop > db_dump.sql`
3. Go back to the host and copy db_dump from mysql conatiner\
`docker cp mysql:/db_dump.sql ./mysql/init`

## How to run prestashop
In order to run prestashop, all you need to do is run:\
`docker-compose up --build -d`\
from the main project folder.

## Members
[Aleksandra Nadzieja](https://github.com/a-leandra)

[Natalia Mierkiewicz](https://github.com/Nataliamier)

[Michał Cwynar](https://github.com/Winetq)

[Michał Błajet](https://github.com/Michal299)
