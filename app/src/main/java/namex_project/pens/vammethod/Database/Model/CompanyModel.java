package namex_project.pens.vammethod.Database.Model;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class CompanyModel {
    private int id;
    private String name;

    public CompanyModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CompanyModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
