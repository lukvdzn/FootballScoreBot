package command

import discord4j.core.event.domain.message.MessageCreateEvent

class CommandHandler {
    private val commands: Map<String, CommandExecutor> = mapOf(
            "standings" to StandingsCommand(),
            "help" to HelpCommand(),
            "fixtures" to FixturesCommand()
    )

    fun handleCommand(event: MessageCreateEvent) {

        val command: String = event.message.content.split(" ")[0]

        for((c: String, ce) in commands) {
            if("!$c".equals(command, ignoreCase = true)) {
                ce.execute(CommandReply(event))
                break
            }
        }
    }
}
