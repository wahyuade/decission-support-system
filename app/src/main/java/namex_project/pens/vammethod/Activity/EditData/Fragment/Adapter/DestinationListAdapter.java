package namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import namex_project.pens.vammethod.Activity.EditData.Fragment.Destination.DestinationFragment;
import namex_project.pens.vammethod.Database.Model.DestinationModel;
import namex_project.pens.vammethod.R;

/**
 * Created by Wahyu Ade Sasongko on 5/19/2017.
 */

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.DSList> {
    private ArrayList<DestinationModel> data_list;
    private Activity activity;
    private DestinationFragment parent;

    public DestinationListAdapter(ArrayList<DestinationModel> data_list, Activity activity, DestinationFragment parent) {
        this.data_list = data_list;
        this.activity = activity;
        this.parent = parent;
    }

    @Override
    public DestinationListAdapter.DSList onCreateViewHolder(ViewGroup parent, int viewType) {
        View company_grid = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ds, parent, false);
        return new DSList(company_grid);
    }

    @Override
    public void onBindViewHolder(DestinationListAdapter.DSList holder, final int position) {
        holder.name.setText(data_list.get(position).getName());
        holder.capacity.setText(Integer.toString(data_list.get(position).getNeeded_num()));

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

    class DSList extends ViewHolder {
        TextView capacity, name;
        LinearLayout item_list_ds;
        private DSList(View view){
            super(view);
            capacity = (TextView)view.findViewById(R.id.capacity);
            name = (TextView)view.findViewById(R.id.name);
            item_list_ds = (LinearLayout)view.findViewById(R.id.item_list_ds);
        }
    }

    public void addDataDestination(DestinationModel destinationModel){
        data_list.add(destinationModel);
    }
}
