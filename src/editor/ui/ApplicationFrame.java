package editor.ui;

import editor.ui.parts.content.StageContentSplitter;
import editor.ui.parts.menu.ApplicationMenuBar;
import editor.ui.parts.toolbar.ApplicationToolBar;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private ApplicationMenuBar applicationMenuBar;
    private ApplicationToolBar applicationToolBar;
    private StageContentSplitter stageContentSplitter;

    public ApplicationFrame() {
        super("Soul World Editor");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        applicationMenuBar = new ApplicationMenuBar();
        setJMenuBar(applicationMenuBar);

        applicationToolBar = new ApplicationToolBar();
        add(applicationToolBar, BorderLayout.PAGE_START);

        stageContentSplitter = new StageContentSplitter();
        add(stageContentSplitter, BorderLayout.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        
        setLocation((width - ApplicationFrame.WIDTH) / 2, (height - ApplicationFrame.HEIGHT) / 2);

        setSize(new Dimension(ApplicationFrame.WIDTH, ApplicationFrame.HEIGHT));
        setPreferredSize(new Dimension(ApplicationFrame.WIDTH, ApplicationFrame.HEIGHT));
        setVisible(true);
    }
}
