package com.nullpoo.songcodes;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;

public class SearchActivity extends Activity {

    public static final String INTENT_PARAM_KEYWORD = "IntentParamSearch";

    /** 検索結果表示用のリストビュー */
    private ListView mListView;

    /** 検索結果一覧 */
    private ArrayList<SongInfo> mSongInfos;

    /** 検索キーワード */
    private String mKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //検索キーワードを取得
        mKeyword = getIntent().getStringExtra(INTENT_PARAM_KEYWORD);
        //キーワードをURLエンコード
        try {
            mKeyword = URLEncoder.encode(mKeyword, "Shift_JIS");
            Log.d("SongCodes", mKeyword);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mListView = (ListView)findViewById(R.id.search_list);
        //アダプタの設定
        mSongInfos = new ArrayList<SongInfo>();
        SearchInfoArrayAdapter adapter = new SearchInfoArrayAdapter(this, R.layout.activity_search_list_item, mSongInfos);
        mListView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, mSearchCallbacks);
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

    /** 検索結果を返すローダー */
    private LoaderCallbacks mSearchCallbacks = new LoaderCallbacks<List<SongInfo>>() {
        @Override
        public Loader<List<SongInfo>> onCreateLoader(int id, Bundle args) {
            SearchSongListLoader listLoader = new SearchSongListLoader(getApplicationContext(), mKeyword);
            listLoader.forceLoad(); //開始
            return listLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<SongInfo>> loader, List<SongInfo> data) {
            mSongInfos.addAll(data);
            ((SearchInfoArrayAdapter) mListView.getAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<SongInfo>> loader) {

        }
    };

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
