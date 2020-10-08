package command

fun interface CommandExecutor {
    fun execute(reply: CommandReply)
}
