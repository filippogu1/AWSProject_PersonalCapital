package helloworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class AppTest {
  @Test
  @Ignore
  public void successfulResponse() {
    App app = new App();
    GatewayResponse result = (GatewayResponse) app.handleRequest(null, null);
    assertEquals(result.getStatusCode(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains("\"hello world\""));
    assertTrue(content.contains("\"location\""));
  }

  @Test
  public void getFullSearchURLTest() {
    App app = new App();
    String url1 = app.getFullSearchURL("domainName", "test-index",
            "field1", "value1");
    String url2 = app.getFullSearchURL("domainName", "test-index",
            "field2", "value2");
    assertEquals(url1, "domainName/test-index/_search?q=field1:value1");
    assertEquals(url2, "domainName/test-index/_search?q=field2:value2");
  }

  @Test
  public void getErrorJSONObjectTest() {
    App app = new App();
    JSONObject errJsonObj = app.getErrorJSONObject("Test Error Info");
    assertEquals(errJsonObj.get("error"), "Test Error Info");
  }

  @Test
  @Ignore
  public void getSearchResultJSONObjectTest() {
    String jsonStr = "{\n" +
            "  \"took\" : 4,\n" +
            "  \"timed_out\" : false,\n" +
            "  \"_shards\" : {\n" +
            "    \"total\" : 1,\n" +
            "    \"successful\" : 1,\n" +
            "    \"skipped\" : 0,\n" +
            "    \"failed\" : 0\n" +
            "  },\n" +
            "  \"hits\" : {\n" +
            "    \"total\" : 1,\n" +
            "    \"max_score\" : 8.404801,\n" +
            "    \"hits\" : [\n" +
            "      {\n" +
            "        \"_index\" : \"test_json_index\",\n" +
            "        \"_type\" : \"_doc\",\n" +
            "        \"_id\" : \"4\",\n" +
            "        \"_score\" : 8.404801,\n" +
            "        \"_source\" : {\n" +
            "          \"ACK_ID\" : \"20180108193032P030007418141001\",\n" +
            "          \"FORM_PLAN_YEAR_BEGIN_DATE\" : \"2017-01-05\",\n" +
            "          \"FORM_TAX_PRD\" : \"2017-07-31\",\n" +
            "          \"TYPE_PLAN_ENTITY_CD\" : \"2\",\n" +
            "          \"TYPE_DFE_PLAN_ENTITY_CD\" : \"\",\n" +
            "          \"INITIAL_FILING_IND\" : \"1\",\n" +
            "          \"AMENDED_IND\" : \"\",\n" +
            "          \"FINAL_FILING_IND\" : \"1\",\n" +
            "          \"SHORT_PLAN_YR_IND\" : \"1\",\n" +
            "          \"COLLECTIVE_BARGAIN_IND\" : \"\",\n" +
            "          \"F5558_APPLICATION_FILED_IND\" : \"\",\n" +
            "          \"EXT_AUTOMATIC_IND\" : \"\",\n" +
            "          \"DFVC_PROGRAM_IND\" : \"\",\n" +
            "          \"EXT_SPECIAL_IND\" : \"\",\n" +
            "          \"EXT_SPECIAL_TEXT\" : \"\",\n" +
            "          \"PLAN_NAME\" : \"STEVEN KING ENTERPRISES, INC. RETIREMENT PLAN\",\n" +
            "          \"SPONS_DFE_PN\" : \"001\",\n" +
            "          \"PLAN_EFF_DATE\" : \"2017-01-05\",\n" +
            "          \"SPONSOR_DFE_NAME\" : \"STEVEN KING ENTERPRISES, INC.\",\n" +
            "          \"SPONS_DFE_DBA_NAME\" : \"\",\n" +
            "          \"SPONS_DFE_CARE_OF_NAME\" : \"\",\n" +
            "          \"SPONS_DFE_MAIL_US_ADDRESS1\" : \"4117 VICTORIA DRIVE\",\n" +
            "          \"SPONS_DFE_MAIL_US_ADDRESS2\" : \"\",\n" +
            "          \"SPONS_DFE_MAIL_US_CITY\" : \"HOFFMAN ESTATES\",\n" +
            "          \"SPONS_DFE_MAIL_US_STATE\" : \"IL\",\n" +
            "          \"SPONS_DFE_MAIL_US_ZIP\" : \"60192\",\n" +
            "          \"SPONS_DFE_MAIL_FOREIGN_ADDR1\" : \"\",\n" +
            "          \"SPONS_DFE_MAIL_FOREIGN_ADDR2\" : \"\",\n" +
            "          \"SPONS_DFE_MAIL_FOREIGN_CITY\" : \"\",\n" +
            "          \"SPONS_DFE_MAIL_FORGN_PROV_ST\" : \"\",\n" +
            "          \"SPONS_DFE_MAIL_FOREIGN_CNTRY\" : \"\",\n" +
            "          \"SPONS_DFE_MAIL_FORGN_POSTAL_CD\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_US_ADDRESS1\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_US_ADDRESS2\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_US_CITY\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_US_STATE\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_US_ZIP\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_FOREIGN_ADDRESS1\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_FOREIGN_ADDRESS2\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_FOREIGN_CITY\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_FORGN_PROV_ST\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_FOREIGN_CNTRY\" : \"\",\n" +
            "          \"SPONS_DFE_LOC_FORGN_POSTAL_CD\" : \"\",\n" +
            "          \"SPONS_DFE_EIN\" : \"814876480\",\n" +
            "          \"SPONS_DFE_PHONE_NUM\" : \"8474142919\",\n" +
            "          \"BUSINESS_CODE\" : \"722511\",\n" +
            "          \"ADMIN_NAME\" : \"\",\n" +
            "          \"ADMIN_CARE_OF_NAME\" : \"\",\n" +
            "          \"ADMIN_US_ADDRESS1\" : \"\",\n" +
            "          \"ADMIN_US_ADDRESS2\" : \"\",\n" +
            "          \"ADMIN_US_CITY\" : \"\",\n" +
            "          \"ADMIN_US_STATE\" : \"\",\n" +
            "          \"ADMIN_US_ZIP\" : \"\",\n" +
            "          \"ADMIN_FOREIGN_ADDRESS1\" : \"\",\n" +
            "          \"ADMIN_FOREIGN_ADDRESS2\" : \"\",\n" +
            "          \"ADMIN_FOREIGN_CITY\" : \"\",\n" +
            "          \"ADMIN_FOREIGN_PROV_STATE\" : \"\",\n" +
            "          \"ADMIN_FOREIGN_CNTRY\" : \"\",\n" +
            "          \"ADMIN_FOREIGN_POSTAL_CD\" : \"\",\n" +
            "          \"ADMIN_EIN\" : \"\",\n" +
            "          \"ADMIN_PHONE_NUM\" : \"\",\n" +
            "          \"LAST_RPT_SPONS_NAME\" : \"\",\n" +
            "          \"LAST_RPT_SPONS_EIN\" : \"\",\n" +
            "          \"LAST_RPT_PLAN_NUM\" : \"\",\n" +
            "          \"ADMIN_SIGNED_DATE\" : \"2018-01-08T19:28:53-0600\",\n" +
            "          \"ADMIN_SIGNED_NAME\" : \"STEVEN M. KING\",\n" +
            "          \"SPONS_SIGNED_DATE\" : \"\",\n" +
            "          \"SPONS_SIGNED_NAME\" : \"\",\n" +
            "          \"DFE_SIGNED_DATE\" : \"\",\n" +
            "          \"DFE_SIGNED_NAME\" : \"\",\n" +
            "          \"TOT_PARTCP_BOY_CNT\" : \"1\",\n" +
            "          \"TOT_ACTIVE_PARTCP_CNT\" : \"0\",\n" +
            "          \"RTD_SEP_PARTCP_RCVG_CNT\" : \"0\",\n" +
            "          \"RTD_SEP_PARTCP_FUT_CNT\" : \"0\",\n" +
            "          \"SUBTL_ACT_RTD_SEP_CNT\" : \"0\",\n" +
            "          \"BENEF_RCVG_BNFT_CNT\" : \"0\",\n" +
            "          \"TOT_ACT_RTD_SEP_BENEF_CNT\" : \"0\",\n" +
            "          \"PARTCP_ACCOUNT_BAL_CNT\" : \"0\",\n" +
            "          \"SEP_PARTCP_PARTL_VSTD_CNT\" : \"0\",\n" +
            "          \"CONTRIB_EMPLRS_CNT\" : \"\",\n" +
            "          \"TYPE_PENSION_BNFT_CODE\" : \"2E2G2R3D\",\n" +
            "          \"TYPE_WELFARE_BNFT_CODE\" : \"\",\n" +
            "          \"FUNDING_INSURANCE_IND\" : \"\",\n" +
            "          \"FUNDING_SEC412_IND\" : \"\",\n" +
            "          \"FUNDING_TRUST_IND\" : \"1\",\n" +
            "          \"FUNDING_GEN_ASSET_IND\" : \"\",\n" +
            "          \"BENEFIT_INSURANCE_IND\" : \"\",\n" +
            "          \"BENEFIT_SEC412_IND\" : \"\",\n" +
            "          \"BENEFIT_TRUST_IND\" : \"1\",\n" +
            "          \"BENEFIT_GEN_ASSET_IND\" : \"\",\n" +
            "          \"SCH_R_ATTACHED_IND\" : \"1\",\n" +
            "          \"SCH_MB_ATTACHED_IND\" : \"\",\n" +
            "          \"SCH_SB_ATTACHED_IND\" : \"\",\n" +
            "          \"SCH_H_ATTACHED_IND\" : \"\",\n" +
            "          \"SCH_I_ATTACHED_IND\" : \"1\",\n" +
            "          \"SCH_A_ATTACHED_IND\" : \"\",\n" +
            "          \"NUM_SCH_A_ATTACHED_CNT\" : \"\",\n" +
            "          \"SCH_C_ATTACHED_IND\" : \"\",\n" +
            "          \"SCH_D_ATTACHED_IND\" : \"\",\n" +
            "          \"SCH_G_ATTACHED_IND\" : \"\",\n" +
            "          \"FILING_STATUS\" : \"FILING_RECEIVED\",\n" +
            "          \"DATE_RECEIVED\" : \"2018-01-08\",\n" +
            "          \"VALID_ADMIN_SIGNATURE\" : \"Filed with authorized/valid electronic signature\",\n" +
            "          \"VALID_DFE_SIGNATURE\" : \"\",\n" +
            "          \"VALID_SPONSOR_SIGNATURE\" : \"\",\n" +
            "          \"ADMIN_PHONE_NUM_FOREIGN\" : \"\",\n" +
            "          \"SPONS_DFE_PHONE_NUM_FOREIGN\" : \"\",\n" +
            "          \"ADMIN_NAME_SAME_SPON_IND\" : \"1\",\n" +
            "          \"ADMIN_ADDRESS_SAME_SPON_IND\" : \"\",\n" +
            "          \"PREPARER_NAME\" : \"\",\n" +
            "          \"PREPARER_FIRM_NAME\" : \"\",\n" +
            "          \"PREPARER_US_ADDRESS1\" : \"\",\n" +
            "          \"PREPARER_US_ADDRESS2\" : \"\",\n" +
            "          \"PREPARER_US_CITY\" : \"\",\n" +
            "          \"PREPARER_US_STATE\" : \"\",\n" +
            "          \"PREPARER_US_ZIP\" : \"\",\n" +
            "          \"PREPARER_FOREIGN_ADDRESS1\" : \"\",\n" +
            "          \"PREPARER_FOREIGN_ADDRESS2\" : \"\",\n" +
            "          \"PREPARER_FOREIGN_CITY\" : \"\",\n" +
            "          \"PREPARER_FOREIGN_PROV_STATE\" : \"\",\n" +
            "          \"PREPARER_FOREIGN_CNTRY\" : \"\",\n" +
            "          \"PREPARER_FOREIGN_POSTAL_CD\" : \"\",\n" +
            "          \"PREPARER_PHONE_NUM\" : \"\",\n" +
            "          \"PREPARER_PHONE_NUM_FOREIGN\" : \"\",\n" +
            "          \"TOT_ACT_PARTCP_BOY_CNT\" : \"1\",\n" +
            "          \"SUBJ_M1_FILING_REQ_IND\" : \"\",\n" +
            "          \"COMPLIANCE_M1_FILING_REQ_IND\" : \"\",\n" +
            "          \"M1_RECEIPT_CONFIRMATION_CODE\" : \"\",\n" +
            "          \"ADMIN_MANUAL_SIGNED_DATE\" : \"\",\n" +
            "          \"ADMIN_MANUAL_SIGNED_NAME\" : \"\",\n" +
            "          \"LAST_RPT_PLAN_NAME\" : \"\",\n" +
            "          \"SPONS_MANUAL_SIGNED_DATE\" : \"\",\n" +
            "          \"SPONS_MANUAL_SIGNED_NAME\" : \"\",\n" +
            "          \"DFE_MANUAL_SIGNED_DATE\" : \"\",\n" +
            "          \"DFE_MANUAL_SIGNED_NAME\" : \"\"\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    App app = new App();
    JSONObject res = app.getSearchResultJSONObject(jsonStr);
    JSONArray resArr = (JSONArray) res.get("res");
    JSONObject resFirstRecord = (JSONObject) resArr.get(0);
    assertEquals(resFirstRecord.get("ACK_ID"), "20180108193032P030007418141001");
    assertEquals(resFirstRecord.get("PLAN_NAME"), "STEVEN KING ENTERPRISES, INC. RETIREMENT PLAN");
  }

  @Test(expected = IOException.class)
  public void getPageContentsTest() throws IOException {
    App app = new App();
    String response = app.getPageContents("");
  }
}
