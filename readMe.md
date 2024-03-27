### 部署文档
# 1.进入项目，renren-ui,进行前端的编译
    npm install
    
    npm run build
# 2.步骤一中产生的dist文件，挪至\video-compression\zhifan-server\src\main\resources
    注意：后期考虑前端编译后自行文件迁移
    目的：下一步中 maven编译将前端资源代码打包进jar包中

# 3.在项目跟目录进行java项目的maven编译
    mvn install

# 4.服务器环境的运行
    * 准备jdk环境 ffmpeg环境
    * 将\zhifan-server\src\main\resources\application.yaml，和sqlite3的初始化数据库放在和jar包同级目录下
    * application.yaml改动说明：如环境中有ffmpeg则使用服务器的ffmpeg,如没有使用javacv
        #ffmpegCommand: "ffmpeg -i {input_file} -b:v {videoBitrate} -bufsize 1000k  {output_file}"
    * 启动： java -jar zhifan-server.jar --spring.config.location=application.yaml