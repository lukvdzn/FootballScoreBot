package model.enums

// TIER_ONE values
enum class Competitions(val competitionName: String) {
    BSA("Brazilian Division One"),
    PL("Premier League"),
    ELC("Championship"),
    CL("Champions League"),
    EC("European Championships"),
    FL1("France League 1"),
    BL1("Bundesliga"),
    SA("Serie A"),
    DED("Evredivise"),
    PPL("Portuguese Primera Dvision"),
    PD("La Liga"),
    WC("World Cup");

    companion object {
        fun displayCompetitions() : String {
            return values().joinToString("\n") { "${it.name.padEnd(3)} : ${it.competitionName}" }
        }
    }
}