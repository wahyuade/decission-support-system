package namex_project.pens.vammethod.Activity.Company;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import namex_project.pens.vammethod.Database.Model.CompanyModel;
import namex_project.pens.vammethod.R;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class CompanyGridAdapter extends BaseAdapter{
    private Activity activity;
    private ArrayList<CompanyModel> data_company;

    public CompanyGridAdapter(Activity activity, ArrayList<CompanyModel> data_company) {
        this.activity = activity;
        this.data_company = data_company;
    }

    @Override
    public int getCount() {
        return data_company.toArray().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        ImageView company_photo;
        TextView company_name;
        LinearLayout company_item;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            grid = new View(activity);
            grid = inflater.inflate(R.layout.company_grid, null);

            company_photo = (ImageView)grid.findViewById(R.id.company_photo);
            company_name = (TextView)grid.findViewById(R.id.company_name);

        }
        return null;
    }
}
