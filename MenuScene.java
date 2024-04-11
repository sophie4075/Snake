import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MenuScene extends Scene{

    public MListener mouseListener;
    public BufferedImage title, play, playPressed, exit, exitPressed, howToPlay, howToPlayPressed , playCurrentImg, exitCurrentImg, howToCurrentImg;
    public Rectangle playRect, exitRect, titleRect, howtoPlayRect;

    public MusicPlayer menuMusic;



    public MenuScene(MListener mouseListener){
        this.mouseListener = mouseListener;

        try {
            menuMusic = new MusicPlayer("assets/2021-08-17_-_8_Bit_Nostalgia_-_www.FesliyanStudios.com.wav");
            menuMusic.setVolume(0.5f);
            BufferedImage spriteSheet = ImageIO.read(new File("assets/Title.png"));
            title = spriteSheet.getSubimage(1, 292, 607, 186);
            howToPlay = spriteSheet.getSubimage(489, 4, 470, 71);
            howToPlayPressed = spriteSheet.getSubimage(490,217, 470, 71);
            play =spriteSheet.getSubimage(1, 130, 200, 82);
            playPressed = spriteSheet.getSubimage(500, 130, 200, 82);
            exit = spriteSheet.getSubimage(0, 4, 200, 85);
            exitPressed = spriteSheet.getSubimage(261, 4, 200, 85);
        }catch (Exception e){
            e.printStackTrace();
        }

        playCurrentImg = play;
        howToCurrentImg = howToPlay;
        exitCurrentImg = exit;


        titleRect = new Rectangle(145, 50, 500, 140);
        playRect = new Rectangle(290, 230, 220, 80);
        exitRect = new Rectangle(292, 470, 220, 80);
        howtoPlayRect = new Rectangle(200, 350, 400, 80);


    }

    @Override
    public void update(double deltaTime) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        if(mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width
        && mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height){
           playCurrentImg = playPressed;

           if(mouseListener.isPressed){
               menuMusic.stop();
               Window.getWindow().changeState(1);
           }

        } else {
            playCurrentImg = play;
        }


        if(mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width
                && mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height){
            exitCurrentImg = exitPressed;

            if(mouseListener.isPressed){
                menuMusic.stop();
               Window.getWindow().close();
            }

        } else {
            exitCurrentImg = exit;
        }

        if(mouseListener.getX() >= howtoPlayRect.x && mouseListener.getX() <= howtoPlayRect.x + howtoPlayRect.width
                && mouseListener.getY() >= howtoPlayRect.y && mouseListener.getY() <= howtoPlayRect.y + howtoPlayRect.height){

            howToCurrentImg = howToPlayPressed;

            if(mouseListener.isPressed){
                menuMusic.stop();
                Window.getWindow().changeState(5);
            }

        } else {
            howToCurrentImg = howToPlay;
        }



    }

    @Override
    public void draw(Graphics graphic) {
        graphic.setColor(Color.decode("#b592fb"));
        menuDrawer(graphic, title, titleRect, playCurrentImg, playRect, exitCurrentImg, exitRect, howToCurrentImg, howtoPlayRect);
    }


    static void menuDrawer(Graphics graphic, BufferedImage title, Rectangle titleRect, BufferedImage playCurrentImg, Rectangle playRect, BufferedImage exitCurrentImg, Rectangle exitRect, BufferedImage howToCurrentImg, Rectangle howtoPlayRect) {
        sceneDrawer(graphic, title, titleRect, playCurrentImg, playRect, exitCurrentImg, exitRect);
        graphic.drawImage(howToCurrentImg, (int) howtoPlayRect.x, (int) howtoPlayRect.y, (int) howtoPlayRect.width, (int) howtoPlayRect.height, null);
    }

    static void sceneDrawer(Graphics graphic, BufferedImage title, Rectangle titleRect, BufferedImage playCurrentImg, Rectangle playRect, BufferedImage exitCurrentImg, Rectangle exitRect) {
        drawer(graphic, title, titleRect, playCurrentImg, playRect, exitCurrentImg, exitRect);
    }

    static void drawer(Graphics graphic, BufferedImage title, Rectangle titleRect, BufferedImage playCurrentImg, Rectangle playRect, BufferedImage exitCurrentImg, Rectangle exitRect) {
        graphic.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        graphic.drawImage(title, (int) titleRect.x, (int) titleRect.y, (int) titleRect.width, (int) titleRect.height , null);
        graphic.drawImage(playCurrentImg, (int) playRect.x, (int) playRect.y, (int) playRect.width, (int) playRect.height, null);
        graphic.drawImage(exitCurrentImg, (int) exitRect.x, (int) exitRect.y, (int) exitRect.width, (int) exitRect.height, null);
    }

}
