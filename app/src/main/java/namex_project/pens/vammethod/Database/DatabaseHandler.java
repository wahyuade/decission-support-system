package namex_project.pens.vammethod.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import namex_project.pens.vammethod.Database.Model.CompanyModel;
import namex_project.pens.vammethod.Database.Model.CostModel;
import namex_project.pens.vammethod.Database.Model.DestinationModel;
import namex_project.pens.vammethod.Database.Model.SourceModel;

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
    private static final String COMPANY_PHOTO = "photo";
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
                COMPANY_PHOTO + " BLOP,"+
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPANY_NAME, data.getName());
        contentValues.put(COMPANY_PHOTO, data.getPhoto());

        db.insert(TABLE_NAME_COMPANIES,null,contentValues);
        Cursor cur = db.query(TABLE_NAME_COMPANIES, new String[] {COMPANY_ID},COMPANY_NAME+"='"+data.getName()+"'",null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }

    //Method membaca satu company
    public CompanyModel readCompany(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_COMPANIES, new String[] {
                COMPANY_ID,
                COMPANY_PHOTO,
                COMPANY_NAME},
                COMPANY_ID + "=?", new String[] {Integer.toString(ID)}, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        CompanyModel  data = new CompanyModel(Integer.parseInt(cursor.getString(0)), cursor.getBlob(1), cursor.getString(2));
        return data;
    }

    //Membaca semua data company
    public ArrayList<CompanyModel> readCompanyAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CompanyModel> data = new ArrayList<CompanyModel>();
        Cursor cur = db.query(TABLE_NAME_COMPANIES,new String[]{COMPANY_ID,COMPANY_PHOTO,COMPANY_NAME},null,null,null,null,null);
        for (int cc=0; cc<cur.getCount();cc++){
            cur.moveToPosition(cc);
            data.add(new CompanyModel(Integer.parseInt(cur.getString(0)),cur.getBlob(1),cur.getString(2)));
        }
        db.close();
        return data;
    }

    //Method insert tabel source
    public String insertSource(SourceModel data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SOURCE_NAME, data.getName());
        contentValues.put(SOURCE_CAPACITY, data.getCapacity());
        contentValues.put(SOURCE_ID_COMPANY, data.getId_company());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_SOURCES, null, contentValues);
        Cursor cur = db.query(TABLE_NAME_SOURCES, new String[] {SOURCE_ID},SOURCE_NAME+"="+data.getName(),null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }

    //Membaca satu source
    public SourceModel readSource(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_SOURCES, new String[] {
                        SOURCE_ID,
                        SOURCE_ID_COMPANY,
                        SOURCE_NAME,
                        SOURCE_CAPACITY},
                SOURCE_ID + "=?", new String[] {Integer.toString(ID)}, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        SourceModel  data = new SourceModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        return data;
    }

    //Membaca semua data sources
    public ArrayList<SourceModel> readSourcesAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SourceModel> data = new ArrayList<SourceModel>();

        Cursor cur = db.query(TABLE_NAME_SOURCES,new String[]{SOURCE_ID,SOURCE_ID_COMPANY,SOURCE_CAPACITY,SOURCE_NAME},null,null,null,null,null);
        for (int cc=0; cc<cur.getCount();cc++){
            cur.moveToPosition(cc);
            data.add(new SourceModel(Integer.parseInt(cur.getString(0)),Integer.parseInt(cur.getString(1)),Integer.parseInt(cur.getString(2)),cur.getString(3)));
        }
        db.close();
        return data;
    }

    //Method insert tabel destination
    public String insertDestination(DestinationModel data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEST_NAME, data.getName());
        contentValues.put(DEST_CAPACITY, data.getNeeded_num());
        contentValues.put(DEST_ID_COMPANY, data.getId_company());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_DESTINATIONS, null, contentValues);
        Cursor cur = db.query(TABLE_NAME_DESTINATIONS, new String[] {DEST_ID},DEST_NAME+"="+data.getName(),null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }

    //Method membaca satu destination
    public DestinationModel readDestination(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_DESTINATIONS, new String[] {
                        DEST_ID,
                        DEST_ID_COMPANY,
                        DEST_CAPACITY,
                        DEST_NAME},
                DEST_ID + "=?", new String[] {Integer.toString(ID)}, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        DestinationModel  data = new DestinationModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        return data;
    }

    //Membaca semua data destinations
    public ArrayList<DestinationModel> readDestinationsAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DestinationModel> data = new ArrayList<DestinationModel>();

        Cursor cur = db.query(TABLE_NAME_DESTINATIONS,new String[]{DEST_ID,DEST_ID_COMPANY,DEST_CAPACITY,DEST_NAME},null,null,null,null,null);
        for (int cc=0; cc<cur.getCount();cc++){
            cur.moveToPosition(cc);
            data.add(new DestinationModel(Integer.parseInt(cur.getString(0)),Integer.parseInt(cur.getString(1)),Integer.parseInt(cur.getString(2)),cur.getString(3)));
        }
        db.close();
        return data;
    }

//    public String insertCost(CostModel data){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COST_ID_SOURCE, data.getId_source());
//        contentValues.put(COST_ID_DEST, data.getId_destination());
//        contentValues.put(COST_ID_COMPANY, data.getId_company());
//        contentValues.put(COST_COST, data.getCost());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(TABLE_NAME_COSTS, null, contentValues);
//        Cursor cur = db.query(TABLE_NAME_COSTS, new String[] {COST_ID},COMPANY_NAME+"="+data.getName(),null,null,null,null);
//        cur.moveToPosition(0);
//        String id  = cur.getString(0);
//        db.close();
//        return id;
//    }

    //Method membaca satu cost
    public CostModel readCost(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_COSTS, new String[] {
                        COST_ID,
                        COST_ID_SOURCE,
                        COST_ID_DEST,
                        COST_ID_COMPANY,
                        COST_COST},
                COST_ID + "=?", new String[] {Integer.toString(ID)}, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        CostModel  data = new CostModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
        return data;
    }

    //Membaca semua data costs
    public ArrayList<CostModel> readCostsAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CostModel> data = new ArrayList<CostModel>();

        Cursor cur = db.query(TABLE_NAME_COSTS,new String[]{COST_ID,COST_ID_SOURCE,COST_ID_DEST,COST_ID_COMPANY,COST_COST},null,null,null,null,null);
        for (int cc=0; cc<cur.getCount();cc++){
            cur.moveToPosition(cc);
            data.add(new CostModel(Integer.parseInt(cur.getString(0)),Integer.parseInt(cur.getString(1)),Integer.parseInt(cur.getString(2)),Integer.parseInt(cur.getString(3)), Integer.parseInt(cur.getString(4))));
        }
        db.close();
        return data;
    }
}
