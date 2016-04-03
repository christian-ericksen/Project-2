package com.example.christian.project2;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView mSearchListView;
    private Button mButton;
    private DatabaseHelper mHelper;
    private Cursor cursor;
    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchListView = (ListView) findViewById(R.id.location_list_view);
        mButton = (Button) findViewById(R.id.button);
        mHelper = DatabaseHelper.getInstance(this);


        cursor = mHelper.getLocationList();

        cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.icon_list_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                //ImageView imageView = (ImageView) view.findViewById(R.id.location_image_view);
                TextView textView = (TextView) view.findViewById(R.id.location_name_text_view);
                String locationName = cursor.getString(cursor.getColumnIndex("LOCATION_NAME"));

                //imageView.setImageResource(getDrawableValue(locationName));
                textView.setText((locationName));
            }
        };

        mSearchListView.setAdapter(cursorAdapter);

        handleIntent(getIntent());

        mSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailScrollingActivity.class);
                cursorAdapter.getCursor().moveToPosition(position);
                //use cursorAdapter.getCursor() so that adapter always knows what its looking at
                intent.putExtra("id", cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);


        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = mHelper.searchLocationList(query);
            cursorAdapter.swapCursor(cursor);
            cursorAdapter.notifyDataSetChanged();
        }
    }
}



   /* private int getDrawableValue(String icon) {
        switch (icon) {
            case "Central Park":
                return R.drawable.central_park;
            case "Bronx Zoo":
                return R.drawable.bronx_zoo;
            case "Botanical Gardens":
                return R.drawable.botanical_gardens;
            case "Madison Square Garden":
                return R.drawable.madison_square_garden;
            case "Grand Central":
                return R.drawable.grand_central;
            default:
                return 0;
        }
    }
}
*/


/*import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    SearchView mSearch;
    Button mFavorites;
    Button mListButton;
    ListView mSearchListView;
    Cursor mCursor;
    CursorAdapter mAdapter;
    TextView mAppName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);


        mListButton = (Button) findViewById(R.id.button);
        mFavorites = (Button) findViewById(R.id.button2);

    View.OnClickListener mainListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == mListButton){

            }
            else if (v == mFavorites){

            }

        }
    };





    }
} */