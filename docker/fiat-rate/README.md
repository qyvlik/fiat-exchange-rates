# fiat-rate-docker-compose

1. make sure you have install `docker` and `docker-compose`
2. copy [.env.example](./.env.example) to [`.env`](./.env)，[`.env`](./.env)
3. define the env var in the [`.env`](./.env) file
4. `docker-compose pull`
5. `docker-compose up -d`

## cn

1. 确认安装好 `docker`, `docker-compose`
2. 将 [.env.example](./.env.example) 拷贝到同目录下，并命名为 [`.env`](./.env)，[`.env`](./.env) 是给 `docker-compose` 使用的
    - [`.env`](./.env) 有若干环境变量
3. 返回项目根路径，然后拉取 `docker` 镜像：`docker-compose pull`
4. 启动项目：`docker-compose up -d`
5. 如果需要重启项目，可以直接 `docker-compose restart`

