package utils;


import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.*;
import java.util.logging.Level;

public class ESBulkAPI_ForPostData {

    private static Logger logger = LogManager.getLogger(ESBulkAPI_ForPostData.class);

    public void testBulkIndexReuqest() {

        String hostURL = "search-filippogu1-es-test-b3te2dcpii5fv5p3w4jw7wg6ji.us-east-2.es.amazonaws.com";

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostURL, 443, "https")));

        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                logger.debug("Executing bulk [{}] with {} requests",
                        executionId, numberOfActions);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                if (response.hasFailures()) {
                    logger.warn("Bulk [{}] executed with failures", executionId);
                } else {
                    logger.debug("Bulk [{}] completed in {} milliseconds",
                            executionId, response.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  Throwable failure) {
                logger.error("Failed to execute bulk", failure);
            }
        };


        BulkProcessor.Builder builder = BulkProcessor.builder(
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                listener);
        builder.setBulkActions(50);
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        builder.setConcurrentRequests(1);
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        builder.setBackoffPolicy(BackoffPolicy
                .constantBackoff(TimeValue.timeValueSeconds(1L), 3));

        BulkProcessor bulkProcessor = builder.build();

        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream("F:\\Personal Capital\\F_5500_2017_Latest\\f_5500_2017_latest.json\\f_5500_2017_latest.json");
            reader = new BufferedReader(new InputStreamReader(fis));

            System.out.println("Reading File line by line using BufferedReader");

            int lineNum = 1;
            String line = reader.readLine();
            while (line != null) {
                System.out.println("Processing line # " + lineNum);

                if (line.length() > 0) {
                    IndexRequest req = new IndexRequest("pc_testdata_json_index", "_doc").id(String.valueOf(lineNum));
//                            .create(true)
//                            .opType(DocWriteRequest.OpType.INDEX)
//                            .timeout(TimeValue.timeValueMillis(30000));

                    req.source(line, XContentType.JSON);
                    bulkProcessor.add(req);
                }

                line = reader.readLine();
                lineNum++;
            }
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(ESBulkAPI_ForPostData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ESBulkAPI_ForPostData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
                fis.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ESBulkAPI_ForPostData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        bulkProcessor.close();
    }

    public static void main(String[] args) {
        ESBulkAPI_ForPostData bulkTest = new ESBulkAPI_ForPostData();
        bulkTest.testBulkIndexReuqest();
        System.exit(0);
    }

}
