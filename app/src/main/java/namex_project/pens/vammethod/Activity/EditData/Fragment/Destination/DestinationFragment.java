package namex_project.pens.vammethod.Activity.EditData.Fragment.Destination;


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

import namex_project.pens.vammethod.Activity.EditData.Fragment.Adapter.DestinationListAdapter;
import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.Database.Model.DestinationModel;
import namex_project.pens.vammethod.R;

public class DestinationFragment extends Fragment {
    private RecyclerView list_destination;
    private String id;
    private FloatingActionButton add_destination;
    DestinationListAdapter destinationListAdapter;
    View emptyView;

    public DestinationFragment() {}
    private ArrayList<DestinationModel> data_destination = new ArrayList<>();
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View destination = inflater.inflate(R.layout.fragment_destination, container, false);
        list_destination = (RecyclerView)destination.findViewById(R.id.list_destination);
        add_destination = (FloatingActionButton)destination.findViewById(R.id.add_destination);
        emptyView = destination.findViewById(R.id.empty_view);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        data_destination = db.readDestinationsAll(id);
        destinationListAdapter = new DestinationListAdapter(data_destination, getActivity(), this);

        list_destination.setLayoutManager(new LinearLayoutManager(getContext()));

        list_destination.setAdapter(destinationListAdapter);

        add_destination.setOnClickListener(new View.OnClickListener() {
            EditText name, capacity;
            Button save_destination;
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final AlertDialog add_company = new AlertDialog.Builder(getActivity()).create();
                add_company.setView(inflater.inflate(R.layout.add_destination, null));
                add_company.show();

                name = (EditText)add_company.findViewById(R.id.name);
                capacity = (EditText)add_company.findViewById(R.id.capacity);
                save_destination = (Button)add_company.findViewById(R.id.save_destination);

                save_destination.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!name.getText().toString().isEmpty() && !capacity.getText().toString().isEmpty()){
                            DestinationModel data_insert = new DestinationModel(Integer.parseInt(id),Integer.parseInt(capacity.getText().toString()),name.getText().toString());
                            DatabaseHandler db = new DatabaseHandler(getActivity());
                            String data_id = db.insertDestination(data_insert);
                            data_insert.setId(Integer.parseInt(data_id));
                            destinationListAdapter.addDataDestination(data_insert);
                            destinationListAdapter.notifyDataSetChanged();
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
        return destination;
    }

    public void check() {
        if (destinationListAdapter.getItemCount() == 0) {
            list_destination.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            list_destination.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}
