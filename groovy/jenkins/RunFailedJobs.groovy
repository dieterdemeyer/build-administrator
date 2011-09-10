joblist = hudson.model.Hudson.instance.items.findAll { job ->
    job.isBuildable()
}

startServer = "admin computer"
startNote = "bulk start"

joblist.each { run ->
    run.scheduleBuild(cause)
}