package command.commands

import command.CommandExecutor
import command.CommandReply
import requests.FootballDataRetriever

class TeamCommand : CommandExecutor {

    override fun execute(reply: CommandReply) {
        val (teamId) = reply.subCommands()
        reply.reply(FootballDataRetriever.getTeam(teamId))
    }

    override fun displayHelpUsage(): String {
        return "!team [id: can be fetched from teams command]"
    }
}