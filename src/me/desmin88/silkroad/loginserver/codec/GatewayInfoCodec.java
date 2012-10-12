package me.desmin88.silkroad.loginserver.codec;

import me.desmin88.silkroad.loginserver.abstracts.MessageCodec;
import me.desmin88.silkroad.loginserver.msg.GatewayInfoMessage;
import me.desmin88.silkroad.loginserver.utils.ChannelBufferUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.io.IOException;

/**
 * Created by William Ryan
 * User: Billy
 * Date: 10/9/12
 * Time: 6:01 PM
 * Not to be distributed, modified, or repackaged at all.
 */
public class GatewayInfoCodec extends MessageCodec<GatewayInfoMessage> {

    public GatewayInfoCodec() {
        super(GatewayInfoMessage.class, 0x2001);
    }


    @Override
    public GatewayInfoMessage decode(ChannelBuffer buffer) throws IOException {
        int nameLength = buffer.readShort();
        String name = ChannelBufferUtils.readString(buffer);
        byte flagTemp = buffer.readByte();
        boolean flag;
        if(flagTemp == 1)
            flag = true;
        else
            flag = false;
        return new GatewayInfoMessage(nameLength, name, flag);
    }

    @Override
    public ChannelBuffer encode(GatewayInfoMessage message) throws IOException {
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        buffer.writeShort(message.getNameLength());
        ChannelBufferUtils.writeString(buffer, message.getName());
        if(message.getFlag())
            buffer.writeByte(1);
        else
            buffer.writeByte(0);

        System.out.println(buffer.toByteBuffer().toString());
        return buffer;
    }

}