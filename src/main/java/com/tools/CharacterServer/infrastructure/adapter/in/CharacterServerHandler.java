package com.tools.CharacterServer.infrastructure.adapter.in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CharacterServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress());
        System.out.println("메시지 받음 : " + msg);
    }
}
