package com.nullpoo.songcodes;

import java.util.ArrayList;

/**
 * Created by 晃樹 on 14/01/18.
 */
public class SearchDataStore implements ISearchDataStore {
    @Override
    public ArrayList<SongInfo> getSearchResult(String[] keywords, SearchType searchType) {
        return new ArrayList<SongInfo>();
    }
}
