package cn.yingyya.next.moment;

import cn.yingyya.next.moment.command.NextMomentCommand;
import org.jetbrains.annotations.NotNull;

public class NextMoment extends NextPlugin<NextMoment> {

	@Override
	public void onNextLoad() {
		this.getCommandManager().registerCommand(new NextMomentCommand(getPluginInstance()));
	}

	@Override
	public void onNextUnload() {

	}

	@Override
	protected @NotNull NextMoment getPluginInstance() {
		return this;
	}
}