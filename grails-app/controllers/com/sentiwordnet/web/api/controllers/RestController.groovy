package com.sentiwordnet.web.api.controllers

import com.sentiwordnet.web.api.domain.SWNEntry
import grails.converters.JSON

class RestController {

    def fetchService

    def getByTermName(String term) {
        List<SWNEntry> swnEntry = fetchService.getSWNEntryByTerm(term)
        render swnEntry as JSON
    }

    def getByTermNameAndPos(String term, String pos) {
        SWNEntry swnEntry = fetchService.getSWNEntryByTermAndPos(term, pos)
        render swnEntry as JSON
    }

    def getAllTerms() {
        render fetchService.getAllEntries() as JSON
    }
}
