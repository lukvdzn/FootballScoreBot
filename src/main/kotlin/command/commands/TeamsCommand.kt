package command.commands

import command.CommandExecutor
import command.CommandReply
import model.enums.Areas
import requests.FootballDataRetriever

class TeamsCommand : CommandExecutor {
    override fun execute(reply: CommandReply) {
        val (area) = reply.subCommands()
        val rep = if(area == CommandReply.EMPTY_COMMAND) "Area not specified.\nPlease check out the !help command for more infos."
            else FootballDataRetriever.getTeamsByArea(area)
        reply.reply(rep)
    }

    override fun displayHelpUsage(): String {
        val areas = Areas.values().joinToString(", ") { it.name }
        return "!teams [area: country or continent]\n\nAvailable areas:\n$areas"
    }
}