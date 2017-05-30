package namex_project.pens.vammethod.Database.Model;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class  CostModel {
    private int id, id_source, id_destination, id_company, cost;

    public CostModel(int id, int id_source, int id_destination, int id_company, int cost) {
        this.id = id;
        this.id_source = id_source;
        this.id_destination = id_destination;
        this.id_company = id_company;
        this.cost = cost;
    }

    public CostModel(int id_source, int id_destination, int id_company, int cost) {
        this.id_source = id_source;
        this.id_destination = id_destination;
        this.id_company = id_company;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getId_source() {
        return id_source;
    }

    public int getId_destination() {
        return id_destination;
    }

    public int getId_company() {
        return id_company;
    }

    public int getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }
}
