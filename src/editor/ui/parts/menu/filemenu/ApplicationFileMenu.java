package editor.ui.parts.menu.filemenu;

import javax.swing.*;

public class ApplicationFileMenu extends JMenu {
    private ApplicationFileMenuOpenItem applicationFileMenuOpenItem;
    private ApplicationFileMenuSaveItem applicationFileMenuSaveItem;
    private ApplicationFileMenuExportItem applicationFileMenuExportItem;
    private ApplicationFileMenuSettingsItem applicationFileMenuSettingsItem;

    public ApplicationFileMenu() {
        super("File");

        applicationFileMenuOpenItem = new ApplicationFileMenuOpenItem();
        add(applicationFileMenuOpenItem);

        applicationFileMenuSaveItem = new ApplicationFileMenuSaveItem();
        add(applicationFileMenuSaveItem);

        addSeparator();

        applicationFileMenuExportItem = new ApplicationFileMenuExportItem();
        add(applicationFileMenuExportItem);

        addSeparator();

        applicationFileMenuSettingsItem = new ApplicationFileMenuSettingsItem();
        add(applicationFileMenuSettingsItem);
    }
}
