package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mad Bomber Labs on 9/13/2017.
 */

public class DBAdapter extends Activity
{

// Properties

    // Add the definitions for the db
    private static final String DATABASE_NAME="Connect_Niagara";
    private static final String TABLE_NAME="organization";
    private static final String KEY_ID="_id";
    private static final String KEY_ORG="org";
    private static final String KEY_FIRSTNAME="firstName";
    private static final String KEY_LASTNAME="lastName";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PHONE="phone";
    private static final String KEY_WEBSITE="website";
    private static final String KEY_SERVICETYPE="serviceType";
    private static final String KEY_SERVICE="service";
    private static final String KEY_FAVORITE="favorite";
    private static final int DATABASE_VERSION=1;

    // DB definitions for address
    private static final String KEY_STREETADDRESS="streetAddress";
    private static final String KEY_CITY="city";
    private static final String KEY_STATE="state";
    private static final String KEY_ZIP="zip";

    private final Context context;

    private CISHelper DBHelper;
    private SQLiteDatabase niagaraDB;

// Constructors
    public DBAdapter(Context context)
    {
        this.context = context;
        DBHelper = new CISHelper(context);
    }

// Methods

    // Open Database
    public DBAdapter open()
    {
        niagaraDB = DBHelper.getWritableDatabase();

        return this;
    }

    // Close Database
    public void close()
    {
        DBHelper.close();
    }

    // Insert Table into DB
    public void insertContact(String id, String org, String firstName, String lastName,
                              String email, String phone, String website, String serviceType,
                              String service, String streetaddress, String city, String state,
                              String zip, String favorite)
    {
        niagaraDB.execSQL("INSERT INTO " + TABLE_NAME + " ("

                + KEY_ID + ", "
                + KEY_ORG + ", "
                + KEY_FIRSTNAME + ", "
                + KEY_LASTNAME + ", "
                + KEY_EMAIL + ", "
                + KEY_PHONE + ", "
                + KEY_WEBSITE + ", "
                + KEY_SERVICETYPE + ", "
                + KEY_SERVICE + ", "
                + KEY_STREETADDRESS + ", "
                + KEY_CITY + ", "
                + KEY_STATE + ", "
                + KEY_ZIP + ", "
                + KEY_FAVORITE

                + ")"
                + " VALUES ("

                + "\'" + id + "\'" + ","
                + "\'" + org + "\'" + ","
                + "\'" + firstName + "\'" + ","
                + "\'" + lastName + "\'" + ","
                + "\'" + email + "\'" + ","
                + "\'" + phone + "\'" + ","
                + "\'" + website + "\'" + ","
                + "\'" + serviceType + "\'" + ","
                + "\'" + service + "\'" + ","
                + "\'" + streetaddress + "\'" + ","
                + "\'" + city + "\'" + ","
                + "\'" + state + "\'" + ","
                + "\'" + zip + "\'" + ","
                + "\'" + favorite + "\'"

                + ");"
        );
    }

    // Query - Get * Records
    public Cursor getAllRecords()
    {
        // SELECT * FROM TABLE_NAME
        return niagaraDB.query(TABLE_NAME,
                new String[]{KEY_ID, KEY_ORG, KEY_FIRSTNAME, KEY_LASTNAME, KEY_EMAIL, KEY_PHONE,
                        KEY_WEBSITE, KEY_SERVICETYPE, KEY_SERVICE, KEY_STREETADDRESS, KEY_CITY,
                        KEY_STATE, KEY_ZIP, KEY_FAVORITE },
                null, null, null, null, null);
    }

    // Query - Get Id by Name
    public Cursor getIdByName(String org)
    {
        // SELECT KEY_ID FROM TABLE_NAME WHERE KEY_ORG = org
        return niagaraDB.query(
                TABLE_NAME,
                new String[] {KEY_ID},
                KEY_ORG + "= \'" + org + "\'",
                null,null,null,null);
    }

    // Query - Get * Records by ID
    public Cursor getRecordByID(String id)
    {
        // SELECT ITEMS FROM TABLE_NAME WHERE KEY_ID = id
        return niagaraDB.query(TABLE_NAME,
                new String[]{KEY_ORG, KEY_FIRSTNAME, KEY_LASTNAME, KEY_EMAIL, KEY_PHONE,
                        KEY_WEBSITE, KEY_SERVICETYPE, KEY_SERVICE, KEY_STREETADDRESS, KEY_CITY,
                        KEY_STATE, KEY_ZIP, KEY_FAVORITE },
                KEY_ID + "= \'" + id + "\'",
                null, null, null, null);
    }

