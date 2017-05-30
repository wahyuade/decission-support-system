package namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import namex_project.pens.vammethod.Activity.EditData.Fragment.Source.SourceFragment;
import namex_project.pens.vammethod.Database.Model.SourceModel;
import namex_project.pens.vammethod.R;

/**
 * Created by Windows 10 on 19/05/2017.
 */

public class SourceListAdapter extends RecyclerView.Adapter<SourceListAdapter.DSList> {
    private ArrayList<SourceModel> data_list;
    private Activity activity;
    private SourceFragment parent;

    public SourceListAdapter(ArrayList<SourceModel> data_list, Activity activity, SourceFragment parent) {
        this.data_list = data_list;
        this.activity = activity;
        this.parent = parent;
    }

    @Override
    public SourceListAdapter.DSList onCreateViewHolder(ViewGroup parent, int viewType) {
        View company_grid = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ds, parent, false);
        return new DSList(company_grid);
    }

    @Override
    public void onBindViewHolder(SourceListAdapter.DSList holder, final int position) {
        holder.name.setText(data_list.get(position).getName());
        holder.capacity.setText(Integer.toString(data_list.get(position).getCapacity()));

        holder.number.setText(Integer.toString(position+1)+". ");

        holder.item_list_ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, Integer.toString(data_list.get(position).getId()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_list.toArray().length;
    }

    class DSList extends RecyclerView.ViewHolder {
        TextView capacity, name, number;
        LinearLayout item_list_ds;
        private DSList(View view){
            super(view);
            capacity = (TextView)view.findViewById(R.id.capacity);
            name = (TextView)view.findViewById(R.id.name);
            number = (TextView)view.findViewById(R.id.number);
            item_list_ds = (LinearLayout)view.findViewById(R.id.item_list_ds);
        }
    }

    public void addDataSource (SourceModel sourceModel){
        data_list.add(sourceModel);
    }
}
