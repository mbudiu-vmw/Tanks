package tanks.tank;

import tanks.Game;
import tanks.gui.screen.ScreenLevelBuilder;

public class TankSpawnMarker extends Tank
{
    public String defaultTexture = "/player_spawn.png";
    public TankSpawnMarker(String name, double x, double y, double angle)
    {
        super(name, x, y, Game.tank_size, 0, 150, 255);
        this.angle = angle;
        this.texture = this.defaultTexture;
    }

    @Override
    public void draw()
    {
        if (Game.screen instanceof ScreenLevelBuilder && ((ScreenLevelBuilder) Game.screen).spawns.size() > 1)
            this.texture = this.defaultTexture;
        else
            this.texture = null;

        super.draw();
    }
}
