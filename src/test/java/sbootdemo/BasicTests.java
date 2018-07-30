package sbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.*;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
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
    public void kmpPrefix() {//mainly compare to end of latest p[1..q]
        String p = /*"aaab";*/"abcdefgh";
        int patLen = p.length();
        ArrayList<Integer> pi = new ArrayList<>();//pi's length is same as the length of P
        //P[1..q] is the matched part that the given Pattern to target TEXT (index follows the book's style)
        //Since Pattern can match the TEXT at any position,
        //then we have to populate all the LENGTH 'k' of longest prefix (also the suffix of P[1..q]) for every prefix of P
        //PI 函数中存放的是到当前位置q的子串p[0..q]的后缀、同时也是其最长前缀的 长度。当index从0开始时，其也正好对应于下一轮迭代时要跟p[q]比较的
        //前缀的最后一个字符
        //to get PI function. Then with the PI function, we can immediately know next start position in the TEXT to compare
        //pi[q] =k;
        pi.add(0);//the first prefix only contains one character 'a' whose longest pefix's (also suffix of 'a') length is always 0
        int k = 0;
        for (int q = 1; q < patLen; q++) {//遍历 pattern
            while (k > 0 && p.charAt(k) != p.charAt(q)) {
                //next char not match , go back based on k and verify again until find one or k not greater than 0
                k = pi.get(k-1);
            }
            if (p.charAt(k) == p.charAt(q)) {//start point is the k=0, q=1 that make k just 1 less than q
                //compare the the last char p[q] to p[k]
                //Increase the length of the prefix for the new q if k th char equals to q th char
                //the longest prefix is getting 1 char longer.
                ++k;//And also for next time to retrieve (k+1)th character.
            }
            pi.add(q, k);
        }
        System.out.println(String.format("Str Len is %d. pi func %s", patLen, pi));
    }

    private String convertTimeStamp(String ts) {
        Timestamp timeStamp = new Timestamp(Long.valueOf(ts));
        Date date = new Date(timeStamp.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(date);

    }

    @Test
    public void getVarMap() {
        URL url = this.getClass().getResource("/app.properties");
//        URL tempUrl = this.getClass().getResource("/tempprops");

        File file = new File(url.getPath());
        File dstFile = new File(file.getParentFile().getAbsolutePath() + File.separator + "tempprops");
        try {
//            if(dstFile.exists()){
//                dstFile.delete();
//            }
            Files.copy(file.toPath(), dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> theMap = new HashMap<>();
        generateVarMap(dstFile, theMap);
        log.info("Final varMap is {}",theMap);
    }

    private void generateVarMap(File file, Map<String, String> theMap) {
        if (!file.exists()) {
            log.warn("File doesn't exist!!");
            return;
        }
        File tmpFile = new File(file.getParentFile().getAbsolutePath() + File.separator + "tempfile");//Will rename to the incoming file
        BufferedReader reader;
        BufferedWriter writer;
        try {
            tmpFile.createNewFile();
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(tmpFile));
            String line;// = reader.readLine();
            String newLine;
            boolean isChanged = false;

            while (null != (line = reader.readLine())) {
                String[] props = line.split("=");
                if (props.length != 2) {
                    log.error("Format error. Returning...");
                    return;
                }
                List<String> varPair = Stream.of(props).map(prop -> prop.trim()).collect(Collectors.toList());
                if (props[1].matches(".*\\$\\{\\S+}.*")) {
                    boolean done = substituteVarWithMap(varPair, theMap);
                    if (!done) {
                        isChanged = true;
                        newLine = generateNewLine(varPair);
                        writer.write(newLine);
                        writer.newLine();
                        continue;
                    }
                }
                theMap.put(varPair.get(0), varPair.get(1));
                writer.write(line);
                writer.newLine();
            }

            writer.flush();
            writer.close();
            reader.close();
            if (isChanged) {
                file.delete();
                tmpFile.renameTo(file); // new File(file.getAbsolutePath())
                generateVarMap(file, theMap);//recursively
            } else {
                tmpFile.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateNewLine(List<String> props) {
        if (props.size()!= 2) {
            log.error("Format error!! returning");
            return null;
        }
        return String.format("%s=%s", props.get(0), props.get(1));
    }

    private boolean substituteVarWithMap(final List<String> prop, Map<String, String> theMap) {
        Pattern pat = Pattern.compile("\\$\\{\\S+}");
        String tobeChangedValue = prop.get(1);
        Matcher matcher = pat.matcher(tobeChangedValue);
        while (matcher.find()) {
            String matched = matcher.group();
            log.info("Current matched end is {} and matched is:{}", matcher.end(), matched);
            String key = matched.substring(2, matched.length() - 1);
            String value;
            if (null != (value = theMap.get(key))) {
                //replace with value
                String tobeReplaced = "\\$\\{" + key + "}";
                //new StringBuilder().append("\\").append(matcher.group()).toString();
//                matcher.reset();
                tobeChangedValue = tobeChangedValue.replaceFirst(tobeReplaced, value);
                log.info("new line is: {}", tobeChangedValue);
                matcher.reset(tobeChangedValue);
            }
        }
        prop.remove(1);
        prop.add(1,tobeChangedValue);
        return !tobeChangedValue.matches(".*\\$\\{\\S+}.*");//replaced all vars of the line?
    }

}
