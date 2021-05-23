package frc.robot;

public class AutoProgram {

    private final AutoCommand[] autoCommands;

    public AutoProgram(AutoCommand... autoCommands) {
        this.autoCommands = autoCommands;
    }

    public AutoCommand getCommand(int commandNumber) {
        if (commandNumber < autoCommands.length) {
            return autoCommands[commandNumber];
        } else {
            return null;
        }
    }

}
