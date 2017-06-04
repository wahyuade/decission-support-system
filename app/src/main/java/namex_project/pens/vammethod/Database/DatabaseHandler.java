package namex_project.pens.vammethod.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import namex_project.pens.vammethod.Activity.EditData.EditDataActivity;
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

    private Context context;
    private EditDataActivity activity;

    String hasil[][];
    String hasil_ds[];

    int data[][];
    int data_ds[];

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DatabaseHandler(EditDataActivity activity){
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);
        this.activity = activity;
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
                SOURCE_CAPACITY + " INTEGER," +
                SOURCE_ID_COMPANY + " INTEGER" +
                ")";
        db.execSQL(CREATE_SOURCES_TABLE);

        //Membuat tabel tujuan
        String CREATE_DESTINATIONS_TABLE = "CREATE TABLE "+ TABLE_NAME_DESTINATIONS +"(" +
                DEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                DEST_NAME + " TEXT," +
                DEST_CAPACITY + " INTEGER," +
                DEST_ID_COMPANY + " INTEGER" +
                ")";
        db.execSQL(CREATE_DESTINATIONS_TABLE);

        //Membuat tabel biaya
        String CREATE_COSTS_TABLE = "CREATE TABLE "+ TABLE_NAME_COSTS +"(" +
                COST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COST_COST + " INTEGER," +
                COST_ID_SOURCE + " INTEGER," +
                COST_ID_DEST + " INTEGER," +
                COST_ID_COMPANY + " INTEGER" +
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
//    private static final String COST_ID = "id";
//    private static final String COST_COST = "cost";
//    private static final String COST_ID_SOURCE = "id_source";
//    private static final String COST_ID_DEST = "id_destination";
//    private static final String COST_ID_COMPANY = "id_company";
    //Method insert tabel cost
    public String insertCost(CostModel data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COST_COST, data.getCost());
        contentValues.put(COST_ID_SOURCE, data.getId_source());
        contentValues.put(COST_ID_DEST, data.getId_destination());
        contentValues.put(COST_ID_COMPANY, data.getId_company());

        db.insert(TABLE_NAME_COSTS,null,contentValues);
        Cursor cur = db.query(TABLE_NAME_COSTS,new String[] {
                COST_ID,
                COST_ID_SOURCE,
                COST_ID_DEST,
                COST_ID_COMPANY,
                COST_COST}, COST_ID_SOURCE + "="+Integer.toString(data.getId_source())+" and "+COST_ID_DEST+"="+Integer.toString(data.getId_destination())+" and "+COST_ID_COMPANY+"="+Integer.toString(data.getId_company()),null,null,null,null);
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
        Cursor cur = db.query(TABLE_NAME_SOURCES, new String[] {SOURCE_ID},SOURCE_NAME+"='"+data.getName()+"'"+" AND "+SOURCE_ID_COMPANY+"="+data.getId_company(),null,null,null,null);
        cur.moveToFirst();
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

        SourceModel  data = new SourceModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(3)), cursor.getString(2));
        return data;
    }
    //Menghitung total souce
    public int totalSource(int id_company){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_SOURCES,new String[]{SOURCE_ID},SOURCE_ID_COMPANY+"="+Integer.toString(id_company),null,null,null,null);
        cursor.moveToFirst();
        return cursor.getCount();
    }
    //Membaca semua data sources
    public ArrayList<SourceModel> readSourcesAll(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SourceModel> data = new ArrayList<SourceModel>();

        Cursor cur = db.query(TABLE_NAME_SOURCES,new String[]{SOURCE_ID,SOURCE_ID_COMPANY,SOURCE_CAPACITY,SOURCE_NAME},SOURCE_ID_COMPANY+"="+id,null,null,null,null);
        for (int cc=0; cc<cur.getCount();cc++){
            cur.moveToPosition(cc);
            data.add(new SourceModel(Integer.parseInt(cur.getString(0)),Integer.parseInt(cur.getString(1)),Integer.parseInt(cur.getString(2)),cur.getString(3)));
        }
        db.close();
        return data;
    }

    //Method insert tabel destination
    public String insertDestination(DestinationModel data){
        ContentValues insert_des = new ContentValues();
        insert_des.put(DEST_ID_COMPANY,data.getId_company());
        insert_des.put(DEST_NAME, data.getName());
        insert_des.put(DEST_CAPACITY, data.getNeeded_num());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_DESTINATIONS, null, insert_des);
        Cursor cur = db.query(TABLE_NAME_DESTINATIONS, new String[] {DEST_ID},DEST_NAME+"='"+data.getName()+"'"+" AND "+DEST_ID_COMPANY+"="+data.getId_company(),null,null,null,null);
        cur.moveToFirst();
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
    //menghitung total destination
    public int totalDestination(int id_company){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_DESTINATIONS,new String[]{DEST_ID},DEST_ID_COMPANY+"="+Integer.toString(id_company),null,null,null,null);
        return cursor.getCount();
    }
    //Membaca semua data destinations
    public ArrayList<DestinationModel> readDestinationsAll(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DestinationModel> data = new ArrayList<DestinationModel>();

        Cursor cur = db.query(TABLE_NAME_DESTINATIONS,new String[]{DEST_ID,DEST_ID_COMPANY,DEST_CAPACITY,DEST_NAME},DEST_ID_COMPANY+"="+id,null,null,null,null);
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
//        contentValues.put(COST_COST, data.getKeyCost());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(TABLE_NAME_COSTS, null, contentValues);
//        Cursor cur = db.query(TABLE_NAME_COSTS, new String[] {COST_ID},+"="+data.getName(),null,null,null,null);
//        cur.moveToPosition(0);
//        String id  = cur.getString(0);
//        db.close();
//        return id;
//    }

    //Method membaca satu cost
    public CostModel readCost(int id_Source, int id_Dest, int id_company) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_COSTS, new String[] {
                        COST_ID,
                        COST_ID_SOURCE,
                        COST_ID_DEST,
                        COST_ID_COMPANY,
                        COST_COST},
                COST_ID_SOURCE + "="+id_Source+" and "+COST_ID_DEST+"="+id_Dest+" and "+COST_ID_COMPANY+"="+id_company, null,null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                CostModel  data = new CostModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
                return data;
            }else{
                CostModel  data = new CostModel(0,0,0,0,0);
                return data;
            }
        }else{
            CostModel  data = new CostModel(0,0,0,0);
            return data;
        }
    }

    public CostModel readCostWhereId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_COSTS, new String[] {
                        COST_ID,
                        COST_ID_SOURCE,
                        COST_ID_DEST,
                        COST_ID_COMPANY,
                        COST_COST},
                COST_ID+"="+id, null,null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                CostModel  data = new CostModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
                return data;
            }else{
                CostModel  data = new CostModel(0,0,0,0,0);
                return data;
            }
        }else{
            CostModel  data = new CostModel(0,0,0,0);
            return data;
        }
    }

    public boolean updateCost(int id_source, int id_destination, int id_company, int cost){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues update_cost = new ContentValues();
        update_cost.put(COST_COST, cost);
        db.update(TABLE_NAME_COSTS,update_cost,COST_ID_SOURCE + "="+id_source+" and "+COST_ID_DEST+"="+id_destination+" and "+COST_ID_COMPANY+"="+id_company,null);
        return true;
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

    public String totalDataSource(String id_company){
        String totalDataSource;
        int tmp = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_SOURCES,new String[]{SOURCE_CAPACITY},SOURCE_ID_COMPANY+"="+id_company,null,null,null,null);
        if(cursor.getCount() > 0){
            for(int i = 0; i< cursor.getCount(); i++){
                cursor.moveToPosition(i);
                tmp = tmp + Integer.parseInt(cursor.getString(0));
            }
            totalDataSource = Integer.toString(tmp);
            return totalDataSource;
        }else{
            return "0";
        }
    }
    public String totalDataDestination(String id_company){
        String totalDataDestination;
        int tmp = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_DESTINATIONS,new String[]{DEST_CAPACITY},DEST_ID_COMPANY+"="+id_company,null,null,null,null);
        if(cursor.getCount() > 0){
            for(int i = 0; i< cursor.getCount(); i++){
                cursor.moveToPosition(i);
                tmp = tmp + Integer.parseInt(cursor.getString(0));
            }
            totalDataDestination = Integer.toString(tmp);
            return totalDataDestination;
        }else{
            return "0";
        }
    }
    public String[][] getKeyCost(String id_company, String tot_s, String tot_d){
        int j=-1;
        hasil = new String[Integer.parseInt(tot_s)][Integer.parseInt(tot_d)];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_COSTS,new String[]{COST_ID}, COST_ID_COMPANY+"="+id_company,null,null,null,COST_ID_SOURCE,null);
        if(cursor.getCount()>0){
            for (int i = 0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                if((i%Integer.parseInt(tot_d))==0){
                    hasil[++j][i%Integer.parseInt(tot_d)] = cursor.getString(0);
                }else{
                    hasil[j][i%Integer.parseInt(tot_d)] = cursor.getString(0);
                }
            }
        }
        return hasil;
    }
    public  String[] getKeySource(String id_company, String tot_s){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_SOURCES,new String[]{SOURCE_ID}, SOURCE_ID_COMPANY+"="+id_company,null,null,null,SOURCE_ID,null);
        hasil_ds = new String[Integer.parseInt(tot_s)];
        for (int i = 0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            hasil_ds[i] = cursor.getString(0);
        }
        return hasil_ds;
    }
    public  String[] getKeyDestination(String id_company, String tot_d){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_DESTINATIONS,new String[]{DEST_ID}, DEST_ID_COMPANY+"="+id_company,null,null,null,DEST_ID,null);
        hasil_ds = new String[Integer.parseInt(tot_d)];
        for (int i = 0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            hasil_ds[i] = cursor.getString(0);
        }
        return hasil_ds;
    }

    public int[][] getCost(String id_company, String tot_s, String tot_d){
        int j=-1;
        data = new int[Integer.parseInt(tot_s)][Integer.parseInt(tot_d)];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_COSTS,new String[]{COST_COST}, COST_ID_COMPANY+"="+id_company,null,null,null,COST_ID_SOURCE,null);
        if(cursor.getCount()>0){
            for (int i = 0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                if(i%Integer.parseInt(tot_d)==0){
                    j++;
                }
                data[j][i%Integer.parseInt(tot_d)] = Integer.parseInt(cursor.getString(0));
            }
        }
        return data;
    }
    public  int[] getSource(String id_company, String tot_s){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_SOURCES,new String[]{SOURCE_CAPACITY}, SOURCE_ID_COMPANY+"="+id_company,null,null,null,SOURCE_ID,null);
        data_ds = new int[Integer.parseInt(tot_s)];
        for (int i = 0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            data_ds[i] = Integer.parseInt(cursor.getString(0));
        }
        return data_ds;
    }
    public  int[] getDestination(String id_company, String tot_d){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_DESTINATIONS,new String[]{DEST_CAPACITY}, DEST_ID_COMPANY+"="+id_company,null,null,null,DEST_ID,null);
        data_ds = new int[Integer.parseInt(tot_d)];
        for (int i = 0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            data_ds[i] = Integer.parseInt(cursor.getString(0));
        }
        return data_ds;
    }
}