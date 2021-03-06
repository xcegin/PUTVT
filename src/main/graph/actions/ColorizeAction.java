package main.graph.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import main.graph.events.ColorizeEvent;

/**
 * Only recolorize, no reposition - NOT WORKING
 */
public class ColorizeAction extends AnAction{


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = getEventProject(anActionEvent);

        if (project == null) {
            return;
        }

        MessageBus messageBus = project.getMessageBus();
        messageBus.syncPublisher(ColorizeEvent.COLORIZE_EVENT_TOPIC).colorize();
    }
}
