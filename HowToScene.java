import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HowToScene extends Scene {

    public MListener mouseListener;
    public BufferedImage wasdControl, arrowControl, escControl, quitToTitle, quitToTitlePressed, currentQuitToTitleImg;
    public Rectangle wasdRect, arrowRect, escRect, quitToTitleRect;

    public HowToScene(MListener mouseListener) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.mouseListener = mouseListener;
        Window.getWindow().setTitle("How to play Snake");

        try {

            BufferedImage spriteSheet = ImageIO.read(new File("assets/Controls.png"));
            wasdControl = spriteSheet.getSubimage(0, 18, 373, 311);
            arrowControl = spriteSheet.getSubimage(513, 170, 373, 311);
            escControl = spriteSheet.getSubimage(843,0, 117, 155);
            quitToTitle = spriteSheet.getSubimage(397, 0, 72, 68);
            quitToTitlePressed = spriteSheet.getSubimage(494, 0, 72, 68);

        }catch (Exception e){
            e.printStackTrace();
        }

        currentQuitToTitleImg = quitToTitle;


        wasdRect = new Rectangle(20, 300, 150, 140);
        arrowRect = new Rectangle(300, 300, 150, 140);
        escRect = new Rectangle(600,340, 55, 70);
        quitToTitleRect = new Rectangle(750,50, 36, 34);

    }

    @Override
    public void update(double deltaTime) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(mouseListener.getX() >= quitToTitleRect.x && mouseListener.getX() <= quitToTitleRect.x + quitToTitleRect.width
                && mouseListener.getY() >= quitToTitleRect.y && mouseListener.getY() <= quitToTitleRect.y + quitToTitleRect.height){

            currentQuitToTitleImg = quitToTitlePressed;

            if(mouseListener.isPressed){
                Window.getWindow().changeState(0);
                Window.getWindow().setTitle(Constants.SCREEN_TITLE);
            }

        } else {
            currentQuitToTitleImg = quitToTitle;
        }
    }

    @Override
    public void draw(Graphics graphic) {
        graphic.setColor(Color.decode("#b592fb"));

        helpDrawer(graphic, wasdControl, wasdRect, arrowControl, arrowRect, escControl, escRect, currentQuitToTitleImg, quitToTitleRect);


        graphic.setFont(new Font("Courier", Font.BOLD, 22));
        graphic.setColor(Color.BLACK);
        graphic.drawString("THE GAME", 20, 95);
        graphic.drawString("BASIC CONTROLS", 20, 280);

        graphic.setFont(Constants.MY_FONT);
        graphic.setColor(Color.BLACK);
        graphic.drawString("Use the arrow keys or \"WASD\" to move the snake around the board.",20,500);
        graphic.drawString("Please note that you can't move up while moving down or move left while moving right and so on... ",20,525);
        graphic.drawString("You may pause the game by pressing \"ESC\".", 20, 550);

        graphic.drawString("You're playing a snake, trying to find food in order to grow! :)",20, 130);
        graphic.drawString("As the snake finds food, it eats the food, and thereby grows larger.", 20, 155);
        graphic.drawString("The game ends when the snake either intersects with the boards borders or with itself.", 20,180);
        graphic.drawString("The goal is to make the snake as large as possible before that happens.", 20, 205);
    }

    static void helpDrawer (Graphics graphic, BufferedImage wasdControl, Rectangle wasdRect, BufferedImage arrowControl, Rectangle arrowRect, BufferedImage escControl, Rectangle escRect, BufferedImage currentQuitToTitleImg, Rectangle quitToTitleRect) {
        MenuScene.drawer(graphic, wasdControl, wasdRect, arrowControl, arrowRect, escControl, escRect);
        graphic.drawImage(currentQuitToTitleImg, (int) quitToTitleRect.x, (int) quitToTitleRect.y, (int) quitToTitleRect.width, (int) quitToTitleRect.height, null);
    }
}
