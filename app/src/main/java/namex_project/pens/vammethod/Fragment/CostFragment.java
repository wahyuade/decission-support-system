package namex_project.pens.vammethod.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import namex_project.pens.vammethod.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {


    public CostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cost, container, false);
    }

}
