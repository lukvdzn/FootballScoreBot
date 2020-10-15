package command.commands

import command.CommandExecutor
import command.CommandReply
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

    override fun displayHelpUsage(): String {
        return "!standings [competition]\nAvailable Competitions:\n\n${Competitions.displayCompetitions()}"
    }
}