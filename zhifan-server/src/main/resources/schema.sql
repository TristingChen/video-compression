CREATE TABLE IF NOT EXISTS `blog` (
                        `id` int AUTO_INCREMENT NOT NULL,
                        `title` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS "video_info" (
                        "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                        "file_path" text(255) NOT NULL DEFAULT '',
                        "file_name" TEXT(255),
                        "compress_file_name" TEXT(255),
                        "status" integer(1) DEFAULT 0,
                        "msg" TEXT(500),
                        "duration" real(10),
                        "stream_num" INTEGER(2),
                        "file_size" REAL,
                        "compress_file_size" REAL,
                        "created_at" text(10),
                        "updated_at" text(10)
);