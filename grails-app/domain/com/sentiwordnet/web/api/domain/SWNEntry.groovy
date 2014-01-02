package com.sentiwordnet.web.api.domain

class SWNEntry {

    SWNEntry() {
        posScore = new Double(0)
        negScore = new Double(0)
        objScore = new Double(0)
    }

    String pos
    String swn_id
    Double posScore
    Double negScore
    Double objScore
    String gloss
    String term

    Double getObjScore() {
        return (1 - (posScore + negScore))
    }

    static constraints = {
        gloss maxSize: 1000
    }
}
