package namex_project.pens.vammethod.Activity.EditData.Fragment.Cost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import namex_project.pens.vammethod.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {
    String id;
    RecyclerView list_cost;
    public void setId(String id) {
        this.id = id;
    }

    public CostFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cost = inflater.inflate(R.layout.fragment_cost, container, false);
        list_cost = (RecyclerView)cost.findViewById(R.id.list_cost);




        return cost;
    }

}
