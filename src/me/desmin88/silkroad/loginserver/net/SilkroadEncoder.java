package me.desmin88.silkroad.loginserver.net;

import me.desmin88.silkroad.loginserver.abstracts.MessageCodec;
import me.desmin88.silkroad.loginserver.abstracts.Message;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by William Ryan
 * User: Billy
 * Date: 10/9/12
 * Time: 5:35 PM
 * Not to be distributed, modified, or repackaged at all.
 */
public class SilkroadEncoder extends OneToOneEncoder {

    @SuppressWarnings("unchecked")
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
        if (msg instanceof Message) {
            Message message = (Message) msg;

            Class<? extends Message> clazz = message.getClass();
            MessageCodec<Message> codec = (MessageCodec<Message>) CodecLookupService.find(clazz);
            if (codec == null) {
                throw new IOException("Unknown message type: " + clazz + ".");
            }

            ChannelBuffer opcodeBuffer = ChannelBuffers.dynamicBuffer(3);
            opcodeBuffer.writeShort(0x5000);
            opcodeBuffer.writeShort(0);

            ChannelBuffer beforeLength = ChannelBuffers.wrappedBuffer(opcodeBuffer, codec.encode(message));
            int length = beforeLength.toByteBuffer().array().length - 4;

            ChannelBuffer lengthBuffer = ChannelBuffers.dynamicBuffer(3);
            lengthBuffer.writeShort(length);

            ChannelBuffer finalBuffer = ChannelBuffers.wrappedBuffer(lengthBuffer, beforeLength);

            System.out.println(Arrays.toString(finalBuffer.toByteBuffer().array()));
            return finalBuffer;
        }
        return msg;
    }


}