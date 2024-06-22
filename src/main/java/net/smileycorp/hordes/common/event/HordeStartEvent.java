package net.smileycorp.hordes.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.ICancellableEvent;
import net.smileycorp.hordes.common.Constants;
import net.smileycorp.hordes.hordeevent.capability.HordeEvent;

public class HordeStartEvent extends HordePlayerEvent implements ICancellableEvent {
	
	protected final BlockPos pos;
	protected String message = Constants.hordeEventStart;
	protected final boolean wasCommand;

	public HordeStartEvent(ServerPlayer player, HordeEvent horde, boolean wasCommand) {
		super(player, horde);
		pos = player.blockPosition();
		this.wasCommand = wasCommand;
	}

	public BlockPos getPlayerPos() {
		return pos;
	}

	//Whether the event was started with a command
	public boolean wasCommand() {
		return wasCommand;
	}

}
