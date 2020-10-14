package command.commands

import command.CommandExecutor
import command.CommandReply
import requests.FootballDataRetriever

class TeamsCommand : CommandExecutor {
    override fun execute(reply: CommandReply) {
        val (area) = reply.subCommands()
        val rep = if(area == CommandReply.EMPTY_COMMAND) "Area not specified.\nPlease check out the !help command for more infos."
            else FootballDataRetriever.getTeamsByArea(area)
        reply.reply(rep)
    }
}