package namex_project.pens.vammethod.Database.Model;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class DestinationModel {
    private int id, id_company, needed_num;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public DestinationModel(int id_company, int needed_num, String name) {
        this.id_company = id_company;
        this.needed_num = needed_num;
        this.name = name;
    }

    public DestinationModel(int id, int id_company, int needed_num, String name) {
        this.id = id;
        this.id_company = id_company;
        this.needed_num = needed_num;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getId_company() {
        return id_company;
    }

    public int getNeeded_num() {
        return needed_num;
    }

    public String getName() {
        return name;
    }
}
