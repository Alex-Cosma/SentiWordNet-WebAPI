package com.sentiwordnet.web.api.services

import com.sentiwordnet.web.api.domain.SWNEntry

class FetchService {

    List<SWNEntry> getSWNEntryByTerm(String term) {
        List<SWNEntry> swnTerms = SWNEntry.findAllByTerm(term)
        Map<String, List<SWNEntry>> map = new HashMap<String, List<SWNEntry>>()

        for (SWNEntry s : swnTerms) {
            List<SWNEntry> prevList = map.get(s.getPos())
            if (!prevList)
                prevList = new ArrayList<SWNEntry>()
            prevList.add(s)
            map.put(s.getPos(), prevList)
        }

        List<SWNEntry> finalList = new ArrayList<SWNEntry>()
        for (List<SWNEntry> entryList : map.values()) {
            SWNEntry finalEntry = extractInfo(entryList)
            finalList.add(finalEntry)
        }
        return finalList
    }

    SWNEntry getSWNEntryByTermAndPos(String term, String pos) {
        List<SWNEntry> entryList = SWNEntry.findAllByTermAndPos(term, pos)
        SWNEntry finalEntry = extractInfo(entryList)
        return finalEntry
    }

    private static SWNEntry extractInfo(List<SWNEntry> entryList) {
        SWNEntry finalEntry = new SWNEntry()

        for (SWNEntry swnEntry : entryList) {
            finalEntry.negScore += swnEntry.negScore
            finalEntry.posScore += swnEntry.posScore
            finalEntry.setPos(swnEntry.getPos())
            finalEntry.setTerm(swnEntry.getTerm())
        }

        if (entryList.size() > 0) {
            finalEntry.negScore /= entryList.size()
            finalEntry.posScore /= entryList.size()
        }
        finalEntry
    }
}
