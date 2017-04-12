package highlighter.highlighters;

import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;

import java.awt.*;

public class ErrorStripeMarkHighlighter {
    public void highlight(RangeHighlighter lineHighlighter, TextAttributes textAttributes, Color color, String testName) {
        if (testName != null) {
            lineHighlighter.setErrorStripeMarkColor(color);
            String tooltip = testName;
            lineHighlighter.setErrorStripeTooltip("The highlighter.errors on this line are:\n" + tooltip);
        }
    }

}
