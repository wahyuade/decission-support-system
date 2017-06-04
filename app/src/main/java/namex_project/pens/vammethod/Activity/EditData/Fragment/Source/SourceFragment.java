package namex_project.pens.vammethod.Activity.EditData.Fragment.Source;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter.SourceListAdapter;
import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.Database.Model.SourceModel;
import namex_project.pens.vammethod.R;

import static namex_project.pens.vammethod.R.id.list_source;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourceFragment extends Fragment {
    private RecyclerView list_source;
    private String id;
    private FloatingActionButton add_source;
    SourceListAdapter sourceListAdapter;
    View emptyView;

    public SourceFragment() {}
    private ArrayList<SourceModel> data_source = new ArrayList<>();
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View source = inflater.inflate(R.layout.fragment_source, container, false);
        list_source = (RecyclerView)source.findViewById(R.id.list_source);
        add_source = (FloatingActionButton)source.findViewById(R.id.add_source);
        emptyView = source.findViewById(R.id.empty_view);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        data_source = db.readSourcesAll(id);
        sourceListAdapter = new SourceListAdapter(data_source, getActivity(), SourceFragment.this);

        list_source.setLayoutManager(new LinearLayoutManager(getContext()));

        list_source.setAdapter(sourceListAdapter);

        add_source.setOnClickListener(new View.OnClickListener() {
            EditText name, capacity;
            Button save_source;
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final AlertDialog add_company = new AlertDialog.Builder(getActivity()).create();
                add_company.setView(inflater.inflate(R.layout.add_source, null));
                add_company.show();

                name = (EditText)add_company.findViewById(R.id.name);
                capacity = (EditText)add_company.findViewById(R.id.capacity);
                save_source = (Button)add_company.findViewById(R.id.save_source);

                save_source.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!name.getText().toString().isEmpty() && !capacity.getText().toString().isEmpty()){
                            SourceModel data_insert = new SourceModel(Integer.parseInt(id),Integer.parseInt(capacity.getText().toString()),name.getText().toString());
                            DatabaseHandler db = new DatabaseHandler(getActivity());
                            String data_id = db.insertSource(data_insert);
                            data_insert.setId(Integer.parseInt(data_id));
                            sourceListAdapter.addDataSource(data_insert);
                            sourceListAdapter.notifyDataSetChanged();
                            check();
                            add_company.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Please insert data first", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        check();
        return source;
    }

    public void check() {
        if (sourceListAdapter.getItemCount() == 0) {
            list_source.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            list_source.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

}
