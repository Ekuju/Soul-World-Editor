package editor.ui.parts.menu;

import editor.ui.parts.menu.filemenu.ApplicationFileMenu;

import javax.swing.*;

public class ApplicationMenuBar extends JMenuBar {
    private ApplicationFileMenu applicationFileMenu;

    public ApplicationMenuBar() {
        applicationFileMenu = new ApplicationFileMenu();
        add(applicationFileMenu);
    }
}
