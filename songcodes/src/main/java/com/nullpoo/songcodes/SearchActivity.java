package com.nullpoo.songcodes;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    public static final String INTENT_PARAM_SEARCH = "IntentParamSearch";

    /** 検索結果表示用のリストビュー */
    private ListView mListView;

    /** データストア */
    private ISearchDataStore mDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDataStore = new SearchDataStore();
        String[] keywords = new String[] {"hoge"};
        ArrayList<SongInfo> songInfos = mDataStore.getSearchResult(keywords, ISearchDataStore.SearchType.AND);
        SongInfo item = new SongInfo();
        item.title = "hoge";
        item.credit = "fuga";
        songInfos.add(item);

        mListView = (ListView)findViewById(R.id.search_list);
        SearchInfoArrayAdapter adapter = new SearchInfoArrayAdapter(this, R.layout.activity_search_list_item, songInfos);
        mListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 検索結果リスト表示のアダプター
     */
    private class SearchInfoArrayAdapter extends ArrayAdapter<SongInfo> {

        /** レイアウトインフレータ */
        private LayoutInflater mInflater;

        /** レイアウトID */
        private int mLayoutId;

        /** リストデータ */
        private List<SongInfo> mData;

        private SearchInfoArrayAdapter(Context context, int resource, List<SongInfo> objects) {
            super(context, resource, objects);
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            mLayoutId = resource;
            mData = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_search_list_item, null);
                holder = new ViewHolder(convertView);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            //値を入れていく
            SongInfo data = getItem(position);
            holder.titleTextView.setText(data.title);
            holder.creditTextView.setText(data.credit);

            return convertView;
        }
    }

    /**
     * ビューホルダー
     */
    private static class ViewHolder {
        /** 曲名 */
        TextView titleTextView;
        /** 著作者情報 */
        TextView creditTextView;

        private ViewHolder(View view) {
            titleTextView = (TextView) view.findViewById(R.id.search_list_item_title);
            creditTextView = (TextView) view.findViewById(R.id.search_list_item_credit);
            view.setTag(this);
        }
    }

}
