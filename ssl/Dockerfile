
# ----------------------------------------------------copy from repo ------------------------------------------
FROM alpine:3.14 AS repoPrepStage
RUN apk update &\
    apk add git

RUN git clone --single-branch https://github.com/Michal299/be_teamX.git

# ---------------------------------------------- main shop files ----------------------------------------------
FROM prestashop/prestashop:1.7

COPY --from=repoPrepStage /be_teamX/shop /var/www/html
RUN chmod -R 755 . &&\
    chown -R www-data:www-data /var/www/html

# ---------------------------------------------------- ssl ----------------------------------------------------
RUN mkdir /ssl_files
COPY --from=repoPrepStage /be_teamX/ssl/000-default.conf /ssl_files/000-default.conf
COPY --from=repoPrepStage /be_teamX/ssl/domains.ext /ssl_file/domains.ext
COPY --from=repoPrepStage /be_teamX/ssl/ssl.sh /ssl_files/ssl.sh

CMD ["bash", "/ssl_files/ssl.sh"]