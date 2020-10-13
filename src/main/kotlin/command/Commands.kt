package command

import command.CommandReply.Companion.CSS_SOLARIZED_TEXT_TEMPLATE
import requests.FootballDataRetriever
import command.CommandReply.Companion.EMPTY_COMMAND
import model.enums.Competitions

class StandingsCommand : CommandExecutor {

    override fun execute(reply: CommandReply) {
        val (competition) = reply.subCommands()
        val table: String = if(competition == EMPTY_COMMAND) {
            "Competition not specified.\nPlease check out the !help command for more infos."
        } else {
            FootballDataRetriever.getStandingsByCompetition(competition)
        }
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
            else -> return "Available help commands:\n!help fixtures\n!help standings"
        }
        val competitions = Competitions.values()
                .joinToString("\n") { "${it.name.padEnd(3)} : ${it.competitionName}" }
        return "$message\n\nAvailable Competitions:\n$competitions\n"
    }


    override fun execute(reply: CommandReply) {
        val (subCommand) = reply.subCommands()
        reply.reply(processSubCommand(subCommand), CSS_SOLARIZED_TEXT_TEMPLATE)
    }
}

class FixturesCommand : CommandExecutor {
    override fun execute(reply: CommandReply) {
        val (competition, matchday) = reply.subCommands()
        val rep: String = if(competition == EMPTY_COMMAND) {
            "Competition not specified.\nPlease check out the !help command for more infos."
            } else {
                if(matchday == EMPTY_COMMAND) {
                    FootballDataRetriever.getMatchesByCompetitionAndCurrentMatchday(competition)
                } else FootballDataRetriever.getMatchesByCompetitionAndMatchday(competition, matchday)
        }
        reply.reply(rep)
    }
}

class TeamsCommand : CommandExecutor {
    override fun execute(reply: CommandReply) {
        val (area) = reply.subCommands()
        val rep = if(area == EMPTY_COMMAND) "Area not specified.\nPlease check out the !help command for more infos."
            else FootballDataRetriever.getTeamsByArea(area)
        reply.reply(rep)
    }

}