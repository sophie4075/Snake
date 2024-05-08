import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PauseScene extends Scene {

    public MListener mouseListener;
    public BufferedImage title, play, playPressed, exit, exitPressed;

    public BufferedImage playCurrentImg, exitCurrentImg;
    public Rectangle playRect, exitRect, titleRect;

    public MusicPlayer pauseMusic;


    public PauseScene(MListener mouseListener) {

        this.mouseListener = mouseListener;

        try {
            BufferedImage spriteSheet = ImageIO.read(new File("assets/pauseSprite.png"));
            pauseMusic = new MusicPlayer("assets/Elevator-music_chosic.com_.wav");
            pauseMusic.setVolume(0.2f);
            title = spriteSheet.getSubimage(0, 297, 494, 152);
            play = spriteSheet.getSubimage(1, 130, 296, 74);
            playPressed = spriteSheet.getSubimage(313, 130, 296, 74);
            exit = spriteSheet.getSubimage(0, 0, 505, 89);
            exitPressed = spriteSheet.getSubimage(455, 208, 505, 89);
        } catch (Exception e) {
            e.printStackTrace();
        }

        titleRect = new Rectangle(100, 80, 600, 200);
        playRect = new Rectangle(250, 320, 250, 80);
        exitRect = new Rectangle(220, 470, 330, 100);
    }

    @Override
    public void update(double deltaTime) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {


        if (mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width
                && mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height) {
            playCurrentImg = playPressed;

            if (mouseListener.isPressed) {
                GameScene.setPaused(false);
                pauseMusic.stop();
                Window.window.changeState(4);
                GameScene.getGameMusic().play();
                GameScene.getGameMusic().clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        } else {
            playCurrentImg = play;
        }

        if (mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width
                && mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height) {
            exitCurrentImg = exitPressed;

            if (mouseListener.isPressed) {
                pauseMusic.stop();
                Thread.sleep(300);
                Window.getWindow().changeState(0);
                Window.getWindow().setTitle(Constants.SCREEN_TITLE);

            }

        } else {
            exitCurrentImg = exit;
        }
    }

    @Override
    public void draw(Graphics graphic) {
        graphic.setColor(Color.decode("#b592fb"));
        sceneDrawer(graphic, title, titleRect, playCurrentImg, playRect, exitCurrentImg, exitRect);
    }

    static void sceneDrawer(Graphics graphic, BufferedImage title, Rectangle titleRect, BufferedImage playCurrentImg, Rectangle playRect, BufferedImage exitCurrentImg, Rectangle exitRect) {
        MenuScene.sceneDrawer(graphic, title, titleRect, playCurrentImg, playRect, exitCurrentImg, exitRect);
    }
}



