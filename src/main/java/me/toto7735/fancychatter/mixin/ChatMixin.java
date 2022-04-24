package me.toto7735.fancychatter.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(ClientConnection.class)
public class ChatMixin {


	@Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
	private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
		if (packet instanceof ChatMessageC2SPacket) {
			String message = ((ChatMessageC2SPacket) packet).getChatMessage();
			if (message.startsWith("/")) return;
			StringBuilder stringBuilder = new StringBuilder();
			char[] charArray = message.toCharArray();
			String[] split = message.split(" ");
			String[] arrayOfString;

			Map<String, String> map = new HashMap<>() {{
				put("youre", "you're");
				put("cant", "can't");
				put("dont", "don't");
				put("theyre", "they're");
				put("couldnt", "couldn't");
				put("whos", "who's");
				put("alot", "a lot");
				put("nor", "nor,");
				put("yet", "yet,");
				put("or", "or,");
				put("imy", "I miss you");
				put("pls", "please");
				put("and", "and,");
				put("np", "no problem");
				put("gtg", "got to go");
				put("cya", "see you again");
				put("asap", "as soon as possible");
				put("fuck", "f***");
				put("shit", "s***");
				put("omw", "on my way");
				put("lmk", "let me know");
				put("ily", "I love you");
				put("iykyk", "if you know you know");
				put("idk", "I don't know");
				put("brb", "be right back");
				put("ty", "thank you");
				put("nvm", "never mind");
				put("omg", "oh my god");
				put("tmr", "tomorrow");
				put("ik", "I know");
				put("wdym", "what do you mean");
				put("idc", "I don't care");
				put("tmi", "too much information");
				put("rn", "right now");
				put("gr8", "great");
				put("ofc", "of course");
				put("yday", "yesterday");
				put("cu", "see you");
			}};

			byte a;
			int i;
			for (i = (arrayOfString = split).length, a = 0; a < i;) {
				String str = arrayOfString[a];
				AtomicBoolean b = new AtomicBoolean(false);

				map.keySet().forEach(e -> {
					if (str.equals(e)) {
						stringBuilder.append(map.get(e)).append(" ");
						b.set(true);
					}
				});

				if (str.equalsIgnoreCase("im") || str.equalsIgnoreCase("i'm"))
					stringBuilder.append("I'm").append(" ");
				else if (str.equalsIgnoreCase("i'll") || str.equalsIgnoreCase("ill"))
					stringBuilder.append("I'll").append(" ");
				else if (!b.get())
					stringBuilder.append(str).append(" ");
				a++;
			}
			
			String message2 = stringBuilder.toString();
			message2 = message2.replaceFirst(String.valueOf(message2.charAt(0)), StringUtils.capitalize(String.valueOf(message2.charAt(0))));
			StringBuilder stringBuilder2 = new StringBuilder(message2);
			
			if (stringBuilder.toString().equals(((ChatMessageC2SPacket) packet).getChatMessage())) return;
			List<String> list = Arrays.asList(".", ",", "?", "!");
			if (!list.contains(String.valueOf(message2.charAt(charArray.length - 1)))) {
				stringBuilder2.setLength(stringBuilder2.length() - 1);
				stringBuilder2.append(".");
			}
			ci.cancel();
			MinecraftClient.getInstance().getNetworkHandler().sendPacket(new ChatMessageC2SPacket(stringBuilder2.toString()));
		}
	}
}