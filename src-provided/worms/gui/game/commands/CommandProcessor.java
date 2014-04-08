package worms.gui.game.commands;

import java.util.LinkedList;
import java.util.List;
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
			while (executingCommand != null && executingCommand.isTerminated()) {
				startNextCommand();
			}
		}
	}

	public List<Command> getCommandStack() {
		List<Command> result = new LinkedList<Command>(commandQueue);
		if (executingCommand != null) {
			result.add(0, executingCommand);
		}
		return result;
	}

}