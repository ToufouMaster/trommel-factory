package toufoumaster.trommelfactory;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static toufoumaster.trommelfactory.TrommelFactory.MOD_ID;

public class Config {
	private static final Toml TOML = new Toml("TrommelFactory TOML Config");
	public static final TomlConfigHandler CFG;

	static {
		TOML.addCategory("IDs")
			.addEntry("startingBlockID", "Default: 2780", 2780);

		CFG = new TomlConfigHandler(MOD_ID, TOML);
	}
}
