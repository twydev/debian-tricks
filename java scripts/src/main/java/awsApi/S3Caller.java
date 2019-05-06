package awsApi;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import kyloApi.Credentials;
import kyloApi.FeedIdHighwatermarkCaller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Use AWS S3 API to archive and manage objects
 * Referenced from https://www.baeldung.com/aws-s3-java
 */
public class S3Caller {
    public static final boolean DEBUG = true;

    public static  Map<String, Integer> itemCounter = new HashMap<>();

    public static void increaseCount(String folder) {
        if (itemCounter.containsKey(folder)) {
            int currentCount = itemCounter.get(folder);
            itemCounter.put(folder,currentCount+1);
        } else {
            itemCounter.put(folder,1);
        }
    }

    public static void printCount() {
        int totalCount = 0;
        System.out.println("\n-------- Archived Items Count --------");
        for (String folder : itemCounter.keySet()) {
            int currentCount = itemCounter.get(folder);
            System.out.println(String.format("%-30s : %-30s", folder, currentCount));
            totalCount += currentCount;
        }
        if (totalCount == 0) {
            System.out.println("  NO VALID FILES TO BE ARCHIVED!");
        }
        System.out.println("--------------------------------------");
    }

    public static void main(String[] args) {
        AWSCredentials credentials = new BasicAWSCredentials(
                Credentials.s3accesskey,
                Credentials.s3secretkey
        );

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();

        FeedIdHighwatermarkCaller.makeHighWaterMarkCall();

        System.out.println("\n-------- ARCHIVING --------");
        Date cutOffDate = getCutOffDate();
        System.out.println("Cut-off Date: " + cutOffDate);
        System.out.println("---------------------------");

        List<String> activeFiles = new ArrayList<>();

        ObjectListing objectListing = s3client.listObjects(S3ObjectModel.SOURCE_BUCKET);
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            String objectKey = os.getKey();
            Date objectLastModified = os.getLastModified();
            if (objectLastModified.before(cutOffDate)) {
                String archiveFolder = S3ObjectModel.getFolder(objectKey);
//                s3client.copyObject(S3ObjectModel.SOURCE_BUCKET,objectKey,S3ObjectModel.DESTINATION_BUCKET,archiveFolder + objectKey);
//                s3client.deleteObject(S3ObjectModel.SOURCE_BUCKET,objectKey);
                if (DEBUG) {
                    System.out.println("MOVED: " + objectLastModified + " | " + objectKey);
                }
                increaseCount(archiveFolder);
            } else {
                activeFiles.add(objectLastModified + " | " + objectKey);
            }
        }

        printCount();
        System.out.println("\n-------- Remaining Files in Source Bucket : " + activeFiles.size() + "--------");
        if (DEBUG) {
            printList(activeFiles);
        }
    }

    public static Date getCutOffDate() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -1);
        Date targetDate = cal.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String targetDateFormatted = dateFormat.format(targetDate);

        Date cutoffDate = null;
        try {
            cutoffDate = dateFormat.parse(targetDateFormatted);
            return cutoffDate;
        } catch (ParseException e) {
            return null;
        }
    }

    public static void printList(List<? extends Object> listObjs) {
        for (Object obj : listObjs) {
            System.out.println(obj.toString());
        }
    }
}
