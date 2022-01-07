import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FindUrls {

    public static void main(String[] args) throws IOException {
        String text = "Adaptive and https://botirbexr.uz  responsive can be viewed https://pdp.uz (https://pdp.uz/) as separate dimensions of an app: you can have an adaptive app that is not responsive, or vice versa. And, of course, https://www.youtube.com (https://www.youtube.com/) an app can be #both, or neither.https://youtu.be/HPUJsn8MUNU";
        String text2 = "Adaptive and #responsive can be viewed as #separate dimensions of an app: you can have an adaptive app that is not #responsive, or vice versa. And, of course, an app can be #both, or neither.";
        System.out.println(extractUrls(text));
        System.out.println(extractHashtags(text2));
    }

    public static List<String> extractUrls(String input) throws IOException {
        List<String> result = new ArrayList<String>();
        List<String> finalResult = new ArrayList<String>();
        Pattern pattern = Pattern.compile(
                "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                        "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
                        "|mil|biz|info|mobi|name|aero|jobs|museum" +
                        "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                        "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                        "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                        "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                        "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            result.add(matcher.group());


        }
        for (int i = 0; i < result.size(); i++) {

            String urlString = result.get(i);

            URL u = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            try {
                huc.connect();
                if (huc.getResponseCode() != 404) {
                    finalResult.add(result.get(i));
                }
            } catch (Exception exception) {

            }


        }

        return finalResult;
    }

    public static List<String> extractHashtags(String input) {
        List<String> result = new ArrayList<String>();
        String regexPattern = "(#\\w+)";
        Pattern p = Pattern.compile(regexPattern);
        Matcher m = p.matcher(input);
        while (m.find()) {
            String hashtag = m.group(1);
            result.add(hashtag);
        }
        return result;
    }
}