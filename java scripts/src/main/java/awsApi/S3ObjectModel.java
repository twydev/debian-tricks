package awsApi;

import java.util.*;

public class S3ObjectModel {
    public static final String SOURCE_BUCKET = "aws-s3-source-bucket";
    public static final String DESTINATION_BUCKET = "aws-s3-destination-bucket";

    public static final Map<String, String> regexFolderMap;
    static {
        Map<String,String> holder = new HashMap<>();
        holder.put("source-folder","destination-folder/");
        regexFolderMap = Collections.unmodifiableMap(holder);
    }

    public static String getFolder(String objectKey) throws NoSuchElementException {
        for (String regexMatch : regexFolderMap.keySet()) {
            if (objectKey.startsWith(regexMatch)) {
                return regexFolderMap.get(regexMatch);
            }
        }
        throw new NoSuchElementException(objectKey);
    }
}
