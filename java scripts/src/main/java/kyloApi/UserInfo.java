package kyloApi;

import java.util.List;

/**
 * UserInfo objects are POJO outputs when retrofit parses response.
 * From endpoint "/proxy/v1/about/me"
 */
public class UserInfo {
    private boolean enabled;
    private List<String> groups;
    private String systemName;

    @Override
    public String toString() {
        return "\nenabled : " + enabled + "\ngroups : " + groups + "\nsystemName : " + systemName;
    }
}
