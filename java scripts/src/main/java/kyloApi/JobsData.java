package kyloApi;

import java.util.List;
import java.util.Map;

/**
 * JobsData objects are POJO outputs when retrofit parses response.
 * From endpoint "/proxy/v1/jobs/running",
 * "/proxy/v1/jobs/failed"
 * "/proxy/v1/feedmgr/feeds"
 */
public class JobsData {
    List<Map<String,Object>> data;
    int recordsTotal;
}
