package command.commands

import command.CommandExecutor
import command.CommandReply
import requests.FootballDataRetriever

class FixturesCommand : CommandExecutor {
    override fun execute(reply: CommandReply) {
        val (competition, matchday) = reply.subCommands()
        val rep: String = if(competition == CommandReply.EMPTY_COMMAND) {
            "Competition not specified.\nPlease check out the !help command for more infos."
            } else {
                if(matchday == CommandReply.EMPTY_COMMAND) {
                    FootballDataRetriever.getMatchesByCompetitionAndCurrentMatchday(competition)
                } else FootballDataRetriever.getMatchesByCompetitionAndMatchday(competition, matchday)
        }
        reply.reply(rep)
    }
}