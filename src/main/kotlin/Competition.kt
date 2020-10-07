enum class Competition(val id: String, val competitionName: String) {
    BSA("2013", "Brazilian Division One"),
    PL("2021", "Premier League"),
    ELC("2016", "Championship"),
    CL("2001", "Champions League"),
    EC("2018", "European Championships"),
    FL1("2015", "France League 1"),
    BL1("2002", "Bundesliga"),
    SA("2019", "Serie A"),
    DED("2003", "Evredivise"),
    PPL("2017", "Portuguese Primera Dvision"),
    PD("2014", "La Liga"),
    WC("2000", "World Cup");

    companion object {
        fun contains(name: String) = values().map { it.name }.contains(name)
    }
}