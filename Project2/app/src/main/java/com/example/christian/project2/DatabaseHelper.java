package com.example.christian.project2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by christianericksen on 2/4/16.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
//    private static final String TAG = DatabaseHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "VDAY_DB";
    public static final String TABLE_NAME = "VDAY_TABLE";

    public static final String COL_ID="_id";
    public static final String COL_1="TYPE";
    public static final String COL_2="NAME";
    public static final String COL_3="BOROUGH";
    public static final String COL_4="ADDRESS";
    public static final String COL_5="DESCRIPTION";
    public static final String COL_6="FAVORITE";

    /* private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "LOCATION_DB";
    public static final String LOCATION_LIST_TABLE_NAME = "LOCATION_LIST";

    public static final String COL_ID="_id";
    public static final String COL_LOCATION_NAME="LOCATION_NAME";
    public static final String COL_LOCATION_ADDRESS="LOCATION_ADDRESS";
    public static final String COL_LOCATION_NEIGHBORHOOD="LOCATION_NEIGHBORHOOD";
    public static final String COL_LOCATION_FAVORITES="LOCATION_FAVORITES";
    public static final String COL_LOCATION_IMAGES="LOCATION_IMAGES"; */


    public static final String [] LOCATION_COLUMNS = {COL_ID, COL_1, COL_2, COL_3, COL_4, COL_5};

    private static final String CREATE_LOCATION_LIST_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( " +
            COL_ID + " INTEGER PRIMARY KEY, " + COL_1 + " TEXT, " +
            COL_2 + " TEXT, "
            + COL_3 + " TEXT, " + COL_4 + " TEXT, "
            + COL_5 + " TEXT," + COL_6 + "INTEGER ) ";

    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }
    Context mContext;
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOCATION_LIST_TABLE);
        addItemToDataBase(db, "Flowers", "Floral Fixation", "Brooklyn", "69 Dank Street", "For weddings, funerals and everything in between, this purveyor of fine botanical products has you covered.", 0);
        addItemToDataBase(db, "Flowers", "Petal to the Metal", "Manhattan", "234 Palindrome Ave", "Flowers and Death Metal ALL DAY", 0);
        addItemToDataBase(db, "Flowers", "Buddies", "Manhattan", "14 E 21st Street", "We sell flowers. Just flowers.", 0);
        addItemToDataBase(db, "Restaurant", "St. Anselm", "Brooklyn", "42 Metropolitan Ave", "This local favorite is considered the best restuarant in Williamsburg. They take no reservations, so be sure to show up two hours beforehand.", 0);
        addItemToDataBase(db, "Restaurant", "Diner", "Brooklyn", "12 S 2nd", "These fucking hipsters have taken an old trailer, recomissioned it as a restaurant, and serves mac n' cheese for $14. They don't take reservations, so show up early.", 0);
        addItemToDataBase(db, "Restaurant", "McDonalds", "Manhattan", "32 E 14th Street", "Mickey D's on Valentine's Day? You sure are a catch, aren't you? Your girl will love the ample seating room and how quickly the food comes out. Enjoy the #7 meal while listening to the sobs of single people as they cry into their cheeseburgers.", 0);
        addItemToDataBase(db, "Condoms", "Duane Reade", "Brooklyn", "123 Bedford Ave", "They sell a variety of brands, including Magnum XL, which is obviously what you need, right?", 0);
        addItemToDataBase(db, "Condoms", "Bodega", "Manhattan", "34 W 42nd", "Selection is limited, but it's going to suck no matter what you buy, so who cares what brands they have?", 0);
        addItemToDataBase(db, "Condoms", "Big Lou's Tattoo Parlour", "Brooklyn", "203 N 4th St.", "Not actually a store that sells condoms, but they have one of those NYC Condom dispensers if you need it in a pinch. Plus, they're free, you cheap bastard!", 0);
    }
        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            this.onCreate(db);
        }


    private void addItemToDataBase (SQLiteDatabase db, String type, String name, String borough, String address, String description, Integer favorite) {
        ContentValues values = new ContentValues();
        values.put(COL_1, type);
        values.put(COL_2, name);
        values.put(COL_3, borough);
        values.put(COL_4, address);
        values.put(COL_5, description);
        values.put(COL_6, favorite);

        db.insert(TABLE_NAME, null, values);
    }

    public boolean updateAddFavorite(int id, int favorite) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(COL_ID, id);
//        values.put(COL_LOCATION_NAME, name);
//        values.put(COL_LOCATION_ADDRESS, address);
//        values.put(COL_LOCATION_NEIGHBORHOOD, environment);
        values.put(COL_6, favorite);

        db.update(TABLE_NAME, values, COL_ID + " = ? ", new String[]{String.valueOf(id)});

        return true;
    }

    public boolean updateRemoveFavorite(int id, int favorite) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_6, favorite);

        db.update(TABLE_NAME, values, COL_ID + " = ? ", new String []{String.valueOf(id)});

        return true;
    }

    public Cursor getFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                LOCATION_COLUMNS,
                COL_6 + " LIKE 1 ",
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    public Cursor getLocationList () {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                LOCATION_COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

//    public int deleteItem(int id){
//        SQLiteDatabase db = getWritableDatabase();
//        int deleteNum = db.delete(LOCATION_LIST_TABLE_NAME,
//                COL_ID + " = ?",
//                new String[]{String.valueOf(id)});
//        db.close();
//        return deleteNum;
//    }

    public Cursor searchLocationList (String query) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                LOCATION_COLUMNS,
                COL_1 + " LIKE ? OR " + COL_2 + " LIKE ? OR " + COL_3 + " LIKE ? ",
                new String []{"%" + query + "%"},
                null,
                null,
                null,
                null);

        return cursor;
    }

    public String[] getDescriptionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_1, COL_2, COL_3, COL_4, COL_5, COL_6},
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        cursor.moveToFirst();

        String [] detailsArray = new String[]{
                cursor.getString(cursor.getColumnIndex(COL_1)),
                cursor.getString(cursor.getColumnIndex(COL_2)),
                cursor.getString(cursor.getColumnIndex(COL_3)),
                cursor.getString(cursor.getColumnIndex(COL_4)),
                cursor.getString(cursor.getColumnIndex(COL_5)),
                cursor.getString(cursor.getColumnIndex(COL_6)),
        };

        return detailsArray;

    }

    public int checkFavoriteById (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_6},
                COL_ID + " = ? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        cursor.moveToFirst();

        int detailsInt = cursor.getInt(cursor.getColumnIndex(COL_6));

        return detailsInt;
    }


}


