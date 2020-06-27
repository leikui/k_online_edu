package com.oyyo.excel;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        String fileName = "D:\\" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, Test.class).sheet("模板").doWrite(data());
//        EasyExcel.write(dir, Test.class).sheet("测试").doWrite(data());
    }

    private static List<Test> data() {
        List<Test> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Test test = new Test();
            test.setName("技术" + i);
            test.setWork("方向" + i);
            list.add(test);
        }
        return list;
    }

    @org.junit.Test
    public void read(){
        String fileName = "D:\\1593080264114.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, Test.class, new DemoDataListener()).sheet().doRead();
    }
}
