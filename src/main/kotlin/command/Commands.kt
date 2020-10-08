package command

import model.HelpSubCommands
import model.contains
import model.handleSubCommand
import requests.getStandingsByCompetition

class StandingsCommand : CommandExecutor {

    override fun execute(reply: CommandReply) {
        val competition: String = reply.subCommand()
        val table: String = getStandingsByCompetition(competition)
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