package editor.ui.parts.menu.filemenu.settingsdialog;

import editor.Application;
import editor.logic.Settings;
import editor.ui.constants.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ApplicationSettingsDialog extends JDialog {
    private IntegerTextField gridRenderSizeTextField;
    private IntegerTextField worldSectionSizeTextField;
    private JTextField projectLocationField;

    private String lastValidProjectLocation;

    public ApplicationSettingsDialog() {
        setLocationRelativeTo(Application.applicationFrame);

        setModal(true);

        JPanel rootPanel = new JPanel();
        add(rootPanel);

        rootPanel.setBorder(UIConstants.EMPTY_BORDER_PADDING);

        BoxLayout boxLayout = new BoxLayout(rootPanel, BoxLayout.Y_AXIS);
        rootPanel.setLayout(boxLayout);

        // grid render size
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

        // world section size
        JPanel worldSectionSizePanel = new JPanel();
        rootPanel.add(worldSectionSizePanel);

        worldSectionSizePanel.setLayout(gridLayout);

        JLabel worldSectionSizeLabel = new JLabel("World Section Size:");
        worldSectionSizePanel.add(worldSectionSizeLabel);

        worldSectionSizeTextField = new IntegerTextField();
        worldSectionSizeTextField.setDefaultValue(Settings.getWorldSectionSize());
        worldSectionSizeTextField.setColumns(12);
        worldSectionSizePanel.add(worldSectionSizeTextField);

        // project location
        lastValidProjectLocation = Settings.getProjectFolder();

        JPanel projectLocationPanel = new JPanel();
        rootPanel.add(projectLocationPanel);

        projectLocationPanel.setLayout(gridLayout);

        JLabel projectLocationLabel = new JLabel("Project Location:");
        projectLocationPanel.add(projectLocationLabel);

        projectLocationField = new JTextField();
        projectLocationPanel.add(projectLocationField);

        projectLocationField.setText(lastValidProjectLocation);
        projectLocationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canonicalizeAndValidateProjectLocation();
            }
        });

        JPanel saveCancelPanel = new JPanel();
        rootPanel.add(saveCancelPanel);

        saveCancelPanel.setLayout(gridLayout);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectLocationField.setText(lastValidProjectLocation);
                setVisible(false);
            }
        });
        saveCancelPanel.add(cancelButton);

        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canonicalizeAndValidateProjectLocation();

                int gridRenderSize = gridRenderSizeTextField.getIntegerValue();
                int worldSectionSize = worldSectionSizeTextField.getIntegerValue();
                String projectLocation = projectLocationField.getText();

                Settings.setGridRenderSize(gridRenderSize);
                Settings.setWorldSectionSize(worldSectionSize);
                Settings.setProjectFolder(projectLocation);

                setVisible(false);
            }
        });
        saveCancelPanel.add(acceptButton);

        pack();
    }

    private void canonicalizeAndValidateProjectLocation() {
        File newProjectLocation = new File(projectLocationField.getText());
        File parentFile = newProjectLocation.getParentFile();
        while (!parentFile.exists()) {
            parentFile = parentFile.getParentFile();
        }

        boolean existsAndCanReadWrite = newProjectLocation.exists() && newProjectLocation.canRead() && newProjectLocation.canWrite();
        boolean doesntExistAndParentCanReadWrite = !newProjectLocation.exists() && parentFile.canRead()
                && parentFile.canWrite();

        if (!existsAndCanReadWrite && !doesntExistAndParentCanReadWrite) {
            System.err.println("Cannot read or write in the new project location.");
            projectLocationField.setText(lastValidProjectLocation);
            projectLocationField.transferFocus();

            return;
        }

        try {
            String newProjectLocationPath = newProjectLocation.getCanonicalPath();
            projectLocationField.setText(newProjectLocationPath);

            lastValidProjectLocation = newProjectLocationPath;
        } catch (IOException e) {
            projectLocationField.setText(lastValidProjectLocation);

            e.printStackTrace();
        }

        projectLocationField.transferFocus();
    }
}
