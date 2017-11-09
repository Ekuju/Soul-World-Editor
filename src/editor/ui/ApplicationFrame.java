package editor.ui;

import editor.ui.listeners.Listener;
import editor.ui.parts.menu.ApplicationMenuBar;
import editor.ui.parts.toolbar.ApplicationToolBar;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private ApplicationMenuBar applicationMenuBar;
    private ApplicationToolBar applicationToolBar;

    public ApplicationFrame() {
        super("Soul World Editor");

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Listener listener = new Listener();

        addKeyListener(listener);
        addMouseListener(listener);

        // setContentPane(applicationFrame);

        applicationMenuBar = new ApplicationMenuBar();
        setJMenuBar(applicationMenuBar);

        applicationToolBar = new ApplicationToolBar();
        add(applicationToolBar, BorderLayout.PAGE_START);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(new Dimension(ApplicationFrame.WIDTH, ApplicationFrame.HEIGHT));
        setPreferredSize(new Dimension(ApplicationFrame.WIDTH, ApplicationFrame.HEIGHT));
        setVisible(true);
    }
}
