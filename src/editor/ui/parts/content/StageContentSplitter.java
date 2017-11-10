package editor.ui.parts.content;

import editor.logic.Settings;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.stageproperties.StageCombinedPanel;

import javax.swing.*;
import java.awt.*;

public class StageContentSplitter extends JSplitPane {
    private StageCombinedPanel stageCombinedPanel;
    private ApplicationLibrary applicationLibrary;

    public StageContentSplitter() {
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setBorder(null);

        Dimension minimumSize = new Dimension(0, 32);

        stageCombinedPanel = new StageCombinedPanel();
        stageCombinedPanel.setMinimumSize(minimumSize);
        setTopComponent(stageCombinedPanel);

        applicationLibrary = new ApplicationLibrary();
        applicationLibrary.setMinimumSize(minimumSize);
        setBottomComponent(applicationLibrary);

        setOneTouchExpandable(true);
        setDividerLocation(Settings.getContentDividerPosition());
    }
}
