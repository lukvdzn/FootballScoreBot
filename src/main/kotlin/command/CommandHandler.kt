package command

import command.commands.*
import discord4j.core.event.domain.message.MessageCreateEvent

class CommandHandler {
    companion object {
        val commands: Map<String, CommandExecutor> = mapOf(
                "standings" to StandingsCommand(),
                "help" to HelpCommand(),
                "fixtures" to FixturesCommand(),
                "teams" to TeamsCommand(),
                "team" to TeamCommand()
        )
    }


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
