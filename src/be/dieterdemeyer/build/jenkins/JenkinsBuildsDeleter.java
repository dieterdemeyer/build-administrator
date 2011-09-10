package be.dieterdemeyer.build.jenkins;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JenkinsBuildsDeleter {

    private String jenkinsServer;
    private String operation = "doDelete";

    private JenkinsBuildsDeleter(String jenkinsServer, String operation) {
        this.jenkinsServer = jenkinsServer;
        this.operation = operation;
    }

    public static void main(String args[]) throws Exception {
        JenkinsBuildsDeleter deleter = new JenkinsBuildsDeleter("localhost", "doDelete");

        Map<String, Range> jobMap = new HashMap<String, Range>();

        /*
        * Key: Name of Jenkins Job
        * Value: Range of build numbers
        */
        jobMap.put("<insert job name>", new Range(25, 55));

        deleter.performOperationOnBuilds(jobMap);
    }

    private void performOperationOnBuilds(Map<String, Range> jobs) {
        for (Map.Entry<String, Range> entry : jobs.entrySet()) {
            for (Integer number : entry.getValue()) {
                performOperationOn(entry.getKey(), number.toString());
            }
        }
    }

    private void performOperationOn(String jobName, String buildNumber) {
        try {
            HttpResponse response = post("http://" + jenkinsServer + "/job/" + jobName + "/" + buildNumber + "/" + operation);
            System.out.println("Performed " + operation + " on " + jobName + " build " + buildNumber + " response: " + response.getStatusLine());
        } catch (Exception ex) {
            System.out.println("Exception while doing " + operation + " on " + jobName + " build " + buildNumber);
            ex.printStackTrace();
        }
    }

    private HttpResponse post(String url) throws IOException {
        return new DefaultHttpClient().execute(new HttpPost(url));
    }

    private static class Range implements Iterable<Integer> {

        private final int startingValue;
        private final int endingValue;

        public Range(int startingValue, int endingValue) {
            this.startingValue = startingValue;
            this.endingValue = endingValue;
        }

        public Iterator<Integer> iterator() {
            return new RangeIterator(startingValue, endingValue);
        }
    }

    private static class RangeIterator implements Iterator<Integer> {

        private final int endingValue;
        private int next;

        private RangeIterator(int startingValue, int endingValue) {
            if (startingValue > endingValue) {
                throw new IllegalArgumentException("startingValue should not be greater then endingValue");
            }
            this.endingValue = endingValue;
            next = startingValue;
        }

        public boolean hasNext() {
            return next <= endingValue;
        }

        public Integer next() {
            int retValue = next;
            next++;
            return retValue;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}