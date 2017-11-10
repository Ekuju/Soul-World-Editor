package editor.ui;

import editor.ui.listeners.Listener;
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

        Listener listener = new Listener();

        addKeyListener(listener);
        addMouseListener(listener);

        applicationMenuBar = new ApplicationMenuBar();
        setJMenuBar(applicationMenuBar);

        applicationToolBar = new ApplicationToolBar();
        add(applicationToolBar, BorderLayout.PAGE_START);

        stageContentSplitter = new StageContentSplitter();
        add(stageContentSplitter, BorderLayout.CENTER);

        setSize(new Dimension(ApplicationFrame.WIDTH, ApplicationFrame.HEIGHT));
        setPreferredSize(new Dimension(ApplicationFrame.WIDTH, ApplicationFrame.HEIGHT));
        setVisible(true);
    }
}
