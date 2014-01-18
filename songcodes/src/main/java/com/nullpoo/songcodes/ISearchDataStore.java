package com.nullpoo.songcodes;

import java.util.ArrayList;

/**
 * Created by nullpoo on 14/01/18.
 */
public interface ISearchDataStore {

    public static enum SearchType {
        AND, OR
    }

    /**
     * 指定したキーワードでAND検索を行います
     * @param keywords 検索ワード
     * @param searchType 検索方法
     * @return 曲情報のリスト
     */
    public ArrayList<SongInfo> getSearchResult(String[] keywords, SearchType searchType);

}
