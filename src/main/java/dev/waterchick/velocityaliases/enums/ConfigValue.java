package dev.waterchick.velocityaliases.enums;


public enum ConfigValue {
    MESSAGES_PREFIX("&8[&3VelocityAliases&8] &r"),
    MESSAGES_CONFIGRELOADED("&7Config reloaded"),
    MESSAGES_CONNECTING("&aConnecting to %server% server..."),
    MESSAGES_CONNECTINGNOPERMISSION("&cYou do not have permission to connect to %server% server."),
    MESSAGES_ALREADYCONNECTED("&cYou're already connected to %server% server."),
    MESSAGES_ONLYPLAYER("&cOnly players may execute this command");

    private String value;

    ConfigValue(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
