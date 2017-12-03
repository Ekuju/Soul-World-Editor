package editor.ui.parts.content.stageproperties.stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Trent on 11/28/2017.
 */
public class StageListener implements KeyListener, MouseWheelListener {
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case KeyEvent.VK_SPACE: {
                StageDraggingManager.beginDrag(RenderingStage.getStagePosition());
                
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case KeyEvent.VK_SPACE: {
                StageDraggingManager.endDrag();

                break;
            }

            case KeyEvent.VK_ESCAPE: {
                RenderingStage.popScene();
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double amount = e.getPreciseWheelRotation();
        if (amount == 0) {
            return;
        }

        // on mac you scroll too fast
        amount = Math.signum(amount);

        RenderingStage.zoom(amount);
    }
}
