package org.usfirst.frc.team6520.robot.commandgroups;

import org.usfirst.frc.team6520.robot.commands.C_AutoByDistance;
import org.usfirst.frc.team6520.robot.commands.C_AutoShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_Mid_Left extends CommandGroup {

    public CG_Mid_Left() { //đi thẳng, bắn
        
        /*
    	 * |                                       |
    	 * |                                       |
    	 * |        ---------------------          |
    	 * |        |     |       |     |          |
    	 * |        |     |       |     |          |
    	 * |        ---------------------          |
    	 * |           ^                           |
    	 * |           |                           |
    	 * |          ---                          |
    	 * |          | |                          |
    	 * |          ---                          |
    	 * -----------------------------------------
    	 */
       addSequential(new C_AutoByDistance(2.76));
       addSequential(new C_AutoShoot());
    }
}
