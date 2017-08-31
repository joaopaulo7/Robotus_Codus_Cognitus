package neatRobotus;

import robocode.AdvancedRobot;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class RobotusCodus extends AdvancedRobot{

	Genoma genoma;
	
		public void run() {
			// Set colors
			setBodyColor(new Color(128, 128, 50));
			setGunColor(new Color(50, 50, 20));
			setRadarColor(new Color(200, 200, 70));
			setScanColor(Color.white);
			setBulletColor(Color.blue);
		     try {
		         FileInputStream fileIn = new FileInputStream("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         genoma = (Genoma) in.readObject();
		         in.close();
		         fileIn.close();
		      }catch(IOException i) {
		         i.printStackTrace();
		         return;
		      }catch(ClassNotFoundException c) {
		         System.out.println("Genoma não encontrado");
		         c.printStackTrace();
		         return;
		      }
			// Loop forever
			while (true) {
				ahead(10);
				System.out.println("kill me");
					double[] v = {
							getBattleFieldHeight(),
							getBattleFieldWidth(),
							getEnergy(),
							getGunCoolingRate(),
							getGunHeading(),
							getGunHeat(),
							getHeading(),
							getHeight(),
							getVelocity(),
							getWidth(),
							getX(),
						 	getY(),
						 	0,
						 	0,
						 	0,
						 	0,
						 	0
						};
					double r[] = genoma.ativar(v);
					ahead( r[0]);
					back( r[1]);
					if( r[2] > 0)
						fire( r[2]);
					turnLeft( r[3]);
					turnRight( r[4]);
					turnGunLeft( r[5]);
					turnGunRight( r[6]);
			}
		}
		
		public void onBulletHit( BulletHitEvent e){
			double[] v = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getVelocity(),
					getWidth(),
					getX(),
				 	getY(),
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
		}
		
		public void onBulletHitBullet( BulletHitBulletEvent e){
			double[] v = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getVelocity(),
					getWidth(),
					getX(),
				 	getY(),
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
	}
		
		public void onHitByBullet( HitByBulletEvent e){
			double[] v = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getVelocity(),
					getWidth(),
					getX(),
				 	getY(),
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
	}
		public void onHitRobot( HitRobotEvent e){
			double[] v = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getVelocity(),
					getWidth(),
					getX(),
				 	getY(),
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
	}
		public void onHitWall( HitWallEvent e){
			double[] v = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getVelocity(),
					getWidth(),
					getX(),
				 	getY(),
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
	}
		
		public void onScannedRobot(ScannedRobotEvent roboto){
			
			double v[] = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getVelocity(),
					getWidth(),
					getX(),
				 	getY(),
				 	roboto.getBearing(),
				 	roboto.getDistance(),
				 	roboto.getEnergy(),
				 	roboto.getHeading(),
				 	roboto.getVelocity()
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
		}
		
		
		public void agir( double[] r){
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
		}

}