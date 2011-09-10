class TagAndBranchRemovalUtil {
    
    TagAndBranchRemovalUtil() {}
    
    def retrieveTagsAndBranchesInfoFromCvsStatus = { cvsStatusFile, tagsAndBranchesSummaryFile, numberOfMonths ->
      def sysCalendar = Calendar.getInstance()
      sysCalendar.add(Calendar.MONTH, -numberOfMonths)
      def year = sysCalendar.get(Calendar.YEAR)
      def month = sysCalendar.get(Calendar.MONTH) + 1
      println "Retrieving tags and branches before " + year + String.format("%02d", month)
      
      List lines = cvsStatusFile.readLines()
      lines.removeRange(0,lines.indexOf("   Existing Tags:") + 1)
      
      def startingPointForRemoval
      for (i in lines) {
        if(i.contains(year + String.format("%02d", month))) {
          startingPointForRemoval = i
          break
        }
      }
      
      if(startingPointForRemoval != null) {
          lines.removeRange(0, lines.indexOf(startingPointForRemoval) + 1)
      
          for (i in lines) {
              tagsAndBranchesSummaryFile << "${i}"
              tagsAndBranchesSummaryFile << "\n"
          }
      }
    }
    
    def findTagsOrBranchesInFileAndCreateOutputFile = { inputFile, revision, outputFile ->
       def listOfTagsOrBranches = []
        if(inputFile.exists()) {
            List lines = inputFile.readLines()
            lines.removeRange(0,lines.indexOf("   Existing Tags:") + 1)
            for (i in lines) {
              if(revision.equals(Revision.TAG) && i.contains("(revision:")) {
                listOfTagsOrBranches << i.split("\\(")[0].trim()
              } else if(revision.equals(Revision.BRANCH) && i.contains("(branch:")) {
                listOfTagsOrBranches << i.split("\\(")[0].trim()
              }
            }
            
            createFile(listOfTagsOrBranches, outputFile)
        }
    }
  
    def createFile = { listOfTagsOrBranches, outputFile ->
      if(listOfTagsOrBranches.size() > 0) {
        listOfTagsOrBranches.each{outputFile << it + "\n"}
      }
    }
}