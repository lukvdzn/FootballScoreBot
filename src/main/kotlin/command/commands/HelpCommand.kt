package command.commands

import command.CommandExecutor
import command.CommandReply
import model.enums.Competitions

class HelpCommand : CommandExecutor {

    private fun processSubCommand(sbc: String) : String {
        val message = "Usage: " + when (sbc) {
            "STANDINGS" -> {
                "!standings [competition]"
            }
            "FIXTURES" -> {
                "!fixtures [competition] [matchday: 1-35]"
            }
            "TEAMS" -> {
                "!teams [area: country or continent]"
            }
            "TEAM" -> {
                "!team [id: can be fetched from teams command]"
            }
            else -> return "Available help commands:\n!help fixtures\n!help standings\n!help team[s]"
        }
        val competitions = Competitions.values()
                .joinToString("\n") { "${it.name.padEnd(3)} : ${it.competitionName}" }
        return "$message\n\nAvailable Competitions:\n$competitions\n"
    }


    override fun execute(reply: CommandReply) {
        val (subCommand) = reply.subCommands()
        reply.reply(processSubCommand(subCommand), CommandReply.CSS_SOLARIZED_TEXT_TEMPLATE)
    }
}