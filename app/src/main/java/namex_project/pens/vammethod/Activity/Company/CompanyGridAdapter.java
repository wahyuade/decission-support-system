package namex_project.pens.vammethod.Activity.Company;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import namex_project.pens.vammethod.Database.Model.CompanyModel;
import namex_project.pens.vammethod.R;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class CompanyGridAdapter extends RecyclerView.Adapter<CompanyGridAdapter.CompanyGrid>{
    private Activity activity;
    private ArrayList<CompanyModel> data_company;
    private CompanyActivity companyActivity;

    public CompanyGridAdapter(Activity activity, ArrayList<CompanyModel> data_company, CompanyActivity companyActivity) {
        this.activity = activity;
        this.data_company = data_company;
        this.companyActivity = companyActivity;
    }

    public class CompanyGrid extends ViewHolder{
        ImageView company_photo;
        TextView company_name;
        LinearLayout company_item;
        public CompanyGrid(View view){
            super(view);
            company_item = (LinearLayout)view.findViewById(R.id.company_item);
            company_photo = (ImageView)view.findViewById(R.id.company_photo);
            company_name = (TextView)view.findViewById(R.id.company_name);
        }
    }


    public void addItemCompany(CompanyModel data){
        data_company.add(data);
    }

    @Override
    public CompanyGrid onCreateViewHolder(ViewGroup parent, int viewType) {
        View company_grid = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_grid, parent, false);
        return new CompanyGrid(company_grid);
    }

    @Override
    public void onBindViewHolder(CompanyGrid holder, int position) {
        holder.company_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Ini perusahaan gan", Toast.LENGTH_SHORT).show();
            }
        });
        holder.company_photo.setImageBitmap(BitmapFactory.decodeByteArray(data_company.get(position).getPhoto(),0,data_company.get(position).getPhoto().length));
        holder.company_name.setText(data_company.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data_company.toArray().length;
    }
}
