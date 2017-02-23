import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.messages.MessageBusConnection;
import handlers.DisplayHandler;
import handlers.EditorManagerListener;
import handlers.FileOperationListener;

/**
 * Created by Cegin on 20.2.2017.
 */
public class ProjectPlugin {
    private Project project;

    public DisplayHandler displayHandler;

    public ProjectPlugin(Project project, DisplayHandler displayHandler) {
        this.project = project;
        this.displayHandler = displayHandler;
        registerListeners(displayHandler);

    }

    private void registerListeners(DisplayHandler displayHandler) {
        final MessageBusConnection connect = this.project.getMessageBus().connect(this.project);

        connect.subscribe(EditorManagerListener.FILE_EDITOR_MANAGER, new EditorManagerListener(displayHandler));
        VirtualFileManager.getInstance().addVirtualFileListener(new FileOperationListener(displayHandler));
    }

}
