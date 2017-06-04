package namex_project.pens.vammethod.Activity.Route;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.Database.Model.OutputModel;
import namex_project.pens.vammethod.R;

public class vamMethod extends AppCompatActivity {
    public static final Integer BERISI = 1;


    int jumlah_sumber ;
    int jumlah_tujuan ;


    //map(String,value);
    Map biaya = new HashMap();
    Map tujuan = new HashMap();
    Map sumber = new HashMap();
    Map rp = new HashMap();
    Map cp = new HashMap();
    Map rf = new HashMap();
    Map cf = new HashMap();
    Map a = new HashMap();

    int id_company;

    //data contoh


    int input_jumlah_sumber;//m
    int input_jumlah_tujuan;//n
    int[][] input_biaya;
    int[] input_tujuan;
    int[] input_sumber;

    String[][] key_cost;
    String[] key_source;
    String[] key_destination;

    RecyclerView list_route;
    TextView result_cost;

    ArrayList<OutputModel> data_route;
    OutputModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vam_method);

        data_route = new ArrayList<>();

        getSupportActionBar().hide();

        list_route = (RecyclerView)findViewById(R.id.list_route);
        result_cost = (TextView)findViewById(R.id.result_cost);

        Intent data = getIntent();
        id_company = Integer.parseInt(data.getStringExtra("id_company"));
        DatabaseHandler db = new DatabaseHandler(this);
        input_jumlah_sumber = db.totalSource(id_company);
        input_jumlah_tujuan = db.totalDestination(id_company);
        key_cost = db.getKeyCost(Integer.toString(id_company),Integer.toString(input_jumlah_sumber),Integer.toString(input_jumlah_tujuan));
        key_source = db.getKeySource(Integer.toString(id_company),Integer.toString(input_jumlah_sumber));
        key_destination = db.getKeyDestination(Integer.toString(id_company),Integer.toString(input_jumlah_tujuan));

        input_biaya = db.getCost(Integer.toString(id_company),Integer.toString(input_jumlah_sumber),Integer.toString(input_jumlah_tujuan));
        input_sumber = db.getSource(Integer.toString(id_company),Integer.toString(input_jumlah_sumber));
        input_tujuan = db.getDestination(Integer.toString(id_company),Integer.toString(input_jumlah_tujuan));
        metodeVam();

        RouteAdapter routeAdapter = new RouteAdapter(vamMethod.this,data_route);

        list_route.setLayoutManager(new LinearLayoutManager(vamMethod.this));
        list_route.setAdapter(routeAdapter);


    }
    private void rubahMenjadiArrayList(){
        jumlah_tujuan = input_jumlah_tujuan;
        jumlah_sumber = input_jumlah_sumber;
        for(int i=0;i<jumlah_sumber;i++){
            for (int j=0;j<jumlah_tujuan;j++) {
                biaya.put(key_cost[i][j],input_biaya[i][j]);
            }
        }
        for (int i=0;i<jumlah_tujuan;i++)
            tujuan.put(i,input_tujuan[i]);
        for (int i=0;i<jumlah_sumber;i++)
            sumber.put(i,input_sumber[i]);
    }
    void metodeVam() {
        rubahMenjadiArrayList();
        cekIsiList();
        int k;

        int i,j,max,min,p,s=0,t = 0,sum = 0;

        for (i=0;i<jumlah_sumber;i++)
            rf.put(i,0);
        for (i=0;i<jumlah_tujuan;i++)
            cf.put(i,0);

        Integer dinamis_sumber = jumlah_sumber;
        Integer dinamis_tujuan = jumlah_tujuan;

        //selama sumber dan tujuan lebih dari 0
        while (dinamis_sumber>0&&dinamis_tujuan>0){

            for ( i =0;i<jumlah_sumber;i++)
                rp.put(i,-1);
            for ( i =0;i<jumlah_tujuan;i++)
                cp.put(i,-1);

            for ( i=0;i<jumlah_sumber;i++){
                k=0;
                if (rf.get(i)!=BERISI){
                    // a[] digunakan untuk menyimpan cost dalam bentuk 1 dimensi dan memprosesnya
                    for ( j=0;j<jumlah_tujuan;j++){
                        if (cf.get(j)!=BERISI){
                            a.put(k++,biaya.get(key_cost[i][j]));
                        }
                    }
                    if (k==1)
                        rp.put(i,a.get(0));

                        //diurutkan dan dicari selisih cost terkecil
                    else{
                        sort(a,k);
                        //rp digunakan untuk menyimpan nilai selisih tiap barisnya
                        rp.put(i,(int)a.get(1)-(int)a.get(0));
                    }
                }
            }
            for (i=0;i<jumlah_tujuan;i++){
                k=0;
                if (cf.get(i)!=BERISI){
                    for (j=0;j<jumlah_sumber;j++) {
                        if (rf.get(j)!= BERISI) {
                            //Log.d(String.valueOf(biaya.get(str)),"nilai biaya.get(str)=======================================");
                            a.put(k++, biaya.get(key_cost[j][i]));
                        }
                    }
                    if (k==1){
                        cp.put(i,a.get(0));
                    }
                    else {
                        sort(a,k);
                        //cp digunakan untuk menyimpan nilai selisih tiap kolomnya
                        cp.put(i,(int)a.get(1)-(int)a.get(0));
                    }
                }
            }

            //Array a[] digunakan untuk menyimpan urutan selisih terkecil
            for ( i=0;i<jumlah_sumber;i++)
                a.put(i,rp.get(i));

            for ( j=0;j<jumlah_tujuan;j++){
                int index = i+j;
                a.put(index,cp.get(j));
                //Log.d(String.valueOf(a.get(index)),"nilai a pada"+index);
            }

            max= (int) a.get(0);
            p=0;

            //Dicari nilai selisih yg terbesar dan disimpan nilai index-nya (p)
            for (i=1;i<jumlah_tujuan+jumlah_sumber;i++){
                if (max<(int)a.get(i)){
                    max=(int) a.get(i);
                    p=i;
                }
            }

            Log.d(String.valueOf(p),"index variabel penyimpan beda max");
            Log.d(String.valueOf(max),"nilai max dari semua beda");

            min=1000;

            //jika nilai selisih terbesar ada di kolom --v--
            if (p>jumlah_sumber-1){
                p=p-jumlah_sumber;

                if (cf.get(p)!=BERISI){
                    for (i=0;i<jumlah_sumber;i++){
                        if (rf.get(i)!=BERISI){
                            if(min> (int) biaya.get(key_cost[i][p])){
                                //min digunakan untuk mencari nilai cost terkecil
                                min= (int) biaya.get(key_cost[i][p]);
                                //s = menyimpan index baris ----- t = menyimpan index kolom (dengan cost terkecil)
                                s=i;
                                t=p;
                            }
                        }
                    }
                }
            }

            //Jika nilai selisih terbesar ada di baris
            else    {
                if (rf.get(p)!=BERISI){
                    for(i=0;i<jumlah_tujuan;i++){
                        if (cf.get(i)!=BERISI){
                            if (min>(int) biaya.get(key_cost[p][i])){
                                //min digunakan untuk mencari nilai cost terkecil
                                min=(int) biaya.get(key_cost[p][i]);
                                s=p;
                                t=i;
                            }
                        }
                    }
                }
            }

            //s = menyimpan index baris ----- t = menyimpan index kolom (dengan cost terkecil)
            //Log.d(String.valueOf(min),"nilai min");
            //Log.d(String.valueOf(s),"nilai S");
            //Log.d(String.valueOf(t),"nilai T");

            //Jika nilai source capacity kurang dari dest capacity
            if((int)sumber.get(s)<(int)tujuan.get(t)){

                data = new OutputModel();

                //sum = digunakan untuk menyimpan jumlah biaya transportasi
                sum=sum+ (int)biaya.get(key_cost[s][t]) * (int) sumber.get(s);
                Log.d(key_cost[s][t],"lokasi yang akan di isi");
                Log.d(String.valueOf(sumber.get(s)),"nilai");

                data.setId(Integer.parseInt(key_cost[s][t]));
                data.setHasil((Integer) sumber.get(s));

                data_route.add(data);
                tujuan.put(t,(int)tujuan.get(t)-(int)sumber.get(s));
                //rf digunakan untuk mengandai bahwa baris tsb tidak akan diproses kembali saat pencarian selisih
                rf.put(s,1);
                //jumlah baris yang akan dikurangi
                dinamis_sumber--;
            }

            //Jika nilai source capacity lebih dari dest capacity
            else if((int)sumber.get(s)>(int)tujuan.get(t)){
                data = new OutputModel();
                sum=sum+ (int)biaya.get(key_cost[s][t])*(int)tujuan.get(t);
                Log.d(key_cost[s][t],"lokasi yang akan di isi");
                Log.d(String.valueOf(tujuan.get(t)),"nilai");

                data.setId(Integer.parseInt(key_cost[s][t]));
                data.setHasil((Integer) tujuan.get(t));

                data_route.add(data);

                sumber.put(s,(int)sumber.get(s)-(int)tujuan.get(t));
                //rf digunakan untuk mengandai bahwa kolom tsb tidak akan diproses kembali saat pencarian selisih
                cf.put(t,1);
                //jumlah kolom akan dikurangi
                dinamis_tujuan--;
            }

            //Jika source capacity = dest capacity
            else if((int) sumber.get(s)==(int) tujuan.get(t)){
                data = new OutputModel();
                sum=sum+ (int)biaya.get(key_cost[s][t])*(int)tujuan.get(t);
                Log.d(key_cost[s][t],"lokasi yang akan di isi");
                Log.d(String.valueOf(tujuan.get(t)),"nilai");

                data.setId(Integer.parseInt(key_cost[s][t]));
                data.setHasil((Integer) tujuan.get(t));

                data_route.add(data);

                cf.put(t,1);
                rf.put(s,1);
                dinamis_sumber--;
                dinamis_tujuan--;
            }
        }//end of while
        Log.d(String.valueOf(sum),"biaya total");
        result_cost.setText("Total Cost : "+String.valueOf(sum));
    }
    //digunakan untuk mengurutkan nilai cost terkecil
    void sort(Map a, Integer n) {
        int j,k,temp;
        Integer tmp;
        for (j=0;j<n;j++){
            for (k=j+1;k<n;k++){
                //Log.d(String.valueOf(a.get(j)),"nilai a["+String.valueOf(j)+"]");
                //Log.d(String.valueOf(a.get(k)),"nilai a["+String.valueOf(k)+"]");
                if ((int)a.get(j)>(int)a.get(k)){
                    temp = (int)a.get(j);
                    a.put(j,a.get(k));
                    a.put(k,temp);
                }
            }
        }
    }
    private void cekIsiList(){
        String str;
        Log.d("jmlah_sumber", Integer.toString(jumlah_sumber));
        Log.d("jmlah_tujuan", Integer.toString(jumlah_tujuan));
        for(int i=0;i<jumlah_sumber;i++){
            for (int j=0;j<jumlah_tujuan;j++) {
                str = String.valueOf(i).concat(String.valueOf(j));
                Log.d(String.valueOf(biaya.get(key_cost[i][j])),"nilai biaya");
            }
        }
        for (int i=0;i<jumlah_tujuan;i++)
            Log.d(String.valueOf(tujuan.get(i)),"tujuan");
        for (int i=0;i<jumlah_sumber;i++)
            Log.d(String.valueOf(sumber.get(i)),"sumber");
    }
}