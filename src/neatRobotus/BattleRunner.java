package neatRobotus;

import robocode.control.*;
import robocode.control.events.*;


public class BattleRunner {

    public void startBatalha( boolean ver) {
        // Disable log messages from Robocode
        RobocodeEngine.setLogMessagesEnabled(true);

        // Create the RobocodeEngine
        //RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("/home/joao/robocode"));
        // Add our own battle listener to the RobocodeEngine 
        engine.addBattleListener(new BattleObserver());

        // Show the Robocode battle view
        engine.setVisible(ver);

        // Setup the battle specification

        int numberOfRounds = 5;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository("neatRobotus.RobotusCodus*,Meusrobos.Primeirorobo*");

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

        // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes

        // Cleanup our RobocodeEngine
        engine.close();
    }
}

//
// Our private battle listener for handling the battle event we are interested in.
//
class BattleObserver extends BattleAdaptor {

    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {
        //System.out.println("-- Battle has completed --");
        
        // Print out the sorted results with the robot names
        //System.out.println("Battle results:");
       // for (robocode.BattleResults result : e.getSortedResults()) {
       // 		System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
        //}
        Principal.evento = e.getSortedResults();
    }
    
    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {
       // System.out.println("Msg> " + e.getMessage());
    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err> " + e.getError());
    }
    
}