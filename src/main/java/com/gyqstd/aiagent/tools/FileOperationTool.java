package com.gyqstd.aiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.gyqstd.aiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 文件操作工具类，提供文件读取和写入功能
 * 文件存储在项目根目录下的 tmp/file 目录中
 * @author GuYuqi
 * @version 1.0
 */
@Component
public class FileOperationTool {

    private final String FILE_DIR;

    public FileOperationTool() {
        // 使用 FileConstant 中定义的目录，并添加 file 子目录
        this.FILE_DIR = FileConstant.FILE_SAVE_DIR + File.separator + "file";
        // 确保目录存在
        FileUtil.mkdir(FILE_DIR);
    }

    /**
     * 根据文件名构建完整的文件路径
     * @param fileName 文件名
     * @return 完整的文件路径
     */
    private String buildFilePath(String fileName) {
        // 如果文件名不以 .txt 结尾，自动添加 .txt 扩展名
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }
        return FILE_DIR + File.separator + fileName;
    }

    /**
     * 读取文件内容
     * @param fileName 文件名（会自动添加 .txt 扩展名）
     * @return 文件内容，如果文件不存在或读取失败则返回错误信息
     */
    @Tool(description = "Read content from a file in the tmp/file directory")
    public String readFile(@ToolParam(description = "The name of the file to read (without .txt extension)") String fileName) {
        try {
            String filePath = buildFilePath(fileName);
            if (!FileUtil.exist(filePath)) {
                return "Error: File does not exist: " + fileName + ".txt";
            }
            return FileUtil.readString(filePath, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    /**
     * 写入文件内容
     * @param fileName 文件名（会自动添加 .txt 扩展名）
     * @param content 要写入的内容
     * @param append 是否追加模式，true为追加，false为覆盖，null为默认覆盖
     * @return 操作结果信息
     */
    @Tool(description = "Write content to a file in the tmp/file directory with options for append or overwrite mode")
    public String writeFile(
            @ToolParam(description = "The name of the file to write to (without .txt extension)") String fileName,
            @ToolParam(description = "The content to write to the file") String content,
            @ToolParam(description = "Whether to append to the file (true) or overwrite it (false). Default is false (overwrite)") Boolean append) {
        try {
            String filePath = buildFilePath(fileName);
            // 如果 append 为 null，默认为 false（覆盖模式）
            boolean appendMode = append != null && append;
            
            if (appendMode) {
                // 追加模式
                FileUtil.appendString(content, filePath, StandardCharsets.UTF_8);
                return "Content successfully appended to file: " + fileName + ".txt";
            } else {
                // 覆盖模式
                FileUtil.writeString(content, filePath, StandardCharsets.UTF_8);
                return "Content successfully written to file: " + fileName + ".txt";
            }
        } catch (Exception e) {
            return "Error writing to file: " + e.getMessage();
        }
    }

}
