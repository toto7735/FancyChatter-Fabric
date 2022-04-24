package me.toto7735.fancychatter;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FancyChatter implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("fancychatter");

	@Override
	public void onInitialize() {
		LOGGER.info("Let's chat fancy!");
	}

}
