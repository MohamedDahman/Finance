package Finance.com.Finance.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class Lookup {

    private String shareName;
    private String sharePrice;
/*
    public Lookup(String shareName) {
        this.shareName = shareName;
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.alphavantage.co/query?apikey=NAJXWIA8D6VN6A3K&datatype=csv&function=TIME_SERIES_INTRADAY&interval=1min&symbol="+shareName;
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

    }*/

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }


    public String getSharePrice() {
        return sharePrice;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public String getPrice() {
        String url = "https://www.alphavantage.co/query?apikey=NAJXWIA8D6VN6A3K&datatype=csv&function=TIME_SERIES_INTRADAY&interval=1min&symbol="+shareName;
        InputStream is = null;
        String result="";
        try { is = new URL(url).openStream();int available = is.available();
            if  (available==0) result = "";
        } catch (IOException e) {
            e.printStackTrace();
             result = "";
        }try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            try {
                System.out.println(rd.readLine());
                String s = rd.readLine();
                System.out.println(s);
                String[] split = s.split(",");
                result = split[1];
            } catch (IOException e) {
                e.printStackTrace();
                result = "";
            }
        } finally {
            try { is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     //   result = "";
        return result;
    }

    }
