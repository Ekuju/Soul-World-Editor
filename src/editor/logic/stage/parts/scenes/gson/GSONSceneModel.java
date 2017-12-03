package editor.logic.stage.parts.scenes.gson;

import editor.logic.stage.parts.instances.ImageAssetInstance;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GSONSceneModel {
    public List<GSONImageAssetInstanceModel> imageInstances;

    public GSONSceneModel(List<ImageAssetInstance> imageAssetInstances) {
        imageInstances = new ArrayList<GSONImageAssetInstanceModel>();

        for (ImageAssetInstance imageInstance : imageAssetInstances) {
            imageInstances.add(new GSONImageAssetInstanceModel(
                    imageInstance.getChecksum(),
                    imageInstance.getPosition(),
                    imageInstance.getScale(),
                    imageInstance.getAnchor()
            ));
        }
    }

    public List<ImageAssetInstance> getImageAssetInstances() {
        List<ImageAssetInstance> imageAssetInstances = new ArrayList<ImageAssetInstance>();
        for (GSONImageAssetInstanceModel instanceModel : imageInstances) {
            ImageAssetInstance imageAssetInstance = new ImageAssetInstance(instanceModel.checksum);
            imageAssetInstance.setPosition(instanceModel.position.x, instanceModel.position.y);
            imageAssetInstance.setScale(instanceModel.scale.x, instanceModel.scale.y);
            imageAssetInstance.setAnchor(instanceModel.anchor.x, instanceModel.anchor.y);

            imageAssetInstances.add(imageAssetInstance);
        }

        return imageAssetInstances;
    }

    private class GSONImageAssetInstanceModel {
        public String checksum;
        public Point position;
        public Point2D.Double scale;
        public Point2D.Double anchor;

        public GSONImageAssetInstanceModel(String checksum, Point position, Point2D.Double scale, Point2D.Double anchor) {
            this.checksum = checksum;
            this.position = position;
            this.scale = scale;
            this.anchor = anchor;
        }
    }
}
