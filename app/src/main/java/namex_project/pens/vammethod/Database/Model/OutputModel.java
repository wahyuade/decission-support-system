package namex_project.pens.vammethod.Database.Model;

/**
 * Created by Windows 10 on 31/05/2017.
 */

public class OutputModel {
    private SourceModel sourceModel;
    private DestinationModel destinationModel;
    private int id, hasil;

    public OutputModel(int id, int hasil) {
        this.id = id;
        this.hasil = hasil;
    }

    public int getId() {
        return id;
    }

    public int getHasil() {
        return hasil;
    }

    public SourceModel getSourceModel() {
        return sourceModel;
    }

    public DestinationModel getDestinationModel() {
        return destinationModel;
    }

    public void setSourceModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    public void setDestinationModel(DestinationModel destinationModel) {
        this.destinationModel = destinationModel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHasil(int hasil) {
        this.hasil = hasil;
    }

    public OutputModel() {
    }
}
