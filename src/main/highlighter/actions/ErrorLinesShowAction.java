package main.highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.ui.Messages;
import main.highlighter.highlighters.ErrorManageFileControler;
import main.highlighter.highlighters.HighlightingMainController;
import main.highlighter.util.StringPytestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Show error lines for visualized source code lines
 */
public class ErrorLinesShowAction extends AnAction {

    final String logMessage = "LOG(s) for Error:" + "\n" + "****************" + "\n";
    final String noLogsFound = "No logs for this line.";

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        int lineN = logicalPosition.line + 1;

        ErrorManageFileControler errorManageFileControler = ErrorManageFileControler.getInstance(null);
        HighlightingMainController highlightingMainController = HighlightingMainController.getInstance(null);

        StringPytestUtil fileUtil = errorManageFileControler.getStringPytestUtilForFileName(editor);
        StringPytestUtil consoleUtil = highlightingMainController.getStringPytestUtilForFileName(editor);

        StringPytestUtil utilUsed;

        if (fileUtil == null && consoleUtil == null){
            return;
        }

        if (fileUtil != null && consoleUtil != null){
            utilUsed = fileUtil;
        }
        else{
            if (consoleUtil != null){
                utilUsed=consoleUtil;
            }
            else{
                utilUsed=fileUtil;
            }
        }
        int count=0;
        List<String[]> runTimeLinesList = new ArrayList<>();
        for (Integer integer : utilUsed.getLineNumber()){
            if (integer == lineN){
                runTimeLinesList.add(utilUsed.getLinesDuringRuntime().get(count));
            }
            count++;
        }

        if (runTimeLinesList.size()==0){
            Messages.showInfoMessage(noLogsFound, noLogsFound);
            return;
        }

        String message="";

        for (String[] lines : runTimeLinesList){
            message+=logMessage;
            for (String line : lines){
                message+=line;
                message+='\n';
            }
            message+='\n';
        }

        Messages.showErrorDialog(message, "Error Logs for line no." + lineN + "");
    }
}
