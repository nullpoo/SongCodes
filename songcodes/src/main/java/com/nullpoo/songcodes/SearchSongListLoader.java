package com.nullpoo.songcodes;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nullpoo on 14/01/26.
 */
public class SearchSongListLoader extends AsyncTaskLoader<List<SongInfo>> {

    private static final String JTOTAL_URL = "http://music.j-total.net/";

    private String mRequestUrl;

    public SearchSongListLoader(Context context, String keyword) {
        super(context);
        //リクエストURLを生成
        mRequestUrl = JTOTAL_URL+"db/search.cgi?mode=search&word="+keyword;
    }

    @Override
    public List<SongInfo> loadInBackground() {
        List<SongInfo> songInfos = new ArrayList<SongInfo>();

        try {
            Document document = Jsoup.connect(mRequestUrl).get();
            for (Element element: document.select("table[width=450]")) {
                SongInfo songInfo = new SongInfo();
                songInfo.title = element.getElementsByTag("b").html();
                songInfo.credit = element.getElementsByTag("td").last().html().replace("　", "");
                songInfo.url = element.getElementsByTag("a").attr("href");
                songInfos.add(songInfo);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return songInfos;
    }


}
