import au.com.bytecode.opencsv.CSVReader
import com.sentiwordnet.web.api.domain.SWNEntry
import com.sentiwordnet.web.api.helpers.Paths
import org.springframework.util.StringUtils

class BootStrap {
    def grailsApplication

    def init = { servletContext ->
        initPaths()
        if (SWNEntry.count == 0) {
            File f = new File(Paths.SENTIWORDNET_FILEPATH)
            InputStream sentiwordData = new FileInputStream(f)
            insertData(sentiwordData)
        }
        File f = new File(Paths.SENTIWORDNET_FILEPATH)

        InputStream sentiwordData = new FileInputStream(f)
        insertData(sentiwordData)
    }

    private void insertData(FileInputStream existingData) {
        int count = 0;
        // todo: find way in grovvy to define simple char
        CSVReader reader = new CSVReader(new InputStreamReader(existingData), '\t'.charAt(0), '\"'.charAt(0), 0);
        String[] header = reader.readNext();
        String[] content = reader.readNext();

        while (content != null) {
            println content
            println count++;
            SWNEntry swnEntry = new SWNEntry()
            // we get the gloss at i-1 step so we can save all the words
            for (int i = 0; i < header.length - 1; i++) {
                for (String value : content[i].split("\\|")) {
                    if (StringUtils.hasText(value)) {
                        println(header[i] + " " + value)

                        switch (i) {
                            case 0:
                                swnEntry.setPos(value)
                                break;
                            case 1:
                                swnEntry.setSwn_id(value)
                                break;

                            case 2:
                                swnEntry.setPosScore(Double.valueOf(value))
                                break;

                            case 3:
                                swnEntry.setNegScore(Double.valueOf(value))
                                break;

                            case 4:
                                String gloss = content[i + 1]
                                swnEntry.setGloss(gloss)

                                String[] terms = value.split(" ")
                                if (terms.length > 0)
                                    for (int j = 0; j < terms.length; j++) {
                                        String term = terms[j]
                                        String clearTerm
                                        if (term.contains("#"))
                                            clearTerm = term.split("#")[0]
                                        else
                                            clearTerm = term
                                        SWNEntry newEntry = copyFromPrevEntry(swnEntry)
                                        newEntry.setTerm(clearTerm)

                                        newEntry.save()
                                    }
                                break;
                            default:
                                break;
                        }

                    }
                }
            }
            content = reader.readNext();
        }

        println "Insertion done."
    }

    SWNEntry copyFromPrevEntry(SWNEntry swnEntry) {
        SWNEntry newEntry = new SWNEntry()
        swnEntry.properties.each { newEntry.setProperty(it.getKey(), it.getValue()) }
        return newEntry
    }

    void initPaths() {
//        Paths.SENTIWORDNET_FILEPATH = grailsApplication.config.sentiwordnetwebapi.csv.locaiton + grailsApplication.config.sentiwordnetwebapi.csv.filename

        Paths.SENTIWORDNET_FILEPATH = grailsApplication.mainContext.getResource("extra/").getFile().getPath() + "/" + grailsApplication.config.sentiwordnetwebapi.csv.filename

    }

    def destroy = {
    }
}
