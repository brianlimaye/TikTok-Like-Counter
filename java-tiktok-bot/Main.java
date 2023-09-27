import static com.teamdev.jxbrowser.engine.RenderingMode.*;
import com.teamdev.jxbrowser.*;

//import com.teamdev.jxbrowser.browser.Browser;
//import com.teamdev.jxbrowser.engine.Engine;
//import com.teamdev.jxbrowser.engine.EngineOptions;
//import com.teamdev.jxbrowser.view.swing.BrowserView;

import java.net.http.*;
import javax.net.ssl.*;
//import java.net.http.HttpResponse.BodyHandlers

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


 
import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Paths;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.swing.BrowserView;
 
import java.time.Duration;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    private static HashMap<String, String> cookieMap = new HashMap<>();
    private static String csrf_session_id;
    private static String xware_csrf;
    private long lastCID = 0l;

    public static void sleep(int durationSec) {

        try
        {
            Thread.sleep(durationSec * 1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static String getVideoPage(final HttpClient httpClient) throws IOException, InterruptedException {

        String sid_guard = cookieMap.get("sid_guard");

        StringBuilder cookieSeq = new StringBuilder();
        cookieSeq.append("sid_guard");
        cookieSeq.append("=");
        cookieSeq.append(sid_guard);

        final HttpRequest request = HttpRequest.newBuilder()
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .setHeader("Cookie", cookieSeq.toString())
                .setHeader("X-Secsdk-Csrf-Version", "1.2.7")
                .setHeader("X-Secsdk-Csrf-Request", "1")
                .setHeader("Accept", "*/*")
                .setHeader("Sec-Fetch-Site", "same-origin")
                .setHeader("Sec-Fetch-Mode", "cors")
                .setHeader("Sec-Fetch-Dest", "empty")
                .setHeader("Referer", "https://www.tiktok.com/@brianlimaye/video/6925498885620206854?is_copy_url=1&is_from_webapp=v1") 
                .setHeader("Accept-Language", "en-US,en;q=0.9")
                .uri(URI.create("https://www.tiktok.com/api/comment/publish/"))
                .build();

        //System.out.println(request.headers());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, List<String>> headerMap = response.headers().map();

        //System.out.println(headerMap.keySet());


        //System.out.println("Map " + headerMap);

        String unparsedCsrf = headerMap.get("set-cookie").get(1);

        int startIndex = unparsedCsrf.indexOf("=") + 1;
        int endIndex = unparsedCsrf.indexOf(";");

        csrf_session_id = unparsedCsrf.substring(startIndex, endIndex);



        String xware = headerMap.get("x-ware-csrf-token").get(0);

        xware = xware.substring(xware.indexOf(",") + 1);

        return xware.substring(0, xware.indexOf(","));
    }

    public static String constructReplyBody(long cid, String msg) {

        StringBuilder body = new StringBuilder();

        body.append("aid=1988&");
        body.append("app_language=en&");
        body.append("app_name=tiktok_web&");
        body.append("aweme_id=6925498885620206854&");
        body.append("browser_language=en-US&");
        body.append("browser_name=Mozilla&");
        body.append("browser_online=true&");
        body.append("browser_platform=MacIntel&");
        body.append("browser_version=5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F115.0.0.0%20Safari%2F537.36&");
        body.append("channel=tiktok_web&");
        body.append("cookie_enabled=true&");
        body.append("device_id=7258843179784324616&");
        body.append("device_platform=web_pc&");
        body.append("focus_state=true&");
        body.append("from_page=video&");
        body.append("history_len=4&");
        body.append("is_fullscreen=false&");
        body.append("is_page_visible=true&");
        body.append("os=mac&");
        body.append("priority_region=US&");
        body.append("referer=&");
        body.append("region=US&");
        body.append("reply_id=" + cid + "&");
        body.append("reply_to_reply_id=0&");
        body.append("screen_height=900&");
        body.append("screen_width=1440&");

        //To-Do: Determine what to reply.
        body.append("text=");
        body.append(msg);
        body.append("&");

        body.append("text_extra=%5B%5D&");
        body.append("tz_name=America%2FNew_York&");
        body.append("webcast_language=en");

        return body.toString();
    }
    public static String constructPostBody(String msg) {

        StringBuilder body = new StringBuilder();

        body.append("aid=1988&");
        body.append("app_language=en&");
        body.append("app_name=tiktok_web&");
        body.append("aweme_id=6925498885620206854&");
        body.append("battery_info=1&");
        body.append("browser_language=en-US&");
        body.append("browser_name=Mozilla&");
        body.append("browser_online=true&");
        body.append("browser_platform=MacIntel&");
        body.append("browser_version=5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F97.0.4692.71%20Safari%2F537.36&");
        body.append("channel=tiktok_web&");
        body.append("cookie_enabled=true&");
        body.append("device_id=7036149434538952197&");
        body.append("device_platform=web_pc&");
        body.append("focus_state=true&");
        body.append("from_page=video&");
        body.append("history_len=5&");
        body.append("is_fullscreen=false&");
        body.append("is_page_visible=true&");
        body.append("os=mac&");
        body.append("priority_region=US&");
        body.append("referer=&");
        body.append("region=US&");
        body.append("screen_height=900&");
        body.append("screen_width=1440&");
        body.append("text=");
        body.append(msg);
        body.append("&");
        body.append("text_extra=%5B%5D&");
        body.append("tz_name=America%2FNew_York&");
        body.append("webcast_language=en");

        return body.toString();
    }

    public static String getAllComments(final HttpClient httpClient) throws IOException, InterruptedException {

        String sid_guard = cookieMap.get("sid_guard");
        URI uri = null;


        String baseUrl = "https://www.tiktok.com/api/comment/list/";
        List<String> parameters = new ArrayList<>();

        StringBuilder cookieSeq = new StringBuilder();

        parameters.add("aid=1988");
        parameters.add("app_language=ja-JP");
        parameters.add("app_name=tiktok_web");
        parameters.add("aweme_id=6925498885620206854");
        parameters.add("browser_language=en-US");
        parameters.add("browser_name=Mozilla");
        parameters.add("browser_online=true");
        parameters.add("browser_platform=MacIntel");
        parameters.add("browser_version=5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F115.0.0.0%20Safari%2F537.36");
        parameters.add("channel=tiktok_web");
        parameters.add("cookie_enabled=true");
        parameters.add("count=20");
        parameters.add("current_region=JP");
        parameters.add("cursor=0");
        parameters.add("device_id=7258843179784324616");
        parameters.add("device_platform=web_pc");
        parameters.add("enter_from=tiktok_web");
        parameters.add("focus_state=true");
        parameters.add("fromWeb=1");
        parameters.add("from_page=video");
        parameters.add("history_len=6");
        parameters.add("is_fullscreen=false");
        parameters.add("is_page_visible=true");
        parameters.add("os=mac");
        parameters.add("priority_region=US");
        parameters.add("referer=");
        parameters.add("region=US");
        parameters.add("screen_height=900");
        parameters.add("screen_width=1440");
        parameters.add("tz_name=America%2FNew_York");
        parameters.add("webcast_language=en");
        parameters.add("msToken=dWTwBPcYrIbpkz1crRo9LomMr3LRCo6QzZW5u_qdfM1wGwrTR6zdwhTbOVS-l0X44eTeR5ZAjZK5Ox3RUlmcPEXk-VHOeLLxTJ0Qe92K2dJe69il03g7yMS0Ow_XBr6Kn2nYAVM=");
        parameters.add("X-Bogus=DFSzxwVOggxANa05t9I75r7TlqCd");
        parameters.add("_signature=_02B4Z6wo00001LLmnmQAAIDDJjDFasdT8VCy5prAAEhtec");

        // Combine parameters into query string
        String queryString = String.join("&", parameters);

        //System.out.println(queryString);

        // Create the URI

        try {
            uri = new URI(baseUrl + "?" + queryString);
        }
        catch(URISyntaxException use) {
        }

        //System.out.println("URI: " + uri);

        
        cookieSeq.append("sid_guard=");
        cookieSeq.append(sid_guard);
        cookieSeq.append("; ");
        cookieSeq.append("sessionid=");
        cookieSeq.append(sid_guard.substring(0, sid_guard.indexOf("%")));
        cookieSeq.append("; ");

        //System.out.println("Cookie: " + cookieSeq.toString());

        final HttpRequest commentRequest = HttpRequest.newBuilder()
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .uri(uri)
            .setHeader("Cookie", cookieSeq.toString())
            .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
            .setHeader("Sec-Ch-Ua-Platform", "\"macOS\"")
            .setHeader("Accept", "*/*")
            .setHeader("Sec-Fetch-Site", "same-origin")
            .setHeader("Sec-Fetch-Mode", "cors")
            .setHeader("Sec-Fetch-Dest", "empty")
            .setHeader("Referer", "https://www.tiktok.com/@brianlimaye/video/6925498885620206854")
            .setHeader("Accept-Language", "en-US,en;q=0.9")
            .build();

        HttpResponse<String> response = httpClient.send(commentRequest, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());

        return response.body();
    }

    public static String constructDeleteBody(String cid) {

        StringBuilder body = new StringBuilder();

        body.append("aid=1988&");
        body.append("app_language=en&");
        body.append("app_name=tiktok_web&");
        body.append("battery_info=1&");
        body.append("browser_language=en-US&");
        body.append("browser_name=Mozilla&");
        body.append("browser_online=true&");
        body.append("browser_platform=MacIntel&");
        body.append("browser_version=5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F97.0.4692.99%20Safari%2F537.36&");
        body.append("channel=tiktok_web&");
        body.append("cid=");
        body.append(cid);
        body.append("&");
        body.append("cookie_enabled=true&");
        body.append("device_id=7036149434538952197&");
        body.append("device_platform=web_pc&");
        body.append("focus_state=true&");
        body.append("from_page=video&");
        body.append("history_len=10&");
        body.append("is_fullscreen=false&");
        body.append("is_page_visible=true&");
        body.append("os=mac&");
        body.append("priority_region=US&");
        body.append("referer=&");
        body.append("region=US&");
        body.append("screen_height=900&");
        body.append("screen_width=1440&");
        body.append("tz_name=America%2FNew_York&");
        body.append("webcast_language=en");

        return body.toString();
    }

    public static int getNumLikes(final HttpClient httpClient) throws IOException, InterruptedException {

        int numLikes = 0;
        String sid_guard = cookieMap.get("sid_guard");

        StringBuilder cookieSeq = new StringBuilder();
        cookieSeq.append("csrf_session_id=");
        cookieSeq.append(csrf_session_id);
        cookieSeq.append("; ");
        cookieSeq.append("sid_guard");
        cookieSeq.append("=");
        cookieSeq.append(sid_guard);

        final HttpRequest request = HttpRequest.newBuilder()
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .setHeader("Cookie", cookieSeq.toString())
            .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36")
            .setHeader("Sec-Ch-Ua-Platform", "\"macOS\"")
            .setHeader("Accept", "*/*")
            .setHeader("Sec-Fetch-Site", "same-origin")
            .setHeader("Sec-Fetch-Mode", "no-cors")
            .setHeader("Sec-Fetch-Dest", "empty")
            .setHeader("Referer", "https://www.tiktok.com/@brianlimaye")
            .setHeader("Accept-Language", "en-US,en;q=0.9")
            .uri(URI.create("https://www.tiktok.com/@brianlimaye/video/6925498885620206854"))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String unparsedLikeCount = response.body();

        unparsedLikeCount = unparsedLikeCount.substring(unparsedLikeCount.indexOf("diggCount"));
        
        unparsedLikeCount = unparsedLikeCount.substring(unparsedLikeCount.indexOf(":"));

        
        try {
            numLikes = Integer.parseInt(unparsedLikeCount.substring(1, unparsedLikeCount.indexOf(",")));
        }
        catch(NumberFormatException nfe) {

        }

        return numLikes;
    }

    public static String parseCID(final String body) {

        int cidIndex = body.indexOf("cid");
        String cidStr = body.substring(cidIndex);
        cidStr = cidStr.substring(cidStr.indexOf(":") + 2);

        return cidStr.substring(0, cidStr.indexOf("\""));
    }

    public static boolean postReply(final HttpClient httpClient, long cid, String msg) throws IOException, InterruptedException {

        String postBody = constructReplyBody(cid, msg);

        String sid_guard = cookieMap.get("sid_guard"); 
        String sessionID = cookieMap.get("sessionid");

        StringBuilder cookieSeq = new StringBuilder();
        cookieSeq.append("sid_guard");
        cookieSeq.append("=");
        cookieSeq.append(sid_guard);
        cookieSeq.append("; ");
        cookieSeq.append("sessionid=");
        cookieSeq.append(sessionID);

        //System.out.println("cookieSeq: " + cookieSeq.toString());
        //System.out.println("xware_csrf: " + xware_csrf);

        final HttpRequest request = HttpRequest.newBuilder()
                .method("POST", HttpRequest.BodyPublishers.ofString(postBody))
                .setHeader("Cookie", cookieSeq.toString())
                .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .setHeader("Sec-Ch-Ua-Platform", "\"macOS\"")
                .setHeader("Accept", "*/*")
                .setHeader("Sec-Fetch-Site", "same-origin")
                .setHeader("Sec-Fetch-Mode", "cors")
                .setHeader("Sec-Fetch-Dest", "empty")
                .setHeader("Referer", "https://www.tiktok.com/@brianlimaye/video/6925498885620206854") 
                .setHeader("Accept-Language", "en-US,en;q=0.9")
                .uri(URI.create("https://www.tiktok.com/api/comment/publish/"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());

        if(response.statusCode() != 200) {
            return false;
        }

        return response.body().length() > 0;
    } 

    
    public static String deleteComment(final HttpClient httpClient, String cid) throws IOException, InterruptedException {

        String deleteBody = constructDeleteBody(cid);

        String sid_guard = cookieMap.get("sid_guard");
        String sessionID = cookieMap.get("sessionid");

        StringBuilder cookieSeq = new StringBuilder();
        cookieSeq.append("sessionid");
        cookieSeq.append("=");
        cookieSeq.append(sessionID);
        cookieSeq.append("; ");
        cookieSeq.append("sid_guard");
        cookieSeq.append("=");
        cookieSeq.append(sid_guard);

        
        final HttpRequest deleteRequest = HttpRequest.newBuilder()
                .method("POST", HttpRequest.BodyPublishers.ofString(deleteBody))
                .setHeader("Cookie", cookieSeq.toString())
                .setHeader("X-Secsdk-Csrf-Token", "DOWNGRADE")
                //.setHeader("Tt-Csrf-Token", "8S7oys_PqOjC-haeE3tlUFjk")
                .setHeader("Accept", "*/*")
                .setHeader("Referer", "https://www.tiktok.com/")
                .setHeader("Accept-Language", "en-US,en;q=0.9")
                .uri(URI.create("https://www.tiktok.com/api/comment/delete/"))
                .build();

        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(deleteResponse.headers().map());

        System.out.println("Delete: " + deleteResponse.statusCode());

        return null;
    }

    public static String reverseStr(String msg) {

        StringBuilder sb = new StringBuilder();

        for(int i = msg.length() - 1; i >= 0; i--) {
            sb.append(msg.charAt(i));
        }

        return sb.toString();
    }

    public static String generateRandomEmoji() {

        int thirdByte = 0;

        int faceFlag = (int)(Math.random() * 2);

        StringBuilder emojiBuilder = new StringBuilder();
        emojiBuilder.append("%F0%9F%");

        int fourthByte = (int)(Math.random() * 64) + 128;

        if(faceFlag == 0) {
            thirdByte = (int)(Math.random() * 5) + 144;
        }
        else {
            thirdByte = 152;
        }

        emojiBuilder.append(Integer.toHexString(thirdByte));
        emojiBuilder.append("%");
        emojiBuilder.append(Integer.toHexString(fourthByte));

        return emojiBuilder.toString();
    }

    public static Comment replyToUnread(final HttpClient httpClient, String commentBody) throws IOException, InterruptedException {

        //System.out.println(commentBody);

        JSONArray allComments = new JSONArray(commentBody.substring(commentBody.indexOf('[')));

        //boolean needsInitial = (lastCID == 0) ? true : false;
        long currCid = 0l;
        int currReplied = 0;

        try {
            for(int i = 0; i < allComments.length(); i++) {

                JSONObject currComment = allComments.getJSONObject(i);
                currCid = (long) currComment.getLong("cid");
                currReplied = currComment.getInt("reply_comment_total");

                if(currReplied == 0) {   
                    
                    String utfEmoji = generateRandomEmoji();
                    postReply(httpClient, currCid, utfEmoji);
                    Thread.sleep(50000);
                }
                
            }
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
        
        return null;
    }

    public static void main(String[] args) {

        String jxBrowserKey = ""; //Put JxBrowserKey here. Original Key was taken out for security purposes. Program works as normally with the key used.

        System.setProperty("jxbrowser.license.key", jxBrowserKey);

        int i = 0;
        int numLikes = 0;
        String updatedMsg = "";
        String cid = "";
        Engine engine = Engine.newInstance(
          EngineOptions.newBuilder(HARDWARE_ACCELERATED).userDataDir(Paths.get("./data")).build());

        Browser browser = engine.newBrowser();

        SwingUtilities.invokeLater(() -> {
            BrowserView view = BrowserView.newInstance(browser);

            JFrame frame = new JFrame("Swing BrowserView");

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(view, BorderLayout.CENTER);
            frame.setSize(700,500);
            frame.setVisible(true);

            browser.navigation().loadUrl("https://tiktok.com");
        });

        com.teamdev.jxbrowser.cookie.CookieStore cookieStore = browser.profile().cookieStore();

        cookieStore.cookies().forEach(cookie -> 
        cookieMap.put(cookie.name(), cookie.value()));

        //System.out.println(cookieMap);

        // one instance, reuse
        
        
        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                //.cookieHandler(CookieHandler.getDefault())
                .connectTimeout(Duration.ofSeconds(30))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        loop:
        while(true) {

            try {

               xware_csrf = getVideoPage(httpClient);
               numLikes = getNumLikes(httpClient);
               System.out.println("This Video has " + numLikes + " Likes!");
               updatedMsg = "This%20Video%20has%20" + numLikes + "%20Likes!"; 
               
               //String commentBody = getAllComments(httpClient);

               
               if((commentBody == null) || (commentBody.length() == 0)) {
                
                    System.out.println("Comment body was empty...");
                    Thread.sleep(5000);
                    continue loop;
               }

               //System.out.println(commentBody);
               //replyToUnread(httpClient, commentBody);
               //postReply(httpClient, 7252778048461849386l, "Test.");

               System.out.println("Iteration: " + i++);
               Thread.sleep(10000);


            }
            catch(Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
}