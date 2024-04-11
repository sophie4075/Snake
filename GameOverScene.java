import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameOverScene extends MenuScene {
    public GameOverScene(MListener mouseListener) {
        super(mouseListener);

        try {
            BufferedImage spriteSheet = ImageIO.read(new File("assets/GameOver Menu.png"));
            title = spriteSheet.getSubimage(8, 298, 946, 156);
            play = spriteSheet.getSubimage(1, 130, 460, 82);
            playPressed = spriteSheet.getSubimage(500, 130, 460, 82);

        } catch (Exception e) {
            e.printStackTrace();
        }

        titleRect = new Rectangle(100, 50, 600, 100);
        playRect = new Rectangle(200, 220, 400, 80);
    }

}


