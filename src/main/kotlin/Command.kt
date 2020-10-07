import discord4j.core.event.domain.message.MessageCreateEvent

fun interface Command {
    fun execute(xAuthToken: String, event: MessageCreateEvent)
}

enum class HelpSubCommands(val subCommand: String, val command: Command) {
    STANDINGS("standings", { _, event ->
        val competitions = Competition
                .values()
                .joinToString("\n") { "${it.name.padEnd(3)} : ${it.competitionName}" }
        val message: String = "Available Competitions:\n$competitions"
        sendMessage(message, event.message.channel)
    });
    //FIXTURES("fixtures", null);

    companion object {
        fun contains(subCommand: String) = values().map { it.name }.contains(subCommand)

        fun executeSubCommand(subCommand: String, event: MessageCreateEvent) {
            if(contains(subCommand)) valueOf(subCommand).command.execute("", event)
            else sendMessage("`command:$subCommand not valid`", event.message.channel)
        }
    }
}