package me.desmin88.silkroad.loginserver.net;

import me.desmin88.silkroad.loginserver.abstracts.MessageCodec;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.handler.codec.replay.VoidEnum;

import java.io.IOException;


/**
 * Created by William Ryan
 * User: Billy
 * Date: 10/9/12
 * Time: 5:35 PM
 * Not to be distributed, modified, or repackaged at all.
 */
public class SilkroadDecoder extends ReplayingDecoder<VoidEnum>{

    private int previousOpcode = -1;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel c, ChannelBuffer buf, VoidEnum state) throws Exception {
        int length = buf.readUnsignedByte();
        int opcode = buf.readUnsignedByte();

        MessageCodec<?> codec = CodecLookupService.find(opcode);
        if (codec == null) {
            throw new IOException("Unknown operation code: " + opcode + " (previous opcode: " + previousOpcode + ").");
        }

        previousOpcode = opcode;

        return codec.decode(buf);
    }



}