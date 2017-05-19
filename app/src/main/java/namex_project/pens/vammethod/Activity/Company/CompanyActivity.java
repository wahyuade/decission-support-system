package namex_project.pens.vammethod.Activity.Company;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.Database.Model.CompanyModel;
import namex_project.pens.vammethod.R;

public class CompanyActivity extends Activity {
    RecyclerView list_company;
    FloatingActionButton add_company;
    CompanyGridAdapter company_adapter;

    private static int RESULT_LOAD_IMAGE = 156;
    Uri selected_image;
    String picturePath;
    File image;
    FileInputStream img;
    byte[] image_data;
    ImageView company_photo;
    Button upload_photo;

    ArrayList<CompanyModel> data_company;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        list_company = (RecyclerView) findViewById(R.id.list_company);
        add_company = (FloatingActionButton)findViewById(R.id.add_company);

        DatabaseHandler db = new DatabaseHandler(CompanyActivity.this);
        data_company = db.readCompanyAll();
        db.close();

        company_adapter = new CompanyGridAdapter(CompanyActivity.this, data_company, this);

        list_company.setLayoutManager(new GridLayoutManager(this,2));

        list_company.setAdapter(company_adapter);

        add_company.setOnClickListener(new View.OnClickListener() {
            Button select_photo;
            EditText company_name;
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final AlertDialog add_company = new AlertDialog.Builder(CompanyActivity.this).create();
                add_company.setView(inflater.inflate(R.layout.add_company, null));
                add_company.show();

                upload_photo = (Button)add_company.findViewById(R.id.upload_photo);
                select_photo = (Button)add_company.findViewById(R.id.company_select_photo);
                company_name = (EditText)add_company.findViewById(R.id.company_name);
                company_photo = (ImageView)add_company.findViewById(R.id.company_photo);

                select_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CompanyActivity.this, "Pilih image", Toast.LENGTH_SHORT).show();
                        Intent open_gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(open_gallery, RESULT_LOAD_IMAGE);
                    }
                });

                upload_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = company_name.getText().toString();
                        if(!name.isEmpty() && image_data!=null){
                            CompanyModel company = new CompanyModel(image_data,name);

                            DatabaseHandler db = new DatabaseHandler(CompanyActivity.this);
                            String id = db.insertCompany(company);
                            company.setId(Integer.parseInt(id));
                            company_adapter.addItemCompany(company);
                            company_adapter.notifyDataSetChanged();
                            db.close();

                            Toast.makeText(CompanyActivity.this, "Company successfull inserted!", Toast.LENGTH_SHORT).show();
                            add_company.dismiss();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null){
            selected_image = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selected_image,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();


            image = new File(picturePath);
            try{
                img = new FileInputStream(image);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(img);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 , baos);
            image_data = baos.toByteArray();

            company_photo.setVisibility(View.VISIBLE);
            company_photo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            upload_photo.setVisibility(View.VISIBLE);
        }
    }
}
