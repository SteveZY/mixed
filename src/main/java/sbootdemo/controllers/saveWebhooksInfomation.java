package sbootdemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
public class saveWebhooksInfomation {
    static FileSystemResource res;

    static {
        res = new FileSystemResource(System.getProperty("user.home") + "/webhooksWithTimeStamp.log");
    }

    @PostMapping("/wh")
    public String saveWebHookMsg(@RequestBody String body) {
        Map<String, String> content = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        content.put("webhook msg", body);
        content.put("timestamp", System.currentTimeMillis()+"");//sdf.format(Calendar.getInstance().getTime()));
        saveToFile(content);
        return body;
    }

    private void saveToFile(Map<String, String> content) {
        BufferedWriter writer;
        ObjectMapper objMapper = new ObjectMapper();
        String contentStr = null;
        try {
            contentStr = objMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            log.error("Exception found as {}", e);
        }

        try {
            File file = res.getFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(res.getFile(), true));
            if (!StringUtils.isEmpty(contentStr)) {
                writer.write(contentStr);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
