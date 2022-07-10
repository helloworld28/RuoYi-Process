docker run --name mysql --restart=always \
    -v /media/powerjun/data/mysql_data:/var/lib/mysql \
    -p 3317:3306 \
    -e MYSQL_ROOT_PASSWORD="root" \
    -e TZ=Asia/Shanghai \
    -e MYSQL_USER=¨admin¨\
    -e MYSQL_PASSWORD=¨admin¨\
    -d mysql:8.0 --lower-case-table-names=1
