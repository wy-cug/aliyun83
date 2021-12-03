package com.aliyun.code83.round4;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("工具类测试")
class UtilsTest {

    @Nested
    @DisplayName("提取HTML中的文本")
    class ExtractHtmlTest {

        List<Triplet<String, String, String>> data = List.of(
                Triplet.with(
                        "提取普通文本",
                        "Welcome to <pre>DevStudio</pre>",
                        "Welcome to DevStudio"
                ),
                Triplet.with(
                        "提取CJK文本",
                        "有<i>对象</i>了么? 别慌, 送你一个! 领取请加钉钉群: <quote>35991139</quote>",
                        "有对象了么? 别慌, 送你一个! 领取请加钉钉群: 35991139"
                ),
                Triplet.with(
                        "提取Tag文本",
                        "<p>Cosy 提效补全用过没, 还能搜搜搜 https://developer.aliyun.com/tool/cosy</p>",
                        "Cosy 提效补全用过没, 还能搜搜搜 https://developer.aliyun.com/tool/cosy"
                ),
                Triplet.with(
                        "提取嵌套tag文本",
                        "<blockquote><p>401?!! 不要慌，不要急，App Observer 帮助您~ https://help.aliyun.com/document_detail/326231.html 了解一下</p></blockquote>",
                        "401?!! 不要慌，不要急，App Observer 帮助您~ https://help.aliyun.com/document_detail/326231.html 了解一下"
                ),
                Triplet.with(
                        "万圣节惊喜小剧场",
                        "<happy>碧油鸡全部退散, 颈腰椎早日康复! </happy>贼真诚",
                        "碧油鸡全部退散, 颈腰椎早日康复! 贼真诚"
                )
        );

        @TestFactory
        Stream<DynamicTest> dynamicTests() {
            return DynamicTest.stream(data.stream(), (input) -> input.getValue0(), (input) -> {
                String html = input.getValue1();
                String extractedText = Utils.stripHtmlTag(html);
                assertEquals(input.getValue2(), extractedText);
            });
        }
    }

    @Nested
    @DisplayName("编码解码")
    class encodeTest {
        List<Pair<String, Charset>> data = List.of(
                Pair.with(
                        "abcdefg",
                        StandardCharsets.ISO_8859_1
                ),
                Pair.with(
                        "你好么",
                        StandardCharsets.UTF_8
                ),
                Pair.with(
                        "abcd你好么gfc",
                        Charset.forName("GB18030")
                )
        );

        @TestFactory
        @Execution(ExecutionMode.CONCURRENT)
        Stream<DynamicTest> dynamicTests() {
            return DynamicTest.stream(data.stream(), (input) -> input.getValue1().toString(), (input) -> {

                String message = input.getValue0();
                Charset charset = input.getValue1();
//                String message = "abcdefg";
//                Charset charset =StandardCharsets.ISO_8859_1;
                byte[] rawMessage = Utils.encodeMessage(message, charset);
                String decodedMessage = Utils.decodeMessage(rawMessage);

                assertEquals(message, decodedMessage);
            });
        }
    }

}