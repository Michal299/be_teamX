
# ----------------------------------------------------copy from repo ------------------------------------------
FROM alpine:3.14 AS repoPrepStage
RUN apk update &\
    apk add git

RUN git clone https://github.com/Michal299/be_teamX.git

# ---------------------------------------------- main shop files ----------------------------------------------
FROM prestashop/prestashop:1.7

RUN rm -r /var/www/html/install

COPY --from=repoPrepStage /be_teamX/shop /var/www/html
RUN chmod -R 755 /var/www/html &&\
    chown -R www-data:www-data /var/www/html

# ---------------------------------------------------- ssl ----------------------------------------------------
RUN cd /etc/apache2/sites-available/ \
&& openssl req -x509 -nodes -new -sha256 -days 1024 -newkey rsa:2048 -keyout RootCA.key -out RootCA.pem -subj "/C=US/CN=Example-Root-CA" \
&& openssl x509 -outform pem -in RootCA.pem -out RootCA.crt

COPY --from=repoPrepStage /be_teamX/ssl/domains.ext /etc/apache2/sites-available/

RUN cd /etc/apache2/sites-available/ \
&& openssl req -new -nodes -newkey rsa:2048 -keyout localhost.key -out localhost.csr -subj "/C=US/ST=YourState/L=YourCity/O=Example-Certificates/CN=localhost.local" \
&& openssl x509 -req -sha256 -days 1024 -in localhost.csr -CA RootCA.pem -CAkey RootCA.key -CAcreateserial -extfile domains.ext -out localhost.crt

COPY --from=repoPrepStage /be_teamX/ssl/000-default.conf /etc/apache2/sites-available/

RUN a2enmod ssl
RUN service apache2 restart