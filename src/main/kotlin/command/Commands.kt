package command

import command.CommandReply.Companion.CSS_SOLARIZED_TEXT_TEMPLATE
import requests.FootballDataRetriever
import command.CommandReply.Companion.EMPTY_COMMAND
import model.Competitions

class StandingsCommand : CommandExecutor {

    override fun execute(reply: CommandReply) {
        val competition: String = reply.subCommand()
        val table: String = FootballDataRetriever.getStandingsByCompetition(competition)
        reply.reply(table)
    }
}

class HelpCommand : CommandExecutor {

    private fun processSubCommand(sbc: String) : String {
        val message = "Usage: " + when (sbc) {
            "STANDINGS" -> {
                "!standings [competition]"
            }
            "FIXTURES" -> {
                "!fixtures [competition] [matchday: 1-35]"
            }
            else -> return "Available commands:\n!help fixtures\n!help standings"
        }
        val competitions = Competitions.values()
                .joinToString("\n") { "${it.name.padEnd(3)} : ${it.competitionName}" }
        return "$message\n\nAvailable Competitions:\n$competitions\n"
    }


    override fun execute(reply: CommandReply) {
        val subCommand: String = reply.subCommand()
        reply.reply(processSubCommand(subCommand), CSS_SOLARIZED_TEXT_TEMPLATE)
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