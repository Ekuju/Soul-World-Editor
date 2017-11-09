package editor.ui.parts.menu.filemenu.settingsdialog;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.text.ParseException;

public class IntegerTextField extends JFormattedTextField {
    private NumberFormat numberFormat;

    public IntegerTextField() {
        super(NumberFormat.getIntegerInstance());

        try {
            ((NumberFormat) ((NumberFormatter) ((DefaultFormatterFactory) getFormatterFactory()).getDefaultFormatter()).getFormat()).setGroupingUsed(false);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValue(int defaultValue) {
        setValue((long) defaultValue);
    }

    public int getIntegerValue() {
        return (int) ((long) getValue());
    }
}
