package namex_project.pens.vammethod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.Database.Model.DestinationModel;
import namex_project.pens.vammethod.Database.Model.SourceModel;

import static namex_project.pens.vammethod.Activity.EditData.EditDataActivity.id;

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


    int input_jumlah_sumber = 3;//m
    int input_jumlah_tujuan = 3;//n
    int[][] input_biaya = {{6,8,10},{7,11,11},{4,5,12}};
    int[] input_tujuan = {200,100,300};
    int[] input_sumber = {150,175,275};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vam_method);

        getSupportActionBar().hide();

        Intent data = getIntent();
        id_company = Integer.parseInt(data.getStringExtra("id_company"));
        DatabaseHandler db = new DatabaseHandler(this);
        jumlah_sumber = db.totalSource(id_company);
        jumlah_tujuan = db.totalDestination(id_company);

        metodeVam();

    }
    private void rubahMenjadiArrayList(){
        String str;
        jumlah_tujuan =input_jumlah_tujuan;
        jumlah_sumber=input_jumlah_sumber;
        for(int i=0;i<jumlah_sumber;i++){
            for (int j=0;j<jumlah_tujuan;j++) {
                str = String.valueOf(i).concat(String.valueOf(j));
                biaya.put(str,input_biaya[i][j]);
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
        String str;
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
                            str = String.valueOf(i).concat(String.valueOf(j));
                            a.put(k++,biaya.get(str));
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
                            str = String.valueOf(j).concat(String.valueOf(i));
                            //Log.d(String.valueOf(biaya.get(str)),"nilai biaya.get(str)=======================================");
                            a.put(k++, biaya.get(str));
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
                            str= String.valueOf(i).concat(String.valueOf(p));
                            if(min> (int) biaya.get(str)){
                                //min digunakan untuk mencari nilai cost terkecil
                                min= (int) biaya.get(str);
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
                            str= String.valueOf(p).concat(String.valueOf(i));
                            if (min>(int) biaya.get(str)){
                                //min digunakan untuk mencari nilai cost terkecil
                                min=(int) biaya.get(str);
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
                //sum = digunakan untuk menyimpan jumlah biaya transportasi
                str= String.valueOf(s).concat(String.valueOf(t));
                sum=sum+ (int)biaya.get(str) * (int) sumber.get(s);
                Log.d(str,"lokasi yang akan di isi");
                Log.d(String.valueOf(sumber.get(s)),"nilai");
                tujuan.put(t,(int)tujuan.get(t)-(int)sumber.get(s));
                //rf digunakan untuk mengandai bahwa baris tsb tidak akan diproses kembali saat pencarian selisih
                rf.put(s,1);
                //jumlah baris yang akan dikurangi
                dinamis_sumber--;
            }

            //Jika nilai source capacity lebih dari dest capacity
            else if((int)sumber.get(s)>(int)tujuan.get(t)){
                str = String.valueOf(s).concat(String.valueOf(t));
                sum=sum+ (int)biaya.get(str)*(int)tujuan.get(t);
                Log.d(str,"lokasi yang akan di isi");
                Log.d(String.valueOf(tujuan.get(t)),"nilai");
                sumber.put(s,(int)sumber.get(s)-(int)tujuan.get(t));
                //rf digunakan untuk mengandai bahwa kolom tsb tidak akan diproses kembali saat pencarian selisih
                cf.put(t,1);
                //jumlah kolom akan dikurangi
                dinamis_tujuan--;
            }

            //Jika source capacity = dest capacity
            else if((int) sumber.get(s)==(int) tujuan.get(t)){
                str = String.valueOf(s).concat(String.valueOf(t));
                sum=sum+ (int)biaya.get(str)*(int)tujuan.get(t);
                Log.d(str,"lokasi yang akan di isi");
                Log.d(String.valueOf(tujuan.get(t)),"nilai");
                cf.put(t,1);
                rf.put(s,1);
                dinamis_sumber--;
                dinamis_tujuan--;
            }
        }//end of while
        Log.d(String.valueOf(sum),"biaya total");
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
                Log.d(String.valueOf(biaya.get(str)),"nilai biaya");
            }
        }
        for (int i=0;i<jumlah_tujuan;i++)
            Log.d(String.valueOf(tujuan.get(i)),"tujuan");
        for (int i=0;i<jumlah_sumber;i++)
            Log.d(String.valueOf(sumber.get(i)),"sumber");
    }
}
