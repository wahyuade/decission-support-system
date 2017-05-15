package namex_project.pens.vammethod.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import namex_project.pens.vammethod.Database.Model.CompanyModel;

/**
 * Created by SHERLY on 15/05/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CompanyDb";
    private static final int DATABASE_VERSION = 1;

    //Inisialisasi tabel perusahaan
    private static final String TABLE_NAME_COMPANIES = "companies";

    //Inisialisasi tabel sumber
    private static final String TABLE_NAME_SOURCES = "sources";

    //Inisialisasi tabel tujuan
    private static final String TABLE_NAME_DESTINATIONS = "destinations";

    //Inisialisasi tabel biaya
    private static final String TABLE_NAME_COSTS = "cost";

    //Kolom tabel perusahaan
    private static final String COMPANY_ID = "id";
    private static final String COMPANY_NAME = "name";

    //Kolom tabel sumber
    private static final String SOURCE_ID = "id";
    private static final String SOURCE_NAME = "name";
    private static final String SOURCE_CAPACITY = "capacity";
    private static final String SOURCE_ID_COMPANY = "id_company";

    //Kolom tabel tujuan
    private static final String DEST_ID = "id";
    private static final String DEST_NAME = "name";
    private static final String DEST_CAPACITY = "capacity";
    private static final String DEST_ID_COMPANY = "id_company";

    //Kolom tabel biaya
    private static final String COST_ID = "id";
    private static final String COST_COST = "cost";
    private static final String COST_ID_SOURCE = "id_source";
    private static final String COST_ID_DEST = "id_destination";
    private static final String COST_ID_COMPANY = "id_company";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Membuat tabel perusahaan
        String CREATE_COMPANIES_TABLE = "CREATE TABLE "+ TABLE_NAME_COMPANIES +"(" +
                COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COMPANY_NAME + " TEXT)";
        db.execSQL(CREATE_COMPANIES_TABLE);

        //Membuat tabel sumber
        String CREATE_SOURCES_TABLE = "CREATE TABLE "+ TABLE_NAME_SOURCES +"(" +
                SOURCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                SOURCE_NAME + " TEXT," +
                SOURCE_CAPACITY + "INTEGER," +
                SOURCE_ID_COMPANY + "INTEGER" +
                ")";
        db.execSQL(CREATE_SOURCES_TABLE);

        //Membuat tabel tujuan
        String CREATE_DESTINATIONS_TABLE = "CREATE TABLE "+ TABLE_NAME_DESTINATIONS +"(" +
                DEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                DEST_NAME + " TEXT," +
                DEST_CAPACITY + "INTEGER," +
                DEST_ID_COMPANY + "INTEGER" +
                ")";
        db.execSQL(CREATE_DESTINATIONS_TABLE);

        //Membuat tabel biaya
        String CREATE_COSTS_TABLE = "CREATE TABLE "+ TABLE_NAME_COSTS +"(" +
                COST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COST_COST + " INTEGER," +
                COST_ID_SOURCE + "INTEGER," +
                COST_ID_DEST + "INTEGER" +
                COST_ID_COMPANY + "INTEGER" +
                ")";
        db.execSQL(CREATE_COSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COMPANIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SOURCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DESTINATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COSTS);
        onCreate(db);
    }

    //Method insert tabel company
    public String insertCompany(CompanyModel data){
        String INSERT_COMPANY = "INSERT INTO "+TABLE_NAME_COMPANIES+" ("+
                COMPANY_NAME + ")" + " VALUES ('"+
                data.getName()+"'"+
                ");";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(INSERT_COMPANY);
        Cursor cur = db.query(TABLE_NAME_COMPANIES, new String[] {COMPANY_ID},COMPANY_NAME+"="+data.getName(),null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }

    //Method insert tabel source
    public String insertSource(CompanyModel data){
        String INSERT_COMPANY = "INSERT INTO "+TABLE_NAME_COMPANIES+" ("+
                COMPANY_NAME + ")" + " VALUES ('"+
                data.getName()+"'"+
                ");";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(INSERT_COMPANY);
        Cursor cur = db.query(TABLE_NAME_COMPANIES, new String[] {COMPANY_ID},COMPANY_NAME+"="+data.getName(),null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }

    //Method insert tabel destination
    public String insertDestination(CompanyModel data){
        String INSERT_COMPANY = "INSERT INTO "+TABLE_NAME_COMPANIES+" ("+
                COMPANY_NAME + ")" + " VALUES ('"+
                data.getName()+"'"+
                ");";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(INSERT_COMPANY);
        Cursor cur = db.query(TABLE_NAME_COMPANIES, new String[] {COMPANY_ID},COMPANY_NAME+"="+data.getName(),null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }

    public String insertCost(CompanyModel data){
        String INSERT_COMPANY = "INSERT INTO "+TABLE_NAME_COMPANIES+" ("+
                COMPANY_NAME + ")" + " VALUES ('"+
                data.getName()+"'"+
                ");";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(INSERT_COMPANY);
        Cursor cur = db.query(TABLE_NAME_COMPANIES, new String[] {COMPANY_ID},COMPANY_NAME+"="+data.getName(),null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }
}
