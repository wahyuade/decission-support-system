package namex_project.pens.vammethod.Activity.EditData.Fragment.Source;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import namex_project.pens.vammethod.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourceFragment extends Fragment {
    String id;

    public SourceFragment() {
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_source, container, false);
    }

}
