package namex_project.pens.vammethod.Activity.Company;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import namex_project.pens.vammethod.R;

public class CompanyActivity extends Activity {
    GridView list_company;
    ArrayList<CompanyActivity> data_company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        list_company = (GridView)findViewById(R.id.list_company);



    }
}
