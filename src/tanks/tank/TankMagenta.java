package tanks.tank;

import java.awt.Color;

import tanks.Game;

public class TankMagenta extends EnemyTank
{
	public TankMagenta(String name, double x, double y, double angle)
	{
		super(name, x, y, Game.tank_size, new Color(230, 0, 200), angle, ShootAI.reflect);
		this.enableTargetEnemyReaction = false;
		this.speed = 1.5;
		this.enableMineLaying = false;
		this.liveBulletMax = 3;
		this.cooldownRandom = 20;
		this.cooldownBase = 40;
		
		this.coinValue = 3;
	}
}