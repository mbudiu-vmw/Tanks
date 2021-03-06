package tanks.tank;

import tanks.Game;
import tanks.bullet.Bullet;

public class TankBlack extends TankAIControlled
{
	public double strafeDirection = Math.PI / 2;

	public TankBlack(String name, double x, double y, double angle)
	{
		super(name, x, y, Game.tank_size, 0, 0, 0, angle, ShootAI.straight);
		this.cooldownBase = 75;
		this.cooldownRandom = 0;
		this.speed = 3.5;
		this.enableDefensiveFiring = true;
		this.bulletSpeed = 25.0 / 2;
		this.bulletBounces = 0;
		this.bulletEffect = Bullet.BulletEffect.darkFire;
		this.aimTurretSpeed = 0.06;
		
		this.coinValue = 10;

		this.description = "A smart, very fast tank---which fires rockets";
	}
	
	@Override
	public void reactToTargetEnemySight()
	{
		if (Math.random() < 0.01)
			strafeDirection = -strafeDirection;

		this.setMotionInDirectionWithOffset(Game.playerTank.posX, Game.playerTank.posY, 3.5, strafeDirection);
	}
}
