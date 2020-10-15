package command.commands

import command.CommandExecutor
import command.CommandReply
import model.enums.Competitions
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

    override fun displayHelpUsage(): String {
        return "!fixtures [competition] [matchday: 1-35]\nAvailable Competitions:\n\n${Competitions.displayCompetitions()}"
    }
}