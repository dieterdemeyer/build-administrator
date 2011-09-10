hudsonInstance = hudson.model.Hudson.instance
allItems = hudsonInstance.items

activeJobs = allItems.findAll { job ->
    job.isBuildable()
}

failedRuns = activeJobs.findAll { job ->
    job.lastBuild.result == hudson.model.Result.FAILURE
}

failedRuns.each {run ->
    println(run.name)
}