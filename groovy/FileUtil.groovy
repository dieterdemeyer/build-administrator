class FileUtil {
    
    FileUtil() {}
    
    def compareFilesAndCreateReport = { sourceFile, targetFile, outputFile ->
      if(sourceFile.exists() && targetFile.exists()) {
        List linesSourceFile = sourceFile.readLines()
        List linesTargetFile = targetFile.readLines()
      
        def listOfTagsOrBranchesNotRemoved = []
      
        for(int i =0; i<linesSourceFile.size(); i++) {
          if(linesTargetFile.contains(linesSourceFile[i])) {
            listOfTagsOrBranchesNotRemoved << linesSourceFile[i]
          }
        }
      
        if(listOfTagsOrBranchesNotRemoved.size() > 0) {
          listOfTagsOrBranchesNotRemoved.each{
            outputFile << it + "\n"
          }
          
          println "Created report : " + outputFile.getName()
        }
      }
    }
    
}