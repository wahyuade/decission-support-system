package namex_project.pens.vammethod.Database.Model;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class SourceModel {
    private int id, id_company, capacity;
    private String name;

    public SourceModel(int id, int id_company, int capacity, String name) {
        this.id = id;
        this.id_company = id_company;
        this.capacity = capacity;
        this.name = name;
    }

    public SourceModel(int id_company, int capacity, String name) {
        this.id_company = id_company;
        this.capacity = capacity;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getId_company() {
        return id_company;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }
}
