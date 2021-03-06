package main.graph.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import main.graph.events.CleanCanvasEvent;

/**
 * Clean canvas Action
 */
public class CleanCanvasAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);

        if (project == null) {
            return;
        }

        MessageBus messageBus = project.getMessageBus();
        messageBus.syncPublisher(CleanCanvasEvent.CLEAN_CANVAS_TOPIC).cleanCanvas();
    }
}
