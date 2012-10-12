package me.desmin88.silkroad.loginserver.net.abstracts;

import me.desmin88.silkroad.loginserver.net.abstracts.Message;
import org.jboss.netty.buffer.ChannelBuffer;

import java.io.IOException;

/**
 * Created by William Ryan
 * User: Billy
 * Date: 10/9/12
 * Time: 6:02 PM
 * Not to be distributed, modified, or repackaged at all.
 */
public abstract class MessageCodec<T extends Message> {

    private final Class<T> clazz;
    private final int opcode;

    public MessageCodec(Class<T> clazz, int opcode) {
        this.clazz = clazz;
        this.opcode = opcode;
    }

    public final Class<T> getType() {
        return clazz;
    }

    public final int getOpcode() {
        return opcode;
    }

    public abstract ChannelBuffer encode(T message) throws IOException;

    public abstract T decode(ChannelBuffer buffer) throws IOException;

}