FROM centos:centos7

LABEL desc="php-sandbox composer"

RUN yum install -y epel-release                                                && \
    yum install -y http://rpms.famillecollet.com/enterprise/remi-release-7.rpm && \
    yum update -y                                                              && \
    yum install -y --enablerepo=remi-php80 php

RUN php -r "readfile('http://getcomposer.org/installer');" | php -- --version=2.0.11 --install-dir=/usr/bin/ --filename=composer && \
    yum install -y git zip