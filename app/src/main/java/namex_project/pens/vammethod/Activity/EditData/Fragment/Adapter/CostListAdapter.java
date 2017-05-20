package namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import namex_project.pens.vammethod.Activity.EditData.Fragment.Cost.CostFragment;
import namex_project.pens.vammethod.Database.Model.CostDataModel;
import namex_project.pens.vammethod.Database.Model.DestinationModel;
import namex_project.pens.vammethod.Database.Model.SourceModel;
import namex_project.pens.vammethod.R;

/**
 * Created by Wahyu Ade Sasongko on 5/19/2017.
 */

public class CostListAdapter extends Adapter<CostListAdapter.ListAdapter>{
    private ArrayList<CostDataModel> data_cost;
    private Activity activity;
    private CostFragment costFragment;


    public CostListAdapter(ArrayList<CostDataModel> data_cost, Activity activity, CostFragment costFragment) {
        this.data_cost = data_cost;
        this.activity = activity;
        this.costFragment = costFragment;
    }

    @Override
    public CostListAdapter.ListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View cost_list = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cost,null);
        return new ListAdapter(cost_list);
    }

    @Override
    public void onBindViewHolder(ListAdapter holder, int position) {
        holder.from.setText(data_cost.get(position).getSourceModel().getName());
        holder.to.setText(data_cost.get(position).getDestinationModel().getName());
    }

    @Override
    public int getItemCount() {
        return data_cost.size();
    }

    class ListAdapter extends ViewHolder{
        TextView from,to,cost;
        private ListAdapter(View view){
            super(view);
            from = (TextView)view.findViewById(R.id.from);
            to = (TextView)view.findViewById(R.id.to);
            cost = (TextView)view.findViewById(R.id.cost);
        }
    }

}
