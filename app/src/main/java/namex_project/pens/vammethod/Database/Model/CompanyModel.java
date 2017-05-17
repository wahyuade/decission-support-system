package namex_project.pens.vammethod.Database.Model;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class CompanyModel {
    private int id;
    private byte[] photo;
    private String name;

    public CompanyModel(int id, byte[] photo, String name) {
        this.id = id;
        this.photo = photo;
        this.name = name;
    }

    public CompanyModel(byte[] photo, String name) {
        this.photo = photo;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
