package namex_project.pens.vammethod.Database.Model;

/**
 * Created by Wahyu Ade Sasongko on 5/20/2017.
 */

public class CostDataModel {
    private SourceModel sourceModel;
    private DestinationModel destinationModel;

    public CostDataModel(SourceModel sourceModel, DestinationModel destinationModel) {
        this.sourceModel = sourceModel;
        this.destinationModel = destinationModel;
    }

    public SourceModel getSourceModel() {
        return sourceModel;
    }

    public DestinationModel getDestinationModel() {
        return destinationModel;
    }
}
