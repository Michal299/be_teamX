FROM prestashop/prestashop:1.7
RUN cd /etc/apache2/sites-available/ \
&& openssl req -x509 -nodes -new -sha256 -days 1024 -newkey rsa:2048 -keyout RootCA.key -out RootCA.pem -subj "/C=US/CN=Example-Root-CA" \
&& openssl x509 -outform pem -in RootCA.pem -out RootCA.crt
COPY ./domains.ext /etc/apache2/sites-available/
RUN cd /etc/apache2/sites-available/ \
&& openssl req -new -nodes -newkey rsa:2048 -keyout localhost.key -out localhost.csr -subj "/C=US/ST=YourState/L=YourCity/O=Example-Certificates/CN=localhost.local" \
&& openssl x509 -req -sha256 -days 1024 -in localhost.csr -CA RootCA.pem -CAkey RootCA.key -CAcreateserial -extfile domains.ext -out localhost.crt
COPY ./000-default.conf /etc/apache2/sites-available/
RUN a2enmod ssl
RUN service apache2 restart