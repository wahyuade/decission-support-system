package namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

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
        View cost_list = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cost,parent,false);
        return new ListAdapter(cost_list);
    }

    @Override
    public void onBindViewHolder(final ListAdapter holder, final int position) {
        holder.from.setText(data_cost.get(position).getSourceModel().getName());
        holder.to.setText(data_cost.get(position).getDestinationModel().getName());
        holder.item_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = activity.getLayoutInflater();
                final AlertDialog edit_cost =  new AlertDialog.Builder(activity).create();
                View interface_cost = inflater.inflate(R.layout.edit_cost,null);
                edit_cost.setView(interface_cost);

                TextView from = (TextView)interface_cost.findViewById(R.id.from);
                TextView to = (TextView)interface_cost.findViewById(R.id.to);
                final EditText cost = (EditText)interface_cost.findViewById(R.id.cost);

                Button save_button_cost = (Button)interface_cost.findViewById(R.id.save_cost);

                from.setText(data_cost.get(position).getSourceModel().getName());
                to.setText(data_cost.get(position).getDestinationModel().getName());

                save_button_cost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cost_data = cost.getText().toString();
                        if(!cost_data.isEmpty()){
                            holder.cost.setText(cost_data);


                            edit_cost.dismiss();
                        }else{
                            Toast.makeText(activity, "Please insert a value.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                edit_cost.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_cost.size();
    }

    class ListAdapter extends ViewHolder{
        TextView from,to,cost;
        LinearLayout item_cost;
        private ListAdapter(View view){
            super(view);
            from = (TextView)view.findViewById(R.id.from);
            to = (TextView)view.findViewById(R.id.to);
            cost = (TextView)view.findViewById(R.id.cost);
            item_cost = (LinearLayout)view.findViewById(R.id.item_cost);
        }
    }

}
