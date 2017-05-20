package namex_project.pens.vammethod.Activity.EditData.Fragment.Cost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter.CostListAdapter;
import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.Database.Model.CostDataModel;
import namex_project.pens.vammethod.Database.Model.DestinationModel;
import namex_project.pens.vammethod.Database.Model.SourceModel;
import namex_project.pens.vammethod.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {
    String id;
    RecyclerView list_cost;
    private CostListAdapter costListAdapter;
    private ArrayList<DestinationModel> destinationModel;
    private ArrayList<SourceModel> sourceModel;

    public void setId(String id) {
        this.id = id;
    }

    public CostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cost = inflater.inflate(R.layout.fragment_cost, container, false);
        list_cost = (RecyclerView)cost.findViewById(R.id.list_cost);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        destinationModel = db.readDestinationsAll(id);
        sourceModel = db.readSourcesAll(id);
        ArrayList<CostDataModel> dataCost = new ArrayList<>();

        for (int i = 0;i<sourceModel.size();i++){
            for (int j=0;j<destinationModel.size();j++){
                dataCost.add(new CostDataModel(sourceModel.get(i),destinationModel.get(j)));
            }
        }

        costListAdapter = new CostListAdapter(dataCost,getActivity(),this);

        list_cost.setLayoutManager(new LinearLayoutManager(getContext()));
        list_cost.setAdapter(costListAdapter);

        return cost;
    }

}
