import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface EventSet {
    public void onMouseMoved(MouseEvent event);
    public void onScrollEvent(ScrollEvent event);
    public void onKeyDownEvent(KeyEvent event);
    public void onKeyUpEvent(KeyEvent event);
}
