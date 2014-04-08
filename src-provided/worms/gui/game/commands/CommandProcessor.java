package worms.gui.game.commands;

import java.util.LinkedList;
import java.util.Queue;

public class CommandProcessor {
	private Queue<Command> commandQueue = new LinkedList<Command>();
	private Command executingCommand;

	public void enqueueCommand(Command cmd) {
		commandQueue.add(cmd);
	}

	private void startNextCommand() {
		if (!commandQueue.isEmpty()) {
			executingCommand = commandQueue.poll();
			executingCommand.startExecution();
		} else {
			executingCommand = null;
		}
	}

	public void advanceCommandQueue(double timeDelta) {
		if (executingCommand == null) {
			startNextCommand();
		}
		if (executingCommand != null) {
			executingCommand.update(timeDelta);
			while (executingCommand != null
					&& executingCommand.isFinished()) {
				startNextCommand();
			}
		}
	}

}