# Native Runner


Currently jobs/scripts/processes are distributed to many servers and are deployed via the crontab of each server.

####This creates the following problems, all of which the Native-Runner system is attempting to solve:
- There is no overall, clear schedule of job executions to be viewed and monitored from a single point.
- If one the servers is down, there needs to be manual triggering/scheduling of the jobs on a different machine (when and if the failing machine is discovered as down). Otherwise the jobs on the failed server might not run at all.
- There is no cohesive history about job executions and their results
- There is no way for allowing non-technical staff (eg. BI team) to trigger a job if they want to.
- There is no way of chaining jobs to run sequentially
- There is no way of monitoring the JVM on each server machine to acknowledge problems in memory/cpu usage/threads


The 'native-runner-server' application is the backbone of the whole system.  It is responsible for persisting, retrieving and scheduling job executions for each Process/ProcessChain.  Each Process/ProcessChain is started on a different thread by the RemoteProcessLauncher.class, which forwards the process details to the most available client. (calculated naively at the moment by the memory availability of the client machine), except when otherwise specified (by defining the fixedURL property of Process/ProcessGroup).

The 'native-runner-client' application receives the command from the Admin, runs the process and returns the results back to the Admin for persistence.  The client can be configured to consume the logs of the nested process ( with the intention at a later stage to forward the logs to the ELK stack via the logback-encoder).  This is configurable via the runner.shouldShowLogs property of the client (in application.yml). The client is setup to launch at a random available port and register itself with the admin.



###Communication
Communication to/from all components is done via REST calls.



###Fail-over 
Any Process marked with priority CRITICAL will be retried if failed for any reason. Max retries are set to 3. 

