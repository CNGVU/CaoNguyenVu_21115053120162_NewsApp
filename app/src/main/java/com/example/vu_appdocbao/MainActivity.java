package com.example.vu_appdocbao;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    ListView lvTieuDe;
    ArrayList<String> arrayLink;
    ArrayList<News> arrayNews;
    NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTieuDe = findViewById(R.id.listview_news);
        arrayLink = new ArrayList<>();
        arrayNews = new ArrayList<>();

        adapter = new NewsAdapter(MainActivity.this, R.layout.item_custom, arrayNews);
        lvTieuDe.setAdapter(adapter);
        new ReadRSS().execute("https://vnexpress.net/rss/giai-tri.rss");
        lvTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("linkNews",arrayLink.get(position));
                startActivity(intent);
            }
        });
    }
    private class ReadRSS extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String title = "",img ="", time = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                time = parser.getValue(element, "pubDate");


                NodeList nodeList2 = element.getElementsByTagName("description");
                Element line = (Element) nodeList2.item(0);
                Matcher matcher = Pattern.compile("<img src=\"([^\"]+)").matcher(getCharacterDataFromElement(line));
                if(matcher.find()) {
                    img = matcher.group(1);
                }
                arrayLink.add(parser.getValue(element, "link"));
                arrayNews.add(new News(title,img,time));
            }
            adapter.notifyDataSetChanged();
        }
    }
    private String getCharacterDataFromElement(Element line) {
        // TODO Auto-generated method stub
        Node child = line.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}