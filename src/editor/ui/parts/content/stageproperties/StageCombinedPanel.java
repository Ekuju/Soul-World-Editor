package editor.ui.parts.content.stageproperties;

import editor.ui.parts.content.stageproperties.stage.RenderingStage;

import javax.swing.*;
import java.awt.*;

public class StageCombinedPanel extends JPanel {
    public static RenderingStage renderingStage;
    
    public StageCombinedPanel() {
        GridLayout gridLayout = new GridLayout(1, 1);
        setLayout(gridLayout);
        
        renderingStage = new RenderingStage();
        add(renderingStage);
    }
}
