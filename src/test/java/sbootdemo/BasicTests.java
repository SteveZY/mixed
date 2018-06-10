package sbootdemo;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicTests {
    @Test
    public void convertCSV() {
        URL url = this.getClass().getResource("/SendFulfilmentUpdate.csv");
        FileSystemResource res = new FileSystemResource(System.getProperty("user.home") + "/new.csv");

        File file = new File(url.getPath());
        Pattern pat = Pattern.compile("\\d{13}");
        File file2Write = res.getFile();

        try {
            file2Write.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));

            BufferedWriter writer = new BufferedWriter(new FileWriter(file2Write));
            String line = reader.readLine();
            while (line != null) {
                StringBuilder sb = new StringBuilder();
                Matcher m = pat.matcher(line);
                sb.append(line);
                while (m.find()) {
                    sb.append(convertTimeStamp(m.group()));
                    sb.append(",");
                }
                line = reader.readLine();

                writer.write(sb.toString());
                writer.newLine();
            }
            writer.flush();
            writer.close();
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void timestampTest() {
        Date date = new Date(1528421330855l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date tsDate = new Date(System.currentTimeMillis());

        System.out.println(sdf.format(date));
        System.out.println(sdf.format(tsDate));


    }

    @Test
    public void kmpPrefix() {
        String p = "ababababca";
        int patLen = p.length();
        ArrayList<Integer> pi = new ArrayList<>();
        pi.add(0);
        //pi[q] =k;
        int k = 0;
        for (int q = 1; q < patLen; q++) {//遍历 pattern
            while (k > 0 && p.charAt(k ) != p.charAt(q)) {
                k = pi.get(k);
            }
            if (p.charAt(k ) == p.charAt(q)) {
                ++k;
            }
            pi.add(q, k);
        }
        System.out.println(String.format("Str Len is %d. pi func %s",patLen, pi));
    }

    private String convertTimeStamp(String ts) {
        Timestamp timeStamp = new Timestamp(Long.valueOf(ts));
        Date date = new Date(timeStamp.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(date);

    }
}
