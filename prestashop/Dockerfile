FROM prestashop/prestashop:1.7
COPY ./html /var/www/html
RUN rm -r /var/www/html/install
RUN chown -R www-data:www-data "/var/www/html"