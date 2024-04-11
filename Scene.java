import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Graphics;
import java.io.IOException;

public abstract class Scene {
    public abstract void update(double deltaTime) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException;
    public abstract void draw (Graphics graphic);
}
