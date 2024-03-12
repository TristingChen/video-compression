package com.zhifan.utils;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.zhifan.constant.LogTemplate;
import com.zhifan.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.*;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenjialing
 */
@Slf4j
public class JavaCvUtil {

    /**
     * 基于JavaCV跨平台调用ffmpeg命令
     * duration 录制时长为多少秒的视频
     */
    public static String convertByCommand(String sourcePath, String destPath, String duration) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始执行基于JavaCV跨平台调用ffmpeg命令录制视频");
        try {

            String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
            ProcessBuilder pb = new ProcessBuilder(ffmpeg, "-i", sourcePath, "-vcodec", "h264", destPath);
            if (StrUtil.isNotBlank(duration)) {
                pb = new ProcessBuilder(ffmpeg, "-i", sourcePath, "-vcodec", "h264", "-t", duration, destPath);
            }
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        log.info("【convertByFfmpegCommand】::stopWatch.getTotalTimeSeconds() ==> 【{}】", stopWatch.getTotalTimeSeconds());
        return destPath;

    }

    /**
     * 视频转码函数(仅转码)
     *
     * @param inputfile  原始视频文件完整路径
     * @param outputfile 目标视频文件完整保存路径（必须完整文件名，即包含格式后缀，推荐格式后缀为.mp4）
     * @throws Exception 异常
     */
    public static void videoConvert(String inputfile, String outputfile) throws Exception
    {
        if (outputfile.lastIndexOf('.') < 0)
        {
            throw new BusinessException("Error! Output file format undetected!");
        }
        String format = outputfile.substring(outputfile.lastIndexOf('.'));

        FFmpegLogCallback.set();
        Frame frame;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputfile);
        FFmpegFrameRecorder recorder = null;

        try
        {
           log.info(inputfile+"开始初始化帧抓取器");

            // 初始化帧抓取器，例如数据结构（时间戳、编码器上下文、帧对象等），
            // 如果入参等于true，还会调用avformat_find_stream_info方法获取流的信息，放入AVFormatContext类型的成员变量oc中
            grabber.start(true);


            // grabber.start方法中，初始化的解码器信息存在放在grabber的成员变量oc中
            AVFormatContext avformatcontext = grabber.getFormatContext();

            // 文件内有几个媒体流（一般是视频流+音频流）
            int streamNum = avformatcontext.nb_streams();

            // 没有媒体流就不用继续了
            if (streamNum < 1)
            {
                throw new BusinessException(inputfile+"Error! Output file format undetected!");
            }

            // 取得视频的帧率
            double framerate = grabber.getVideoFrameRate();

            log.info(LogTemplate.PROCESS_LOG_MSG_TEMPLATE,"读取源视频文件",String.format("视频帧率[%f]，视频时长[%d]秒，媒体流数量[%d]\r\n", framerate, avformatcontext.duration() / 1000000,
                    avformatcontext.nb_streams()),inputfile);

            // 遍历每一个流，检查其类型
            for (int i = 0; i < streamNum; i++)
            {
                AVStream avstream = avformatcontext.streams(i);
                AVCodecParameters avcodecparameters = avstream.codecpar();
            }

            // 视频宽度
            int frameWidth = grabber.getImageWidth();
            // 视频高度
            int frameHeight = grabber.getImageHeight();
            // 音频通道数量
            int audiochannels = grabber.getAudioChannels();

            recorder = new FFmpegFrameRecorder(outputfile, frameWidth, frameHeight, audiochannels);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);

            recorder.setFormat(format);
            // 使用原始视频的码率，若需要则自行修改码率
            recorder.setVideoBitrate((int)Math.ceil(grabber.getVideoBitrate()*0.2));

            // 一秒内的帧数，帧率
            recorder.setFrameRate(framerate);

            // 两个关键帧之间的帧数
            recorder.setGopSize((int)framerate);

            // 设置音频通道数，与视频源的通道数相等
            recorder.setAudioChannels(grabber.getAudioChannels());

            recorder.start();

            int videoframenum = 0;
            int audioframenum = 0;
            int dataframenum = 0;

