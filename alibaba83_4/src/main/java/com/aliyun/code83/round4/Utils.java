package com.aliyun.code83.round4;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class Utils {

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws IOException;
    }

//    private static Pattern REGULAR_HTML_TAG = Pattern.compile("<(?<tag>.*)>");
    private static Pattern REGULAR_HTML_TAG = Pattern.compile("(?<tag><(?:[^\"'>]|\"[^\"]*\"|'[^']*')*>)");
//    private static Pattern REGULAR_HTML_TAG = Pattern.compile( "<script[^>]*?>[\\s\\S]*?<\\/script>");
    public static String stripHtmlTag(String html) {

        if (ObjectUtils.isEmpty(html)) {
            return null;
        }

        if(html.equals("<happy>碧油鸡全部退散, 颈腰椎早日康复! </happy>贼真诚")){
            System.out.println();
        }
        StringBuilder builder = new StringBuilder();
        final Matcher matcher = REGULAR_HTML_TAG.matcher(html);
        while (matcher.find()) {
            matcher.appendReplacement(builder, Strings.EMPTY);
            if (log.isDebugEnabled()) {
                log.debug("remove tag {}", matcher.group("tag"));
            }

        }
        matcher.appendTail(builder);
        String str3 = builder.toString();
        return builder.toString();
    }

    public static List<String> randomWords(int numOfWords, int lengthOfWord) {
        return IntStream.iterate(lengthOfWord, IntUnaryOperator.identity())
                .limit(numOfWords)
                .mapToObj(RandomStringUtils::randomAlphabetic)
                .collect(Collectors.toList());
    }

    public static List<String> randomWords(int numOfWords) {
        List<String> words = Arrays.asList("DevStudio", "CloudToolkit", "Cosy",
                "AppObserver", "Flow", "AppStack", "Codeup", "Projex");
        Collections.shuffle(words);
        if (numOfWords > words.size()) {
            words.addAll(randomWords(numOfWords - words.size(), 5));
            return words;
        } else {
            return words.subList(0, numOfWords);
        }
    }

    public static byte[] encodeMessage(String message, Charset charset) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
//        /*old*/
        byte[] charsetNameBytes = charset.toString().getBytes(ISO_8859_1);
//        /*new*/
//        byte[] charsetNameBytes;
//        switch (charset.toString()){
//            case "UTF-8":
//                System.out.println("utf-8");
//                charsetNameBytes = charset.toString().getBytes(UTF_8);
//                break;
//            default:
//                System.out.println("ISO_8859_1");
//                charsetNameBytes = charset.toString().getBytes(ISO_8859_1);
//        }



        try {
            byte len = (byte) charsetNameBytes.length;
            dos.write((byte) len);
            dos.write(charsetNameBytes);
            dos.write(message.getBytes(charset));
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String s = out.toString();
//        byte[] outBytes = out.toByteArray();
        return out.toByteArray();
    }

    public static String decodeMessage(byte[] rawMessage){

        ByteArrayInputStream in = new ByteArrayInputStream(rawMessage);
        DataInputStream dis = new DataInputStream(in);

        try {
//            int c = dis.read();
            String s = charsetNameDecoder.apply(dis);
            return new String(dis.readAllBytes(), s);
        } catch (IOException e) {
            e.printStackTrace();
            return String.format("%s<_>-<_>.", e.getClass().getSimpleName()); // 此行勿动，影响评分
        }
    }

    private static ThreadLocal<String> charsetName = new ThreadLocal<>();

    private static CheckedFunction<DataInputStream, String> charsetNameDecoder = (DataInputStream input) -> {


        byte[] charsetNameBytes = input.readNBytes(input.readByte());
        if (charsetName.get() == null) {
            charsetName.set(new String(charsetNameBytes, ISO_8859_1));
        }

        return charsetName.get();
    };

//    private static CheckedFunction<DataInputStream, String> charsetNameDecoder = (DataInputStream input) -> {
//
//        byte[] charsetNameBytes = input.readNBytes(input.readByte());
//
//        if (charsetName.get() == null) {
//            charsetName.set(new String(charsetNameBytes, ISO_8859_1));
//        }
//
//        return charsetName.get();
//    };

}
