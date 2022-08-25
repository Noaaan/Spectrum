package de.dafuqs.spectrum.particle.effect;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.PositionSourceType;

public class WirelessRedstoneTransmission {
	
	public static final Codec<WirelessRedstoneTransmission> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Vec3d.CODEC.fieldOf("origin").forGetter((itemTransfer) -> {
			return itemTransfer.origin;
		}), PositionSource.CODEC.fieldOf("destination").forGetter((itemTransfer) -> {
			return itemTransfer.destination;
		}), Codec.INT.fieldOf("arrival_in_ticks").forGetter((itemTransfer) -> {
			return itemTransfer.arrivalInTicks;
		})).apply(instance, (Function3) (WirelessRedstoneTransmission::new));
	});
	private final Vec3d origin;
	private final PositionSource destination;
	private final int arrivalInTicks;
	
	public WirelessRedstoneTransmission(Vec3d origin, PositionSource destination, int arrivalInTicks) {
		this.origin = origin;
		this.destination = destination;
		this.arrivalInTicks = arrivalInTicks;
	}
	
	public WirelessRedstoneTransmission(Object origin, Object destination, Object arrivalInTicks) {
		this((Vec3d) origin, (PositionSource) destination, (int) arrivalInTicks);
	}
	
	public static WirelessRedstoneTransmission readFromBuf(PacketByteBuf buf) {
		Vec3d origin = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		PositionSource destination = PositionSourceType.read(buf);
		int arrivalInTicks = buf.readVarInt();
		return new WirelessRedstoneTransmission(origin, destination, arrivalInTicks);
	}
	
	public static void writeToBuf(PacketByteBuf buf, WirelessRedstoneTransmission transfer) {
		buf.writeDouble(transfer.origin.x);
		buf.writeDouble(transfer.origin.y);
		buf.writeDouble(transfer.origin.z);
		PositionSourceType.write(transfer.destination, buf);
		buf.writeVarInt(transfer.arrivalInTicks);
	}
	
	public int getArrivalInTicks() {
		return this.arrivalInTicks;
	}
	
	public Vec3d getOrigin() {
		return this.origin;
	}
	
	public PositionSource getDestination() {
		return this.destination;
	}
}
