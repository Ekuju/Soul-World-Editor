package editor.ui.parts.menu.filemenu.settingsdialog;

import editor.Application;
import editor.logic.Settings;
import editor.ui.constants.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationSettingsDialog extends JDialog {
    private IntegerTextField gridRenderSizeTextField;
    private IntegerTextField worldSectionSizeTextField;

    public ApplicationSettingsDialog() {
        setLocationRelativeTo(Application.applicationFrame);

        JPanel rootPanel = new JPanel();
        add(rootPanel);

        rootPanel.setBorder(UIConstants.EMPTY_BORDER_PADDING);

        BoxLayout boxLayout = new BoxLayout(rootPanel, BoxLayout.Y_AXIS);
        rootPanel.setLayout(boxLayout);

        JPanel gridRenderSizePanel = new JPanel();
        rootPanel.add(gridRenderSizePanel);

        GridLayout gridLayout = new GridLayout(1, 0);
        gridRenderSizePanel.setLayout(gridLayout);

        JLabel gridRenderSizeLabel = new JLabel("Grid Render Size:");
        gridRenderSizePanel.add(gridRenderSizeLabel);

        gridRenderSizeTextField = new IntegerTextField();
        gridRenderSizeTextField.setDefaultValue(Settings.getGridRenderSize());
        gridRenderSizeTextField.setColumns(12);
        gridRenderSizePanel.add(gridRenderSizeTextField);

        JPanel worldSectionSizePanel = new JPanel();
        rootPanel.add(worldSectionSizePanel);

        worldSectionSizePanel.setLayout(gridLayout);

        JLabel worldSectionSizeLabel = new JLabel("World Section Size:");
        worldSectionSizePanel.add(worldSectionSizeLabel);

        worldSectionSizeTextField = new IntegerTextField();
        worldSectionSizeTextField.setDefaultValue(Settings.getWorldSectionSize());
        worldSectionSizeTextField.setColumns(12);
        worldSectionSizePanel.add(worldSectionSizeTextField);

        JPanel saveCancelPanel = new JPanel();
        rootPanel.add(saveCancelPanel);

        saveCancelPanel.setLayout(gridLayout);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        saveCancelPanel.add(cancelButton);

        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int gridRenderSize = gridRenderSizeTextField.getIntegerValue();
                int worldSectionSize = worldSectionSizeTextField.getIntegerValue();

                Settings.setGridRenderSize(gridRenderSize);
                Settings.setWorldSectionSize(worldSectionSize);

                setVisible(false);
            }
        });
        saveCancelPanel.add(acceptButton);

        pack();
    }
}
