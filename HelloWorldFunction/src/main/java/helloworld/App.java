package helloworld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<Object, Object> {

    /**
     * Initialization of variables (Valid search fields collection, es domain endpoint, and index name)
     */

    private static final String[] SEARCH_FIELD_ARR =
            new String[]{"ACK_ID", "PLAN_NAME", "SPONSOR_DFE_NAME", "SPONS_DFE_MAIL_US_STATE"};
    private static final Set<String> SEARCH_FIELD_SET = new HashSet<>(Arrays.asList(SEARCH_FIELD_ARR));

    private static final String ES_DOMAIN_ENDPOINT
            = "https://search-filippogu1-es-test-b3te2dcpii5fv5p3w4jw7wg6ji.us-east-2.es.amazonaws.com";
    private static final String INDEX_NAME = "pc_testdata_json_index";


    /**
     * Java handler for AWS lambda function incoming request
     * (
     *     input - JSONObject which is transformed from API Gateway URL parameters
     *     {
     *      "searchField" : "$input.params('searchField')",
     *      "searchValue" : "$input.params('searchValue')"
     *     }
     *     output body - a JSONObject which has key = "res", and value is JSONArray which contains the query hit JSONObjects
     * )
     *
     */
    public Object handleRequest(final Object input, final Context context) {

        // "PLAN_NAME": "STEVEN KING ENTERPRISES, INC. RETIREMENT PLAN"

        // Get query parameters from API Gateway URL GET parameters
        // LinkedHashMap<String, String> inputParameters = (LinkedHashMap<String, String>) input;
        String queryField = "PLAN_NAME"; // inputParameters.get("searchField");
        String queryValue = "STEVEN+KING"; // inputParameters.get("searchValue");

        // If queryField or QueryType is wrong, return error message JSON body.
        if (!isSearchFieldValid(queryField)) return getErrorJSONObject("Search field value is wrong!");

        // Form the final url for es search API
        String url = getFullSearchURL(ES_DOMAIN_ENDPOINT, INDEX_NAME, queryField, queryValue);
        System.out.println(url);

        System.out.println("Testing - Send Http GET request based on search field, search type and search value");
        try {
            // If succeeded, we will get the expected JSON Object which has "res" as key, and an JSONArray.
            String response = getPageContents(url);
            return getSearchResultJSONObject(response);
        } catch (Exception e) {
            // If not, URL openstream has issues, we have to recheck the URL.
            e.printStackTrace();
            return getErrorJSONObject("URL openstream has issues, check code for detail!");
        }
    }

    /**
     * Check if searchField is valid
     */
    public boolean isSearchFieldValid(String searchField) { return SEARCH_FIELD_SET.contains(searchField); }

    /**
     * Returns a error message JSONObject, to demonstrate as output body in API Gateway
     */
    public JSONObject getErrorJSONObject(String errorInfo) {
        JSONObject err_json_obj = new JSONObject();
        err_json_obj.put("error", errorInfo);
        return err_json_obj;
    }

    /**
     * Returns the complete search result JSONObject, as
     *      - Key : "res"
     *      - Val : an JSONArray which has qualified JSON objects
     */
    public JSONObject getSearchResultJSONObject(String response) {
        JSONParser parser = new JSONParser();

        JSONObject res = new JSONObject();
        JSONArray res_arr = new JSONArray();
        long totalCount = 0L;

        try {
            JSONObject jsonObject_full = (JSONObject) parser.parse(response);
            JSONObject jsonObject_hits = (JSONObject) jsonObject_full.get("hits");
            // System.out.println((long)jsonObject_hits.get("total"));
            totalCount = (long)jsonObject_hits.get("total");
            JSONArray jsonArray_hits = (JSONArray) jsonObject_hits.get("hits");

            for (int i = 0; i < jsonArray_hits.size(); i++) {
                JSONObject currHit = (JSONObject) jsonArray_hits.get(i);
                JSONObject currHit_source = (JSONObject) currHit.get("_source");
                res_arr.add(currHit_source);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        res.put("res", res_arr);
        res.put("total", totalCount);

        return res;
    }

    /**
     * Create the full URL for es search API.
     */
    public String getFullSearchURL(String esDomainEndpoint, String indexName,
                             String queryField, String queryValue) {
        return esDomainEndpoint + "/" + indexName + "/_search?q=" + queryField + ":" + queryValue + "";
    }

    /**
     * Return the response string from url.openStream.
     */
    public String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
