package model

enum class HelpSubCommands {
    STANDINGS,
    FIXTURES
}

fun handleSubCommand(subCommands: HelpSubCommands): String {
    val message = when(subCommands) {
        HelpSubCommands.STANDINGS -> {
            val competitions = Competitions.values()
                    .joinToString("\n") { "${it.name.padEnd(3)} : ${it.competitionName}" }
            "Available Competitions:\n$competitions"
        }
        HelpSubCommands.FIXTURES -> {
           ""
        }
    }
    return "`$message`"
}