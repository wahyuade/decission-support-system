package namex_project.pens.vammethod.Activity.Route;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter.CostListAdapter;
import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.Database.Model.CostDataModel;
import namex_project.pens.vammethod.Database.Model.CostModel;
import namex_project.pens.vammethod.Database.Model.OutputModel;
import namex_project.pens.vammethod.R;

/**
 * Created by Wahyu Ade Sasongko on 5/31/2017.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ListAdapter> {
    ArrayList<OutputModel> data_proses;
    Activity activity;


    public RouteAdapter(Activity activity,ArrayList<OutputModel> data_proses) {
        this.data_proses = data_proses;
        this.activity = activity;
    }

    @Override
    public RouteAdapter.ListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View route_list = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_route,parent,false);
        return new RouteAdapter.ListAdapter(route_list);
    }

    @Override
    public void onBindViewHolder(RouteAdapter.ListAdapter holder, int position) {
        DatabaseHandler db = new DatabaseHandler(activity);
        CostModel dataModel = db.readCostWhereId(data_proses.get(position).getId());
        data_proses.get(position).setDestinationModel(db.readDestination(dataModel.getId_destination()));
        data_proses.get(position).setSourceModel(db.readSource(dataModel.getId_source()));

        holder.content.setText(Integer.toString(data_proses.get(position).getHasil()));
        holder.destination.setText(data_proses.get(position).getDestinationModel().getName());
        holder.source.setText(data_proses.get(position).getSourceModel().getName());

        db.close();
    }

    @Override
    public int getItemCount() {
        return data_proses.size();
    }
    class ListAdapter extends RecyclerView.ViewHolder {
        TextView source,destination,content;
        private  ListAdapter(View view){
            super(view);
            source = (TextView)view.findViewById(R.id.source);
            destination = (TextView)view.findViewById(R.id.destination);
            content = (TextView)view.findViewById(R.id.content);
        }
    }

}
