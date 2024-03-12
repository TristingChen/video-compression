package com.zhifan.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 环形数组模拟时钟
 * @author Miracle
 * @date 2023/1/10 14:43
 */
@Slf4j
@Data
public class CircleArray<T> {

    /**
     * 安全锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 一轮次数
     */
    private Long circleSize;

    /**
     * 当前指标所在位置，每隔一秒进入到下一个位置
     */
    private volatile long currentIndex = 0;

    /**
     * 数据所在节点
     */
    private Map<T, Long> dataIndexMap;

    /**
     * 实际数组
     */
    private Map<Long, Set<T>> indexDataMap;


    /**
     * 初始化循环数组
     * @param circleSize 环形数组长度
     */
    public CircleArray(long circleSize){
        if (circleSize < 60){
            circleSize = 60;
        }
        this.circleSize = circleSize;
        this.dataIndexMap = new HashMap<>();
        this.indexDataMap = new HashMap<>();
    }

    /**
     * 初始化循环数组
     * @param circleSize 环形数组长度
     * @param maxData 最大数据数
     */
    public CircleArray(long circleSize, int maxData){
        // 设定最小值为60s
        if (circleSize < 60){
            circleSize = 60;
        }
        this.circleSize = circleSize;
        this.dataIndexMap = new HashMap<>(maxData);
        this.indexDataMap = new HashMap<>(maxData);
    }



    /**
     * 添加或修改数据
     * @param data 数据
     * @param offset 保存偏移值
     */
    public void addOrUpdateTime(T data, Long offset){
        if (offset <= 0){
            throw new RuntimeException("非法偏移值");
        }
        try{
            // 上锁
            lock.lock();
            // 计算插入位置
            long position = (currentIndex + offset) % circleSize;
            // 移除旧数据
            removeOldData(data);
            // 记录数据位置信息
            dataIndexMap.put(data, position);
            Set<T> dataSet = indexDataMap.getOrDefault(position, new HashSet<>());
            dataSet.add(data);
            // 记录位置数据信息
            indexDataMap.put(position, dataSet);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 移除旧数据
     * @param data
     */
    private void removeOldData(T data) {
        // 获取上一次的插入位置
        Long oldPosition = dataIndexMap.getOrDefault(data, null);
        // 判断是否存在数据
        if (Objects.nonNull(oldPosition)){
            // 获取上一次插入位置的数据
            Set<T> dataSet = indexDataMap.get(oldPosition);
            // 移除数据
            dataSet.remove(data);
            // 判断数据组是否为空
            if (dataSet.size() == 0){
                // 移除当前位置数据
                indexDataMap.remove(oldPosition);
            }
        }
    }

    /**
     * 添加或修改数据
     * @param dataSet 数据
     * @param offset 保存偏移值
     */
    public void addOrUpdateTime(Set<T> dataSet, Long offset){
        if (offset <= 0){
            throw new RuntimeException("非法偏移值");
        }
        if (dataSet.isEmpty()){
            return;
        }
        if(offset >= this.circleSize){
            throw new RuntimeException("偏移量不能大于等于结构体的最大长度");
        }
        try{
            // 上锁
            lock.lock();
            // 计算插入位置
            long position = (currentIndex + offset) % circleSize;
            for (T data : dataSet){
                // 获取上一次的插入位置
                removeOldData(data);
                // 记录数据位置信息
                dataIndexMap.put(data, position);
            }
            Set<T> oldDataSet = indexDataMap.getOrDefault(position, new HashSet<>());
            oldDataSet.addAll(dataSet);
            // 记录位置数据信息
            indexDataMap.put(position, dataSet);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    /**
     * 移除数据
     * @param data 数据
     */
    public void deleteTime(T data){
        try{
            lock.lock();
            Long position = dataIndexMap.get(data);
            if (Objects.isNull(position)){
                return;
            }
            indexDataMap.remove(position);
            dataIndexMap.remove(data);
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 游标移动到下一个位置并获取数据
     * @return 数据数组
     */
    public Set<T> pullAndNext(){
        try{
            // 上锁
            lock.lock();
            Set<T> dataSet = this.indexDataMap.remove(this.currentIndex);
            // 判断是否有数据
            if (Objects.nonNull(dataSet) && dataSet.size() > 0){
                // 循环移除数据位置信息
                for (T data : dataSet){
                    this.dataIndexMap.remove(data);
                }
                return dataSet;
            }
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
            this.currentIndex = (this.currentIndex + 1) % circleSize;
            lock.unlock();
        }
    }
}
