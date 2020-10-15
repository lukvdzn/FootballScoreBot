package command

interface CommandExecutor {
    fun execute(reply: CommandReply)
    fun displayHelpUsage() : String
}
