package editor.ui.parts.menu.filemenu;

import editor.logic.Settings;
import editor.ui.parts.menu.filemenu.settingsdialog.ApplicationSettingsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationFileMenuSettingsItem extends JMenuItem implements ActionListener {
    private static ApplicationSettingsDialog applicationSettingsDialog;

    public ApplicationFileMenuSettingsItem() {
        super("Settings");

        applicationSettingsDialog = new ApplicationSettingsDialog();

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Start " + Settings.getProjectFolder());
        showDialog();
        System.out.println("End " + Settings.getProjectFolder());
    }

    public static void showDialog() {
        applicationSettingsDialog.setVisible(true);
    }
}