    // Query - Get * Favorites
    public Cursor getRecordByFavorite(String favorite)
    {
        // SELECT * FROM TABLE_NAME WHERE KEY_FAVORITE = 1
        return niagaraDB.query(TABLE_NAME,
                new String[] {KEY_ID, KEY_ORG, KEY_FIRSTNAME, KEY_LASTNAME, KEY_EMAIL, KEY_PHONE,
                        KEY_WEBSITE, KEY_SERVICETYPE, KEY_SERVICE, KEY_STREETADDRESS, KEY_CITY,
                        KEY_STATE, KEY_ZIP, KEY_FAVORITE },
                KEY_FAVORITE + "= \'" + favorite + "\'",
                null, null, null, null);
    }

    // Query - Get * Service Type
    public Cursor getRecordByServiceType(String serviceType)
    {
        // SELECT * FROM TABLE_NAME WHERE KEY_SERVICETYPE = serviceType
        return niagaraDB.query(TABLE_NAME,
                new String[] {KEY_ID, KEY_ORG, KEY_FIRSTNAME, KEY_LASTNAME, KEY_EMAIL, KEY_PHONE,
                        KEY_WEBSITE, KEY_SERVICETYPE, KEY_SERVICE, KEY_STREETADDRESS, KEY_CITY,
                        KEY_STATE, KEY_ZIP, KEY_FAVORITE },
                KEY_SERVICETYPE + "= \'" + serviceType + "\'",
                null, null, null, null);
    }

    // Query - Get Email
    public Cursor getEmailByID(String id)
    {
        // SELECT KEY_EMAIL FROM TABLE_NAME WHERE KEY_ID = id
        return niagaraDB.query(
                TABLE_NAME,
                new String[] {KEY_EMAIL},
                KEY_ID + "= \'" + id + "\'",
                null,null,null,null);
    }

    // Query - Get PhoneNum
    public Cursor getPhoneByID(String id)
    {
        // SELECT KEY_PHONE FROM TABLE_NAME WHERE KEY_ID = id
        return niagaraDB.query(
                TABLE_NAME,
                new String[] {KEY_PHONE},
                KEY_ID + "= \'" + id + "\'",
                null,null,null,null);
    }

    // Query - Get PhoneNum
    public Cursor getWebByID(String id)
    {
        // SELECT KEY_WEBSITE FROM TABLE_NAME WHERE KEY_ID = id
        return niagaraDB.query(
                TABLE_NAME,
                new String[] {KEY_WEBSITE},
                KEY_ID + "= \'" + id + "\'",
                null,null,null,null);
    }

    // Query - Get Address
    public Cursor getAddressByID(String id)
    {
        // SELECT KEY_WEBSITE FROM TABLE_NAME WHERE KEY_ID = id
        return niagaraDB.query(
                TABLE_NAME,
                new String[] {KEY_STREETADDRESS, KEY_CITY, KEY_STATE, KEY_ZIP},
                KEY_ID + "= \'" + id + "\'",
                null,null,null,null);
    }

    // Update Record
    public boolean updateFavById(String str, String favorite)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_FAVORITE, favorite);

        return niagaraDB.update(TABLE_NAME, args, KEY_ID + "=\'" + str + "\'", null) > 0;
    }


// Subclasses
    // New class CISHelper
    public class CISHelper extends SQLiteOpenHelper
    {
        public CISHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("

                    + KEY_ID + " CHAR(6) NOT NULL PRIMARY KEY,"
                    + KEY_ORG + " VARCHAR(100),"
                    + KEY_FIRSTNAME + " VARCHAR(25),"
                    + KEY_LASTNAME + " VARCHAR(25),"
                    + KEY_EMAIL + " VARCHAR(100),"
                    + KEY_PHONE + " VARCHAR(14),"
                    + KEY_WEBSITE + " VARCHAR(100),"
                    + KEY_SERVICETYPE + " VARCHAR(25),"
                    + KEY_SERVICE + " VARCHAR(1000),"
                    + KEY_STREETADDRESS + " VARCHAR(100),"
                    + KEY_CITY + " VARCHAR(25),"
                    + KEY_STATE + " VARCHAR(2),"
                    + KEY_ZIP + " VARCHAR(10),"
                    + KEY_FAVORITE + " VARCHAR(1)"

                    + ");"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            onCreate(db);
        }
    }

}
