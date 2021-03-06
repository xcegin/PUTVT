package main.highlighter.pattern;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import main.highlighter.util.HashtableCombineUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Controller, which calls the python and pytest decoders and then combines the results
 */
public class PatternController {

    private PatternDecoder patternPythonDecoder;
    private PatternDecoder patternPytestDecoder;

    public PatternController(){

    }

    public Hashtable patternDecode(AnActionEvent anActionEvent) throws IOException {
        this.patternPythonDecoder = new PatternPythonDecoder();
        this.patternPytestDecoder = new PatternPytestDecoder();

        FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();
        VirtualFile virtualFile = FileChooser.chooseFile(fileChooserDescriptor, anActionEvent.getProject(), anActionEvent.getProject().getBaseDir());
        if (virtualFile==null){
            return null;
        }
        Hashtable hashTable = null;
        try {
            if (patternPytestDecoder.patternDecode(virtualFile)){
                hashTable = patternPytestDecoder.getFileKeyDTOObject();
            }
            if (patternPythonDecoder.patternDecode(virtualFile)){
                if (hashTable==null){
                    hashTable = patternPythonDecoder.getFileKeyDTOObject();
                }
                else{
                    HashtableCombineUtil hashtableCombineUtil = new HashtableCombineUtil();
                    hashTable=hashtableCombineUtil.combineHashTables(hashTable, patternPythonDecoder.getFileKeyDTOObject(), patternPythonDecoder.getFileNames());
                }
            }
            else{
                if (hashTable==null) {
                    createErrorHover(anActionEvent, "The file was invalid - could not parse.");
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            createErrorHover(anActionEvent, e.getMessage());
        }
        return hashTable;
    }
    //used in case of external logs and console runs
    public Hashtable patternDecode(String consoleLogs){
        this.patternPythonDecoder = new PatternPythonDecoder();
        this.patternPytestDecoder = new PatternPytestDecoder();
        Hashtable hashTable = null;

            if (patternPytestDecoder.patternDecode(consoleLogs)){
                hashTable = patternPytestDecoder.getFileKeyDTOObject();
            }
            if (patternPythonDecoder.patternDecode(consoleLogs)){
                if (hashTable==null){
                    hashTable = patternPythonDecoder.getFileKeyDTOObject();
                }
                else{
                    HashtableCombineUtil hashtableCombineUtil = new HashtableCombineUtil();
                    hashTable=hashtableCombineUtil.combineHashTables(hashTable, patternPythonDecoder.getFileKeyDTOObject(), patternPythonDecoder.getFileNames());
                }
            }
        return hashTable;

    }

    private void createErrorHover(AnActionEvent anActionEvent, String message){

        StatusBar statusBar = WindowManager.getInstance()
                .getStatusBar(CommonDataKeys.PROJECT.getData(anActionEvent.getDataContext()));

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.ERROR, null)
                .setFadeoutTime(30000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);

    }



}
