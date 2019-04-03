package com.ak.process_launcher.web.to;

import java.io.Serializable;

public class ChainProcessTO extends AbstractTO implements Serializable{

  static final Long serialVersionUID = 1L;

  private ProcessTO processTO;
  private int step;

  public ChainProcessTO() {
  }

  public ChainProcessTO(ProcessTO processTO, int step) {
    this.processTO = processTO;
    this.step = step;
  }

  public ProcessTO getProcessTO() {
    return processTO;
  }

  public void setProcessTO(ProcessTO processTO) {
    this.processTO = processTO;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }
}
