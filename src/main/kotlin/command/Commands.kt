package command

import model.HelpSubCommands
import contains
import model.handleSubCommand
import requests.FootballDataRetriever
import command.CommandReply.Companion.EMPTY_COMMAND

class StandingsCommand : CommandExecutor {

    override fun execute(reply: CommandReply) {
        val competition: String = reply.subCommand()
        val table: String = FootballDataRetriever.getStandingsByCompetition(competition)
        reply.reply(table)
    }
}

class HelpCommand : CommandExecutor {
    override fun execute(reply: CommandReply) {
        val subCommand: String = reply.subCommand()
        val rep = if(contains<HelpSubCommands>(subCommand)) handleSubCommand(HelpSubCommands.valueOf(subCommand))
                    else "`command:$subCommand does not exist`"
        reply.reply(rep)
    }
}

class FixturesCommand : CommandExecutor {
    override fun execute(reply: CommandReply) {
        val (competition, matchday) = reply.subCommands()
        val rep = if(matchday == EMPTY_COMMAND) {
            FootballDataRetriever.getMatchesByCompetitionAndCurrentMatchday(competition)
        } else FootballDataRetriever.getMatchesByCompetitionAndMatchday(competition, matchday)
        reply.reply(rep)
    }
}