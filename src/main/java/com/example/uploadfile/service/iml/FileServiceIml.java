package com.example.uploadfile.service.iml;

import com.example.uploadfile.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
public class FileServiceIml implements FileService {

//    @Value("${config.path}")
    private static String path = "E:\\\\Work\\\\Demo\\\\uploadfile\\\\src\\\\main\\\\java\\\\com\\\\example\\\\uploadfile\\\\container";

//    @Value("${config.des}")
    private static String des = "\\\\download";

    public FileServiceIml() {
    }

    @Override
    public int upLoadLargeFile(MultipartFile file)  {
        byte[] buffer = new byte[1024*1024];
        int byteRead = 0;
        int segment = 0;
        String base64 = "";
        try {
            //File fileBase = new File("C:\\Users\\domin\\Videos\\Captures\\Zoom Cuộc họp 2022-04-07 19-32-50.mp4");
            InputStream inputStream = file.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            String finalPath = path + des;
            if (!checkFolderExisted(finalPath)){
                createNewFolder(finalPath);
            }
            System.out.println(finalPath);
            OutputStream outputStream = new FileOutputStream(finalPath+"\\" + this.getFileName(file) + "." + this.getFileFormat(file), true);

            while (byteRead != -1) {
                byteRead = bufferedInputStream.read(buffer, 0, buffer.length);
                if (byteRead == -1) continue;
                byte[] finalByte;
                if (buffer.length > byteRead) {
                    finalByte = Arrays.copyOf(buffer, byteRead);
                } else {
                    finalByte = buffer;
                }

                base64 = toBase64(finalByte);
                downloadFile(base64, outputStream);
                segment++;
            }
            inputStream.close();
            bufferedInputStream.close();
            outputStream.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return segment;
    }

    private void downloadFile(String base64, OutputStream outputStream) {
        try {
            outputStream.write(Base64.getDecoder().decode(base64));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    private String getFileName(MultipartFile file) {
        byte[] name = Objects.requireNonNull(file.getOriginalFilename()).getBytes();
        return MD5Encoder.encode(name);
    }

    private String getFileFormat(MultipartFile file) {
        return file.getContentType().split("/")[1];
    }

    private boolean checkFolderExisted(String path) {
        return new File(path).exists();
    }

    private void createNewFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            boolean res = folder.mkdir();
        }
    }

    private String toBase64(byte[] byteArr) {
        return Base64.getEncoder().encodeToString(byteArr);
    }
}
