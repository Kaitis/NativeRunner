export class ScheduleTO {
  process:string;
  nextRunTimestamp:Date;


  constructor(nextRunTimestamp: Date) {
    this.nextRunTimestamp = nextRunTimestamp;
  }
}

export abstract class AbstractProcessHistoryTO {
  startTime: Date;
  stopTime: Date;
  result: EProcessStatus;
  executionUrl: string;

  constructor(startTime: Date, stopTime: Date, result: EProcessStatus, executionUrl: string) {
    this.startTime = startTime;
    this.stopTime = stopTime;
    this.result = result;
    this.executionUrl = executionUrl;
  }
}

export class ProcessHistoryTO extends AbstractProcessHistoryTO {
  processID:number;
  process: string;
  exitCode: number;


  constructor(startTime: Date, stopTime: Date, result: EProcessStatus, processID: number, process: string, exitCode: number, executionUrl: string) {
    super(startTime, stopTime, result, executionUrl);
    this.processID = processID;
    this.process = process;
    this.exitCode = exitCode;
  }
}

export class ProcessGroupHistoryTO extends AbstractProcessHistoryTO {
  processGroupId: number;
  processGroup: string;
  processHistory: ProcessHistoryTO[];

  constructor(startTime: Date, stopTime: Date, result: EProcessStatus, processGroupId: number, processGroup: string, executionUrl: string, processHistory: ProcessHistoryTO[]) {
    super(startTime, stopTime, result, executionUrl);
    this.processGroupId = processGroupId;
    this.processGroup = processGroup;
    this.processHistory = processHistory;
  }
}

export class ProcessTO{
  id: number;
  name: string;
  directory:string;
  command:string;
  status: EProcessStatus;
  cron: string;
  lastExecutionDate: Date;
  lastExecutionUrl: String;
  priority: EProcessPriority;
  fixedURL: string;

  constructor(id: number, name: string, directory: string, command: string, status: EProcessStatus, cron:string, lastExecutionDate: Date, priority: EProcessPriority, fixedURL:string, lastExecutionUrl: String) {
    this.id = id;
    this.name = name;
    this.directory = directory;
    this.command = command;
    this.status = status;
    this.cron = cron;
    this.lastExecutionDate = lastExecutionDate;
    this.priority = priority;
    this.fixedURL = fixedURL;
    this.lastExecutionUrl = lastExecutionUrl;
  }
}

export class ChainProcessTO {
  processTO: ProcessTO;
  step: number;


  constructor(processTO: ProcessTO, step: number) {
    this.processTO = processTO;
    this.step = step;
  }
}

export class ProcessGroupTO {
  id: number;
  name: string;
  processes: ChainProcessTO[] = [];
  lastExecutionDate: Date;
  status: EProcessStatus;
  cron: string;
  priority: EProcessPriority;
  fixedURL: string;
  lastExecutionUrl: String;

  constructor(id: number, name: string, processes: ChainProcessTO[], status: EProcessStatus, cron:string, lastExecutionDate: Date, priority: EProcessPriority, fixedURL: string, lastExecutionUrl: String) {
    this.id = id;
    this.name = name;
    this.processes = processes;
    this.status = status;
    this.cron = cron;
    this.lastExecutionDate = lastExecutionDate;
    this.priority = priority;
    this.fixedURL = fixedURL;
    this.lastExecutionUrl = lastExecutionUrl;
  }
}

// export class NodeTO {
//   serviceUrl:string;
//   name:string;
//   processes: ProcessTO[] = [];
//   chains: ProcessGroup[] = [];
// }

export class ProcessDetailTO extends ProcessTO{
  history: ProcessHistoryTO[];

  constructor(id: number, name: string, directory: string, command: string, status: EProcessStatus, cron:string, lastExecutionDate: Date, history: ProcessHistoryTO[], priority: EProcessPriority, fixedURL: string, lastExecutionUrl: string) {
    super(id, name, directory, command, status, cron, lastExecutionDate, priority, fixedURL, lastExecutionUrl);
    this.history = history;
  }
}

export class ProcessGroupDetailTO extends ProcessGroupTO {
  history: ProcessGroupHistoryTO[];

  constructor(id: number, name: string, processes: ChainProcessTO[], status: EProcessStatus, cron: string, lastExecutionDate: Date, priority: EProcessPriority, history: ProcessGroupHistoryTO[], fixedURL: string, lastExecutionUrl: string) {
    super(id, name, processes, status, cron, lastExecutionDate, priority, fixedURL, lastExecutionUrl);
    this.history = history;
  }
}

export enum EProcessStatus {
  RUNNING = "RUNNING",
  STOPPED = "STOPPED",
  COMPLETED = "COMPLETED",
  FAILED = "FAILED"
}

export enum EProcessPriority {
  NORMAL = "NORMAL",
  CRITICAL = "CRITICAL"
}