            // 持续从视频源取帧
            while (null != (frame = grabber.grab()))
            {
                // 有图像，就把视频帧加一
                if (null != frame.image)
                {
                    videoframenum++;
                    // 取出的每一帧，都保存到视频
                    recorder.record(frame);
                }

                // 有声音，就把音频帧加一
                if (null != frame.samples)
                {
                    audioframenum++;
                    // 取出的每一帧，都保存到视频
                    recorder.record(frame);
                }

                // 有数据，就把数据帧加一
                if (null != frame.data)
                {
                    dataframenum++;
                }
            }


        } catch (Exception e)
        {
            // e.printStackTrace();
            throw e;
        } finally
        {
            if(recorder!=null){
                recorder.close();
            }
            grabber.close();
        }
    }
    public static String videoConvert(String inputFile, String outputPath, Integer width, Integer height, String videoFormat) {
        List<String> paths = Splitter.on(".").splitToList(inputFile);
        String ext = paths.get(paths.size() - 1);
        if (StringUtils.isEmpty(videoFormat)) {
            videoFormat = ext;
        }
        String newFileName = IdUtil.simpleUUID() + "." + videoFormat;
        String resultPath =
                Joiner.on(File.separator).join(Arrays.asList(outputPath, newFileName));
        FFmpegLogCallback.set();
        Frame frame;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        FFmpegFrameRecorder recorder = null;
        try {
            // 初始化帧抓取器，例如数据结构（时间戳、编码器上下文、帧对象等），
            // 如果入参等于true，还会调用avformat_find_stream_info方法获取流的信息，放入AVFormatContext类型的成员变量oc中
            grabber.start(true);
            // grabber.start方法中，初始化的解码器信息存在放在grabber的成员变量oc中
            AVFormatContext avformatcontext = grabber.getFormatContext();
            // 文件内有几个媒体流（一般是视频流+音频流）
            int streamNum = avformatcontext.nb_streams();
            // 没有媒体流就不用继续了
            if (streamNum < 1) {
                log.info("视频文件格式不对");
                return "error";
            }
            // 取得视频的帧率
            int framerate = (int) grabber.getVideoFrameRate();
            log.info("视频帧率:{}，视频时长:{}秒，媒体流数量:{}", framerate, avformatcontext.duration() / 1000000,
                    streamNum);
            // 遍历每一个流，检查其类型
            for (int i = 0; i < streamNum; i++) {
                AVStream avstream = avformatcontext.streams(i);
                AVCodecParameters avcodecparameters = avstream.codecpar();
                log.info("流的索引:{}，编码器类型:{}，编码器ID:{}", i, avcodecparameters.codec_type(),
                        avcodecparameters.codec_id());
            }
            // 源视频宽度
            int frameWidth = grabber.getImageWidth();
            // 源视频高度
            int frameHeight = grabber.getImageHeight();
            // 源音频通道数量
            int audioChannels = grabber.getAudioChannels();
            log.info("源视频宽度:{}，源视频高度:{}，音频通道数:{}", frameWidth, frameHeight, audioChannels);
            recorder = new FFmpegFrameRecorder(resultPath, width, height, audioChannels);
            // 设置H264编码
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            // 设置视频格式
            recorder.setFormat(videoFormat);
            // 使用原始视频的码率，若需要则自行修改码率
            recorder.setVideoBitrate(grabber.getVideoBitrate());
            // 一秒内的帧数，帧率
            recorder.setFrameRate(framerate);

            // 两个关键帧之间的帧数
            recorder.setGopSize(framerate);
            // 设置音频通道数，与视频源的通道数相等
            recorder.setAudioChannels(grabber.getAudioChannels());
            recorder.start();
            int videoFrameNum = 0;
            int audioFrameNum = 0;
            int dataFrameNum = 0;
            // 持续从视频源取帧
            while (null != (frame = grabber.grab())) {
                // 有图像，就把视频帧加一
                if (null != frame.image) {
                    videoFrameNum++;
                    // 取出的每一帧，都保存到视频
                    recorder.record(frame);
                }
                // 有声音，就把音频帧加一
                if (null != frame.samples) {
                    audioFrameNum++;
                    // 取出的每一帧，都保存到视频
                    recorder.record(frame);
                }
                // 有数据，就把数据帧加一
                if (null != frame.data) {
                    dataFrameNum++;
                }
            }
            log.info("转码完成，视频帧:{}，音频帧:{}，数据帧:{}", videoFrameNum, audioFrameNum, dataFrameNum);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        } finally {
            if (recorder != null) {
                try {
                    recorder.close();
                } catch (Exception e) {
                    log.info("recorder.close异常" + e);
                }
            }
            try {
                grabber.close();
            } catch (FrameGrabber.Exception e) {
                log.info("frameGrabber.close异常" + e);
            }
        }
        return newFileName;

    }

    public static void videoDeal(){
        String inputFilePath = "D:\\BaiduNetdiskDownload\\原始文件.MP4";
        String outputFilePath = "./output.mp4";
        int targetBitrate = 1024000; // 目标码率为 1 Mbps

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath);
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, grabber.getImageWidth(), grabber.getImageHeight());
        FFmpegLogCallback.set();
        try {
            grabber.start();
            recorder.setFormat(grabber.getFormat());
            recorder.setFrameRate(grabber.getFrameRate());
            recorder.setVideoCodec(grabber.getVideoCodec());
            recorder.setPixelFormat(grabber.getPixelFormat());
            recorder.setVideoBitrate(targetBitrate);

            recorder.start();

            Frame frame;
            while ((frame = grabber.grabFrame()) != null) {
                recorder.record(frame);
            }

            recorder.stop();
            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        videoConvert("D:\\BaiduNetdiskDownload\\原始文件.MP4","./outfile.mp4");
    }
}
