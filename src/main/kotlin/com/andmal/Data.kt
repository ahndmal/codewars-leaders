package com.andmal

class User {
    var id: String? = null
    var username: String? = null
    var name: String? = null
    var honor = 0
    var clan: String? = null
    var leaderboardPosition = 0
    var skills: ArrayList<Any>? = null
    var ranks: Ranks? = null
    var codeChallenges: CodeChallenges? = null
    override fun toString(): String {
        return "User(id=$id, username=$username, name=$name, honor=$honor, clan=$clan, " +
                "leaderboardPosition=$leaderboardPosition, skills=$skills, ranks=$ranks, " +
                "codeChallenges=$codeChallenges)"
    }

}

class C {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class CodeChallenges {
    var totalAuthored = 0
    var totalCompleted = 0
}

class Cpp {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Elixir {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Go {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Haskell {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Javascript {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Languages {
    var python: Python? = null
    var cpp: Cpp? = null
    var sql: Sql? = null
    var shell: Shell? = null
    var javascript: Javascript? = null
    var haskell: Haskell? = null
    var ruby: Ruby? = null
    var c: C? = null
    var go: Go? = null
    var elixir: Elixir? = null
    var ocaml: Ocaml? = null
}

class Ocaml {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Overall {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Python {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Ranks {
    var overall: Overall? = null
    var languages: Languages? = null
}

class Ruby {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}

class Shell {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}


class Sql {
    var rank = 0
    var name: String? = null
    var color: String? = null
    var score = 0
}


