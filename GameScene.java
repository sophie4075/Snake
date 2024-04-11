import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class GameScene extends Scene{
    Rectangle bg, fg;

    Graphics2D graphic2;

    static Scene pausedScene;

    static Snake snake;

    static MusicPlayer gameMusic;

    KListener kListener;

    public Food food;

    public static int score = 0;

    public static String currentScore = "Your Score: ";

    public static int snakeSize = 2;

    public static int highScore;

    public static boolean paused;



    public GameScene(KListener kListener) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        Window.getWindow().setTitle(Constants.SCREEN_TITLE);

        this.kListener = kListener;
        bg = new Rectangle(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        fg = new Rectangle(24, 48, Constants.TILE_WIDTH*31, Constants.TILE_WIDTH*22);
        snake = new Snake(2, 48, 48+24, 24, 24, fg);
        gameMusic = new MusicPlayer("assets/2020-04-24_-_Arcade_Kid_-_FesliyanStudios.com_-_David_Renda.wav");
        gameMusic.setVolume(0.5f);
        food = new Food(fg, snake, 12, 12, Color.GREEN);
        food.spawn();
        highScore = ScoreWriter.readScores();


    }

    @Override
    public void update(double deltaTime) throws UnsupportedAudioFileException, LineUnavailableException, IOException {



        if (kListener.isKeyPressed(KeyEvent.VK_UP) || kListener.isKeyPressed(KeyEvent.VK_W)){
            snake.changeDirection(Directions.UP);
        } else if(kListener.isKeyPressed(KeyEvent.VK_DOWN) || kListener.isKeyPressed(KeyEvent.VK_S)){
            snake.changeDirection(Directions.DOWN);
        }else if(kListener.isKeyPressed(KeyEvent.VK_LEFT) || kListener.isKeyPressed(KeyEvent.VK_A)){
            snake.changeDirection(Directions.LEFT);
        }else if(kListener.isKeyPressed(KeyEvent.VK_RIGHT) || kListener.isKeyPressed(KeyEvent.VK_D)){
            snake.changeDirection(Directions.RIGHT);
        } else if (kListener.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            paused = true;
            gameMusic.pause();
            pausedScene = Window.getWindow().getCurrentScene();
            Window.getWindow().changeState(3);
        }


        if(!food.foodIsSpawned){
            food.spawn();
        }

        snake.update(deltaTime);
        food.update(deltaTime);

    }

    @Override
    public void draw(Graphics graphic) {
        graphic2 = (Graphics2D)graphic;
        graphic2.setColor(Color.BLUE);
        graphic2.fill(new Rectangle2D.Double(bg.x, bg.y, bg.width, bg.height));

        graphic2.setColor(Color.BLACK);
        graphic2.fill(new Rectangle2D.Double(fg.x, fg.y, fg.width, fg.height));

        graphic.setFont(Constants.MY_FONT);
        graphic.setColor(Color.WHITE);
        graphic.drawString("HIGHSCORE: " + highScore,28,43);

        snake.draw(graphic2);
        food.draw(graphic2);

    }

    public static MusicPlayer getGameMusic() {
        return gameMusic;
    }


    public static void count() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        if (snakeSize <= 5) {
            score += 5;
        } else if (snakeSize <= 10) {
            score += 10;
        }else if (snakeSize <= 15) {
            score += 12;
        } else if (snakeSize <= 20) {
            score += 20;
        } else if (snakeSize <= 35) {
            score += 30;
        } else if (snakeSize > 36){
            score += 50;
        }


        if(score == 106){
            snake.setOgWaitBetweenUpdate(0.09);

        } else if (score == 230) {
            snake.setOgWaitBetweenUpdate(0.08);

        } else if (score == 380) {
            snake.setOgWaitBetweenUpdate(0.07);
        }

        snakeSize +=1;

        Window.getWindow().setTitle( currentScore + score);

    }


    public static void setSnakeSize(int snakeSize) {
        GameScene.snakeSize = snakeSize;
    }

    public static void setPaused(boolean paused) {
        GameScene.paused = paused;
    }

    public static Scene getPausedScene() {
        return pausedScene;
    }


}
