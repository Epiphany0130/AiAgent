package com.gyqstd.aiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.gyqstd.aiagent.constant.FileConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileOperationTool 测试类
 * @author GuYuqi
 * @version 1.0
 */
@SpringBootTest
class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "testfile.txt";
        String result = tool.readFile(fileName);
        assertNotNull(result);
    }



    @Test
    void writeFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "testfile2";
        String content = "This is a test content2.";
        String result = tool.writeFile(fileName, content, false);
        assertNotNull(result);
    }


}