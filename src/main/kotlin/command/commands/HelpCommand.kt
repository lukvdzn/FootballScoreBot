package command.commands

import command.CommandExecutor
import command.CommandHandler
import command.CommandReply

class HelpCommand : CommandExecutor {

    private val commands by lazy { CommandHandler.commands.entries }

    override fun execute(reply: CommandReply) {
        val (subCommand) = reply.subCommands()
        // If subCommand is Empty, display general usage of help command
        val msg = commands.firstOrNull { it.key.equals(subCommand, true) }
                ?.value
                ?.displayHelpUsage()
                ?.let{ "Usage: $it" }
                ?: displayHelpUsage()

        reply.reply(msg, CommandReply.CSS_SOLARIZED_TEXT_TEMPLATE)
    }

    override fun displayHelpUsage() : String {
        val avc = commands.filter { it.key != "help" }.joinToString("\n") { "!help ${it.key}" }
        return "Available help commands:\n$avc"
    }
}