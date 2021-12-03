// 请不要修改包名
package com.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionInitializer;
import com.aliyun.fc.runtime.StreamRequestHandler;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.io.IOUtils;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 代码API名称前缀搜索
 *
 * 请不要修改类名CodePrefixSearchRequest
 * </pre>
 *
 * @author DevStudio
 * @author Alibaba Cloud AI Coding Assistant
 */
public class CodePrefixSearchRequest implements StreamRequestHandler, FunctionInitializer {

    /**
     * <pre>
     * 搜索代码API名称前缀
     *
     * 输入数据格式：
     * {
     * "prefixs":["",""],  // 字符串前缀
     * "oss_endpoint": "", // OSS访问endpoint,
     * "oss_key": "",  // OSS数据集访问地址
     * "bucket": "",  // OSS Bucket
     * "access_key": "", // OSS 访问AK
     * "access_secret": "" // OSS 访问SK
     * }
     *
     * 输出格式:
     * { "prefix1": [""], "prefix2": ["", ""] }
     *
     * 数据示例：
     * 输入：
     *  {"prefixs":["Buffer","Str"], "oss_endpoint": "*****", "oss_key": "*****" "bucket": "*****", "access_key": "*****", "access_secret": "*****"}
     *
     * 输出：
     * {
     *  "Buffer":["BufferedReader", "BufferedInputStream"]，
     *  "Str":["String", "StringBuilder"]，
     * }
     *
     * 注：请不要修改方法名
     * </pre>
     *
     * @param input 输入流
     * @param output 输出流
     * @param context 上下文信息
     * @throws IOException IO异常
     */
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        FunctionComputeLogger logger = context.getLogger();
        logger.debug(String.format("RequestID is %s %n", context.getRequestId()));
        String inputJson = IOUtils.toString(input, "UTF-8");
        if (inputJson == null || "".equals(inputJson)) {
            error(output, "invalid input json string.");
            return;
        }
        // 解析输入JSON数据
        JSONObject params = JSON.parseObject(inputJson);
        if (params == null) {
            error(output, "invalid input json data.");
            return;
        }
        // 创建OSS Client
        OSS ossClient = createOSSClient(params);
        // 实现算法并返回结果
        Map<String, List<String>> result = calculate(ossClient, params);
        System.out.println(result.size());
        // 输出结果
        output.write(JSON.toJSONString(result).getBytes());
        output.flush();
    }

    @Override
    public void initialize(Context context) throws IOException {
        FunctionComputeLogger logger = context.getLogger();
        logger.debug(String.format("RequestID is %s %n", context.getRequestId()));
    }

    /**
     * 创建OSS Client
     *
     * @param params 输入参数
     * @return
     */
    @SuppressWarnings("unused")
    private OSS createOSSClient(JSONObject params) throws IOException {
        String endpoint = params.getString("oss_endpoint");
        String accessKeyId = params.getString("access_key");
        String secretAccessKey = params.getString("access_secret");
        // TODO 创建OSS Client，建议参照OSS文档 https://help.aliyun.com/document_detail/32010.html
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

        return ossClient;
    }

    /**
     * <pre>
     * 计算前缀字符串查找
     *
     * 输出格式:
     * { "prefix1": [""], "prefix2": ["", ""] }
     *
     * 示例：
     * {
     *    "Buffer":["BufferedReader", "BufferedInputStream"]，
     *    "Str":["String", "StringBuilder"]，
     * }
     * </pre>
     *
     * @param ossClient OSS Client
     * @param params 输入参数
     * @return 计算结果
     * @throws IOException IO异常
     */
    @SuppressWarnings("unused")
    private Map<String, List<String>> calculate(OSS ossClient, JSONObject params) throws IOException {
        // OSS文件地址
        String ossFileKey = params.getString("oss_key");
        // OSS Bucket
        String ossBucket = params.getString("bucket");
        // 待计算的前缀字符串集合
        JSONArray prefixs = params.getJSONArray("prefixs");
        // 计算结果，key为前缀字符串，value为匹配前缀的字符串集合
        Map<String, List<String>> result = new HashMap<>();
//        OSSObject ossObject = null;
        // TODO 获取OSS文件数据，建议参照OSS文档 https://help.aliyun.com/document_detail/84823.html
        System.out.println("Object content:");
        String bucketName = params.getString("bucket");
        String objectName = params.getString("oss_key");
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        Trie trie = new Trie(' ');
        List<String> readerStringList = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            trie.insert(line);
            readerStringList.add(line);
        }
        JSONArray prefixJsonArray = params.getJSONArray("prefixs");
        List<String> prefixList = new ArrayList<>();
        for(Object obj:prefixJsonArray){
            prefixList.add(obj.toString());
        }
        System.out.println(prefixList.toString());
        for(String prefix : prefixList) {
            if(prefix.equals("")){
                result.put("",new ArrayList<>());
                continue;
            }
            Trie triePrefix = trie.searchPrefix(prefix);
            if(triePrefix==null){
                result.put("",new ArrayList<>());
                continue;
            }
            ListStack<TrieAndPrefix> stack = new ListStack<>();
            stack.push(new TrieAndPrefix(triePrefix, prefix));
//            for(char ch:triePrefix.getChildren().keySet()){
//                stack.push(new TrieAndPrefix(triePrefix.getChildren().get(ch), prefix+triePrefix.getChildren().get(ch).getC()));
//            }
            List<String> r = trie.getResultByPrefix(stack);
//            for (String s : r) {
//                System.out.println("** "+s);
//            }
            System.out.println(r.size());
            result.put(prefix,r);
        }
// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        reader.close();

// 关闭OSSClient。
        ossClient.shutdown();


        // TODO 实现前缀字符串查找算法，建议考虑百万数据集情况下的性能及内存消耗问题
        return result;
    }

    /**
     * 输出错误信息
     * @param output 输出流
     * @param message 错误信息
     */
    private void error(OutputStream output, String message) throws IOException {
        Map<String, String> msg = new HashMap<>();
        msg.put("errorMessage", message);
        output.write(JSON.toJSONString(msg).getBytes());
        output.flush();
    }
}
